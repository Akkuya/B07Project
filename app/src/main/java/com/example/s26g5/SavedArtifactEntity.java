package com.example.s26g5;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_artifacts")
public class SavedArtifactEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String lotNumber;
    private String culturalOrigin;
    private String image;
    // Constructors, getters, and setters
    public SavedArtifactEntity(String name, String lotNumber, String culturalOrigin, String image) {
        this.name = name;
        this.lotNumber = lotNumber;
        this.culturalOrigin = culturalOrigin;
        this.image = image;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLotNumber() {
        return lotNumber;
    }
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }
    public String getCulturalOrigin() {
        return culturalOrigin;
    }
    public void setCulturalOrigin(String culturalOrigin) {
        this.culturalOrigin = culturalOrigin;
    }
    public String getImage() {
        return image;
    }
}
