package com.example.s26g5;

public class Artifact_basic {
    private String accessionNumber; // year.accessiongrp.objectnum
    private String acquisitionMethod;
    private String artifactName;
    private String conditionReport;
    private String culturalOrigin;
    private int numberOfLikes;
    private String description;
    private String dimensions;
    private String dynasty;
    private String image;
    private String lotNumber; //UUID
    private String materials;
    private String notes;


    Artifact_basic(String lotNumber, String artifactName, String image, String culturalOrigin){ //to be displayed in grid
        this.lotNumber = lotNumber;
        this.artifactName = artifactName;
        this.image = image;
        this.culturalOrigin = culturalOrigin;
    }
    Artifact_basic(){
    }
    Artifact_basic(String lotNumber){
        this.lotNumber = lotNumber;
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
    public String getCulturalOrigin() {
        return culturalOrigin;
    }
    public void setCulturalOrigin(String culturalOrigin) {
        this.culturalOrigin = culturalOrigin;
    }
    public String getAccessionNumber() {
        return accessionNumber;
    }
    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getAcquisitionMethod() {
        return acquisitionMethod;
    }
    public void setAcquisitionMethod(String acquisitionMethod) {
        this.acquisitionMethod = acquisitionMethod;
    }
    public String getConditionReport() {
        return conditionReport;
    }
    public void setConditionReport(String conditionReport) {
        this.conditionReport = conditionReport;
    }
    public int getNumberOfLikes() {
        return numberOfLikes;
    }
    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDimensions() {
        return dimensions;
    }
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    public String getDynasty() {
        return dynasty;
    }
    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }
    public String getMaterials() {
        return materials;
    }
    public void setMaterials(String materials) {
        this.materials = materials;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

}