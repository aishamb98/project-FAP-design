package com.project.shopping.Model;

public class ImageDataModel {


    private String result;
    private float confidence;


    public ImageDataModel(String result, float confidence) {
        this.result = result;
        this.confidence = confidence;
    }


    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}