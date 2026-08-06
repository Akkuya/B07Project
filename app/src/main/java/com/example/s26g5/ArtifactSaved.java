package com.example.s26g5;
public class ArtifactSaved extends Artifact_basic {

    private Boolean isSaved;

    ArtifactSaved(String name, String lotNumber, String culturalOrigin, String image, Boolean isSaved) {
        super(name,
                lotNumber,
                culturalOrigin,
                image);
        this.isSaved = isSaved;
    }

    public ArtifactSaved(String name, String lotNumber, Boolean isSaved) {
        super(name,
                lotNumber);
        this.isSaved = isSaved;
    }

    public Boolean getIsSaved() {
        return isSaved;
    }
    public void setIsSaved(Boolean isSaved) {
        this.isSaved = isSaved;
    }
}