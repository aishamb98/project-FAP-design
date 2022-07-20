package com.project.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.project.shopping.Model.Products;
import com.project.shopping.util.ImageProcessing;
import com.project.shopping.util.SearchByImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchByImageActivity extends AppCompatActivity implements SearchByImageAdapter.ProductsListener {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_image);
        recyclerView = findViewById(R.id.searchByImageRV);
        initViews();
        setTitle("Search By Image");
    }

    private void initViews() {
        ImageProcessing.selectedProducts.observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                if(!products.isEmpty()){
                    SearchByImageAdapter adapter = new SearchByImageAdapter(products, SearchByImageActivity.this);
                    recyclerView.setAdapter(adapter);
                }
//                ImageProcessing.selectedProducts.setValue(new ArrayList<Products>());
            }
        });
    }

    @Override
    public void onProductClick(Products products) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("pid", products.getPid());
        startActivity(intent);
    }
}