package com.example.s26g5;

public class Item {

    private String id;
    private String lotNumber;
    private String artifactName;
    private String image;
    private String description;
    private String materials;
    private String dynasty;

    public Item() {
        // Required by Firebase
    }

    public Item(String id, String lotNumber, String materials, String artifactName, String dynasty, String image, String description) {
        this.id = id;
        this.lotNumber = lotNumber;
        this.materials = materials;
        this.artifactName = artifactName;
        this.dynasty = dynasty;
        this.image = image;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }
}