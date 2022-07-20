package com.project.shopping.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.project.shopping.Model.ImageDataModel;
import com.project.shopping.Model.ProcessingDataModel;
import com.project.shopping.Model.Products;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageProcessing {
    public static MutableLiveData<List<Products>> selectedProducts = new MutableLiveData<>();
    private List<Products> productsList = new ArrayList<>();
    private Context context;

    public ImageProcessing(DatabaseReference databaseReference, Context context) {
        this.context = context;
        getListOfImage(databaseReference);
    }

    private void getListOfImage(DatabaseReference databaseReference) {
        databaseReference.get().addOnSuccessListener(dataSnapshot -> {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Products products = snapshot.getValue(Products.class);
                productsList.add(products);
            }
            processListOfImage();
        });
    }

    private void processListOfImage() {
        for (Products products : productsList) {
            processImageLink(products);
        }
    }

    private List<ProcessingDataModel> processingDataModelList = new ArrayList<>();

    public void processBitmapImage(Bitmap bitmap, Products products) {
        InputImage image;
        image = InputImage.fromBitmap(bitmap, 0);
        ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    List<ImageDataModel> list = new ArrayList<>();
                    for (ImageLabel imageLabel : labels) {
                        list.add(
                                new ImageDataModel(imageLabel.getText(), imageLabel.getConfidence())
                        );
                    }
                    if(products != null) {
                        processingDataModelList.add(
                                new ProcessingDataModel(products, list)
                        );
                    }else{
                        checkSelectedImage(list);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        System.out.println(":>>>>>>>>>>>>>5555 " + e.getMessage());
                    }
                });
    }


    private void checkSelectedImage(List<ImageDataModel> list) {
        Set<Products> productsList = new HashSet<>();
        for(ProcessingDataModel processingDataModel : processingDataModelList) {
            for (ImageDataModel processing : list) {
                for(ImageDataModel dataModel : processingDataModel.getImageDataModel()){
                    if(processing.getResult().equals(dataModel.getResult())){
                        if(processing.getConfidence() > 0.83 || dataModel.getConfidence() > 0.83) {
                            System.out.println(">>>>>>>>>>>>>>>> " + processing.getResult()
                            +  " >>>>>>>>>> " + dataModel.getResult());
                            productsList.add(
                                    processingDataModel.getProducts()
                            );
                        }
                    }
                }
            }
        }
        if(productsList.isEmpty()){
            Toast.makeText(context, "No Products Found with same searched image!", Toast.LENGTH_SHORT).show();
        }else
            selectedProducts.setValue(new ArrayList<>(productsList));
    }

    private void processImageLink(Products products) {
        Picasso.get().load(products.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                processBitmapImage(bitmap, products);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("TAG>>>>>>>>>>>>onBitmapFailed", "Getting ready to get the image");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d("TAG>>>>>>>>>>>>", "Getting ready to get the image");
                //Here you should place a loading gif in the ImageView
                //while image is being obtained.
            }
        });
    }

}
