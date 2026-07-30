package com.example.s26g5;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArtifactDataAccessObject {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertSavedArtifact(SavedArtifactEntity artifact);
    @Delete
    void deleteSavedArtifact(SavedArtifactEntity artifact);
    @Query("SELECT * FROM saved_artifacts")
    List<SavedArtifactEntity> getAllSavedArtifacts();

    @Query("SELECT EXISTS(SELECT 1 FROM saved_artifacts WHERE id = :id)")
    boolean isSaved(int id);
}
