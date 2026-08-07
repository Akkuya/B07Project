package com.example.s26g5;

import java.util.ArrayList;
import java.util.List;
public class Item {

    private String lotNumber;
    private String artifactName;
    private String artifactNameLower;
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
    private long timestamp;
    private List<String> liked;
    private String key;
    private String category;

    public Item() {
        // Required by Firebase
    }

    public Item(String lotNumber, String materials, String artifactName, String dynasty, String image, String description, String CulturalOrigin, String Dimensions,
                String CurrentLocation, String AcquisitionMethod, String Provenance, String AccessionNumber, String ConditionReport, String Notes, long timestamp, List<String> liked) {

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
        this.liked = liked == null ? new ArrayList<>() : liked;
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
    public void setTimestamp(long timestamp) {this.timestamp = timestamp;}
    public List<String> getLiked() {
        if (liked == null) {
            liked = new ArrayList<>();
        }
        return liked;
    }
    public void setLiked(List<String> liked) {this.liked = liked == null ? new ArrayList<>() : liked;}
    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
}
