package com.example.s26g5;

public class Item {

    private String lotNumber;
    private String artifactName;
    private String image;
    private String description;
    private String materials;
    private String dynasty;
    private String culturalOrigin;
    private String dimensions;
    private String currentLocation;
    private String acquisitionMethod;
    private String provenance;
    private String accessionNumber;
    private String conditionReport;
    private String notes;
    private Object timestamp;
    private int numberOfLikes;

    public Item() {
        // Required by Firebase
    }

    public Item(String lotNumber, String materials, String artifactName, String dynasty, String image, String description, String CulturalOrigin, String Dimensions,
                String CurrentLocation, String AcquisitionMethod, String Provenance, String AccessionNumber, String ConditionReport, String Notes, Object timestamp, int numberOfLikes) {

        this.lotNumber = lotNumber;
        this.materials = materials;
        this.artifactName = artifactName;
        this.dynasty = dynasty;
        this.image = image;
        this.description = description;
        this.culturalOrigin = CulturalOrigin;
        this.dimensions = Dimensions;
        this.currentLocation = CurrentLocation;
        this.acquisitionMethod = AcquisitionMethod;
        this.accessionNumber = AccessionNumber;
        this.provenance = Provenance;
        this.conditionReport = ConditionReport;
        this.notes = Notes;
        this.timestamp = timestamp;
        this.numberOfLikes = 0;
    }


    public String getLotNumber() {return lotNumber;}
    public void setLotNumber(String lotNumber) {this.lotNumber = lotNumber;}
    public String getArtifactName() {return artifactName;}
    public void setArtifactName(String artifactName) {this.artifactName = artifactName;}
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getMaterials() {return materials;}
    public void setMaterials(String materials) {this.materials = materials;}
    public String getDynasty() {return dynasty;}
    public void setDynasty(String dynasty) {this.dynasty = dynasty;}
    public String getCulturalOrigin() {return culturalOrigin;}
    public void setCulturalOrigin(String culturalOrigin) {this.culturalOrigin = culturalOrigin;}
    public String getDimensions() {return dimensions;}
    public void setDimensions(String dimensions) {this.dimensions = dimensions;}
    public String getCurrentLocation() {return currentLocation;}
    public void setCurrentLocation(String currentLocation) {this.currentLocation = currentLocation;}
    public String getAcquisitionMethod() {return acquisitionMethod;}
    public void setAcquisitionMethod(String acquisitionMethod) {this.acquisitionMethod = acquisitionMethod;}
    public String getProvenance() {return provenance;}
    public void setProvenance(String provenance) {this.provenance = provenance;}
    public String getAccessionNumber() {return accessionNumber;}
    public void setAccessionNumber(String accessionNumber) {this.accessionNumber = accessionNumber;}
    public String getConditionReport() {return conditionReport;}
    public void setConditionReport(String conditionReport) {this.conditionReport = conditionReport;}
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    public Object getTimestamp() {return timestamp;}
    public void setTimestamp(Object timestamp) {this.timestamp = timestamp;}
    public int getNumberOfLikes() {return numberOfLikes;}
    public void setNumberOfLikes(int numberOfLikes) {this.numberOfLikes = numberOfLikes;}
}