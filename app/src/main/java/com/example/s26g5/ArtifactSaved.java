package com.example.s26g5;
public class ArtifactSaved extends Artifact_basic {

    private boolean isSaved;

    ArtifactSaved(String name, String lotNumber, String culturalOrigin, String image, boolean isSaved) {
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