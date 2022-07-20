package com.project.shopping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.project.shopping.Adapter.ARAdapter;
import com.project.shopping.Adapter.CommentsAdapter;
import com.project.shopping.Model.Comments;
import com.project.shopping.Model.ThreeModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ARActivity extends AppCompatActivity implements ARAdapter.ARClick {
    ArFragment arFragment;
    ModelRenderable modelRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aractivity);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        setUpRV();


//        List<Comments> comments = new ArrayList<>();
//        comments.add(
//                new Comments("test", "123", "testName", "")
//        );
//        comments.add(
//                new Comments("test1", "1223", "testName1", "")
//        );
//        comments.add(
//                new Comments("test2", "123", "testName2", "")
//        );
//        CommentsAdapter adapter = new CommentsAdapter(this, comments);
//        RecyclerView rv = findViewById(R.id.testRV);
//        rv.setAdapter(adapter);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            Toast.makeText(this, "TAP Listener", Toast.LENGTH_SHORT).show();
            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
            anchorNode.setRenderable(duckRenderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);
        });
    }

    private void setUpRV() {
        List<ThreeModel> list = get3dList();
        ARAdapter adapter = new ARAdapter(list, this);
        RecyclerView recyclerView = findViewById(R.id.arRV);
        recyclerView.setAdapter(adapter);
    }

    private List<ThreeModel> get3dList() {
        List<ThreeModel> list = new ArrayList<>();
        list.add(
                new ThreeModel(
                        "Bed", R.drawable.bed,
                        Uri.parse("https://raw.githubusercontent.com/MhmdAwad/RetrofitApp/master/New%20folder/bed/scene.gltf"),
                        1.0f
        ));
        list.add(
                new ThreeModel(
                        "Chair", R.drawable.modern_chair,
                        Uri.parse("https://raw.githubusercontent.com/MhmdAwad/RetrofitApp/master/New%20folder/office_chair/scene.gltf"),
                        0.9f
                )
        );
        list.add(
                new ThreeModel(
                        "Sofa", R.drawable.sofa,
                        Uri.parse("https://raw.githubusercontent.com/MhmdAwad/RetrofitApp/master/New%20folder/victorian_lounge_sofa/scene.gltf")
                        ,1.0f
                )
        );
        list.add(
                new ThreeModel(
                        "Table", R.drawable.table,
                        Uri.parse("https://raw.githubusercontent.com/MhmdAwad/RetrofitApp/master/New%20folder/table/scene.gltf")
                        ,0.1f
                )
        );
        return list;
    }


    ModelRenderable duckRenderable;

    private void setUpModel(ThreeModel model) {

        Uri assetLink = model.getModel3D();
        float itemScale = model.getScale();

        ModelRenderable.builder()
                .setSource(this, RenderableSource.builder().setSource(
                        this,
                       assetLink,
                        RenderableSource.SourceType.GLTF2)
                        .setScale(itemScale)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(assetLink.toString())
                .build()
                .thenAccept(renderable -> {
                    System.out.println(">>>>>>>>> SUCCESS");
                    Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show();
                    duckRenderable = renderable;
//                    addNoteToScene(renderable);
                })
                .exceptionally(
                        throwable -> {
                            System.out.println(">>>>>>>> MODEL ERROR " + throwable.getMessage());
                            Toast toast =
                                    Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    AnchorNode anchorNode = new AnchorNode();

    private void addNoteToScene(ModelRenderable modelRenderable) {
        removeAnchorNode(anchorNode);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }

    private void removeAnchorNode(AnchorNode nodeToRemove) {
        try {
            arFragment.getArSceneView().getScene().removeChild(nodeToRemove);
            nodeToRemove.getAnchor().detach();
            nodeToRemove.setParent(null);
            nodeToRemove.setRenderable(null);
        } catch (Exception e) {
            System.out.println(">>>>>>>>EEE "+ e.getMessage());
        }
    }

    @Override
    public void on3DClick(ThreeModel threeModel) {
        Toast.makeText(this, "Please wait until " + threeModel.getModelName() + " model loaded!", Toast.LENGTH_SHORT).show();
        setUpModel(threeModel);
    }
}