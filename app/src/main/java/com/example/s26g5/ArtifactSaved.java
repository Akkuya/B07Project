package com.example.s26g5;

import com.example.s26g5.item_viewing.Artifact_basic;

public class ArtifactSaved extends Artifact_basic {

    private boolean isSaved;

    public ArtifactSaved(String name, String lotNumber, String culturalOrigin, String image, Boolean isSaved) {
        super(name,
                lotNumber,
                culturalOrigin,
                image);
        this.isSaved = isSaved;
    }

    public ArtifactSaved() {
        super();
    }

    public boolean getIsSaved() {
        return isSaved;
    }
    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }
}