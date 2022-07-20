package com.project.shopping.util;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shopping.HomeActivity;
import com.project.shopping.Model.Products;
import com.project.shopping.ProductDetailsActivity;
import com.project.shopping.R;
import com.project.shopping.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchByImageAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Products> productsList;
    private ProductsListener listener;

    public SearchByImageAdapter(List<Products> productsList,ProductsListener listener) {
        this.productsList = productsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.product_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products model = productsList.get(position);
        holder.txtProductName.setText(model.getPname());
        holder.txtProductDescription.setText(model.getDescription());
        holder.txtProductPrice.setText("Price = " + model.getPrice() + "Rs.");
        Picasso.get().load(model.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProductClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public interface ProductsListener{
        void onProductClick(Products products);
    }
}
