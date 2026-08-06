package com.example.s26g5.item_viewing;

import android.content.Context;

import com.example.s26g5.AppDatabase;
import com.example.s26g5.ArtifactSaved;

import java.util.ArrayList;
import java.util.List;

public class DashboardPresenter implements Dashboard.Presenter {
    private SavedArtifactsFragment savedArtifactsFragment;
    private final Context context;

    public DashboardPresenter(SavedArtifactsFragment savedArtifactsFragment, Context context) {
        this.savedArtifactsFragment = savedArtifactsFragment;
        this.context = context;
    }

    @Override
    public void loadSavedArtefacts() {
        Dashboard.View view;
        view = savedArtifactsFragment;
        //DatabaseReference MyRef;
        //MyRef = FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/").getReference("categories");
        //MyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            /*public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ArtifactSaved> fetchedArtefacts = new ArrayList<>();

                for (DataSnapshot categorySnapshot : snapshot.getChildren()) { //cycles through each artifact
                    Artifact_basic artifact = categorySnapshot.getValue(Artifact_basic.class);
                    if (artifact != null) {
                        //converts fetched artifact to UI artifact
                        ArtifactSaved UIartifact = new ArtifactSaved(artifact.getArtifactName(), artifact.getLotNumber(), artifact.getCulturalOrigin(), artifact.getImage(), false);
                        fetchedArtefacts.add(UIartifact);
                    }
                }
                }*/

                //To retrieve locally stored saved artifacts
                new Thread(() -> {
                    AppDatabase local_db = AppDatabase.getInstance(context);
                    List<SavedArtifactEntity> savedArtifacts = local_db.artifactDao().getAllSavedArtifacts();
                    List<ArtifactSaved> fetchedArtifacts = new ArrayList<>();
                    for (SavedArtifactEntity savedArtifact : savedArtifacts) {
                        ArtifactSaved UArtifact = new ArtifactSaved(savedArtifact.getName(), savedArtifact.getLotNumber(), savedArtifact.getCulturalOrigin(), savedArtifact.getImage(), true);
                        fetchedArtifacts.add(UArtifact);
                    }

                    if (fetchedArtifacts.isEmpty()) {
                        view.showError("No saved artifacts found.");
                    }else {
                        view.showArtefacts(fetchedArtifacts);
                    }
                }).start();
            }


           /* @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showError(error.getMessage());
            } */



    @Override
    public void detachView() {
        this.savedArtifactsFragment = null;
    }
}