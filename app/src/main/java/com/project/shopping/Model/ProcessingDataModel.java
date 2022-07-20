package com.project.shopping.Model;

import java.util.List;

public class ProcessingDataModel {
    private Products products;
    private List<ImageDataModel> imageDataModel;


    public ProcessingDataModel(Products products, List<ImageDataModel> imageDataModel) {
        this.products = products;
        this.imageDataModel = imageDataModel;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public List<ImageDataModel> getImageDataModel() {
        return imageDataModel;
    }

    public void setImageDataModel(List<ImageDataModel> imageDataModel) {
        this.imageDataModel = imageDataModel;
    }
}
