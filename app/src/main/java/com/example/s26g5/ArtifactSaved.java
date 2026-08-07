package com.example.s26g5;
public class ArtifactSaved extends Artifact_basic {

    private Boolean isSaved;

    ArtifactSaved(String name, String lotNumber, String culturalOrigin, String image, boolean isSaved) {
        super(name,
                lotNumber,
                culturalOrigin,
                image);
        this.isSaved = isSaved;
    }

    public Boolean getIsSaved() {
        return isSaved;
    }
    public void setIsSaved(Boolean isSaved) {
        this.isSaved = isSaved;
    }
}