package com.example.s26g5;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_artifacts")
public class SavedArtifactEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "lot_number")
    private final String lotNumber;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "cultural_origin")
    private String culturalOrigin;

    @ColumnInfo(name = "image")
    private String image;

    // Constructors, getters, and setters
    public SavedArtifactEntity(@NonNull String name, @NonNull String lotNumber, @NonNull String culturalOrigin, String image) {
        this.name = name;
        this.lotNumber = lotNumber;
        this.culturalOrigin = culturalOrigin;
        this.image = image;
    }

    // Getters and setters
    @NonNull
    public String getName() {
        return name;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    @NonNull
    public String getLotNumber() {
        return lotNumber;
    }
    /*public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }*/
    @NonNull
    public String getCulturalOrigin() {
        return culturalOrigin;
    }

    public String getImage() {
        return image;
    }

}
