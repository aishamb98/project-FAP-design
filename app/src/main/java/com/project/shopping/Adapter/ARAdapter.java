package com.project.shopping.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shopping.Model.ThreeModel;
import com.project.shopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ARAdapter extends RecyclerView.Adapter<ARAdapter.ARViewHolder>{

    private List<ThreeModel> threeModelList;
    private ARClick listener;

    public ARAdapter(List<ThreeModel> threeModelList, ARClick listener) {
        this.threeModelList = threeModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ARViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ARViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ARViewHolder holder, int position) {
        holder.bind(threeModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return threeModelList.size();
    }

    public class ARViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ARViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.text);

        }

        void bind(ThreeModel threeModel){
            textView.setText(threeModel.getModelName());
            Picasso.get().load(threeModel.getModelImage()).into(imageView);
            itemView.setOnClickListener(view -> listener.on3DClick(threeModel));
        }

    }

    public interface ARClick{
        void on3DClick(ThreeModel threeModel);
    }
}
