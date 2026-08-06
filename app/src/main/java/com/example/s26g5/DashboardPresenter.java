package com.example.s26g5;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            view.showError("User not logged in.");
            loadFromLocalDB(view);
            return;
        }
        String uid = user.getUid();

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