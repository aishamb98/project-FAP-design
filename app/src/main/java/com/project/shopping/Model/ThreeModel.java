package com.project.shopping.Model;

import android.net.Uri;

public class ThreeModel {
    private final String modelName;
    private final Integer modelImage;
    private final Uri model3D;
    private final float scale;

    public ThreeModel(String modelName, Integer modelImage, Uri model3D, float scale) {
        this.modelName = modelName;
        this.modelImage = modelImage;
        this.model3D = model3D;
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public String getModelName() {
        return modelName;
    }

    public Integer getModelImage() {
        return modelImage;
    }

    public Uri getModel3D() {
        return model3D;
    }
}
