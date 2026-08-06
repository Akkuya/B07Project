package com.example.s26g5.item_viewing;

import android.content.Context;

import com.example.s26g5.AppDatabase;
import com.example.s26g5.ArtifactSaved;

import java.util.ArrayList;
import java.util.List;

public class DashboardPresenter implements Dashboard.Presenter {
    private SavedArtifactsFragment savedArtifactsFragment;
    private final Context context;
    private DatabaseReference MyRef;
    private ValueEventListener listener;

    public DashboardPresenter(SavedArtifactsFragment savedArtifactsFragment, Context context) {
        this.savedArtifactsFragment = savedArtifactsFragment;
        this.context = context;
    }

    @Override
    public void loadSavedArtefacts() {
        Dashboard.View view = savedArtifactsFragment;
        clearLocalCache();

        if (savedArtifactsFragment == null) {
            return;
        }

                //To retrieve locally stored saved artifacts
                new Thread(() -> {
                    AppDatabase local_db = AppDatabase.getInstance(context);
                    List<SavedArtifactEntity> savedArtifacts = local_db.artifactDao().getAllSavedArtifacts();
                    List<ArtifactSaved> fetchedArtifacts = new ArrayList<>();
                    for (SavedArtifactEntity savedArtifact : savedArtifacts) {
                        ArtifactSaved UArtifact = new ArtifactSaved(savedArtifact.getName(), savedArtifact.getLotNumber(), savedArtifact.getCulturalOrigin(), savedArtifact.getImage(), true);
                        fetchedArtifacts.add(UArtifact);
                    }

        MyRef = FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/")
                        .getReference("users")
                        .child(uid)
                        .child("saved_artifacts");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.exists()) || !(snapshot.hasChildren())) {
                    clearLocalCache();
                    if (savedArtifactsFragment != null) {
                        view.showError("No saved artifacts found.");
                    }
                    return;
                }

                ArrayList<ArtifactSaved> fetchedArtefacts = new ArrayList<>();
                final long total = snapshot.getChildrenCount();
                final int[] completed = {0};

                for (DataSnapshot child : snapshot.getChildren()) {
                    String lotNumber = child.getKey();
                    if (lotNumber == null) {
                        completed[0]++;
                        checkCompletion(completed, total, fetchedArtefacts, view);
                        continue;
                    }

                    FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/")
                            .getReference("artifacts")
                            .child(lotNumber)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot artifactSnapshot) {
                                    Artifact_basic artifact = artifactSnapshot.getValue(Artifact_basic.class);
                                    if (artifact != null) {
                                        ArtifactSaved UIartifact = new ArtifactSaved(artifact.getArtifactName(),
                                                artifact.getLotNumber(),
                                                artifact.getCulturalOrigin(),
                                                artifact.getImage(),
                                                true);
                                        fetchedArtefacts.add(UIartifact);

                                        new Thread(() -> {
                                            AppDatabase local_db = AppDatabase.getInstance(context);
                                            local_db.artifactDao().insertSavedArtifact(new SavedArtifactEntity(artifact.getArtifactName(),
                                                    artifact.getLotNumber(),
                                                    artifact.getCulturalOrigin(),
                                                    artifact.getImage()));
                                        }).start();
                                    } else {
                                        MyRef.child(lotNumber).removeValue();
                                    }

                                    completed[0]++;
                                    checkCompletion(completed, total, fetchedArtefacts, view);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    completed[0]++;
                                    checkCompletion(completed, total, fetchedArtefacts, view);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (savedArtifactsFragment != null) {
                    view.showError(error.getMessage());
                }
                //if offline, load from local db
                loadFromLocalDB(view);

            }

            private void checkCompletion(int[] completed, long total, List<ArtifactSaved> fetchedArtefacts, Dashboard.View view) {
                if (completed[0] == total && savedArtifactsFragment != null) {
                    if (fetchedArtefacts.isEmpty()) {
                        view.showError("No saved artifacts found.");
                    } else {
                        view.showArtefacts(fetchedArtefacts);
                    }
                }
            }
        };
        MyRef.addValueEventListener(listener);
    }

    private void loadFromLocalDB(Dashboard.View view) {
        if (savedArtifactsFragment == null) {
            return;
        }
        new Thread(() -> {
            AppDatabase local_db = AppDatabase.getInstance(context);
            List<SavedArtifactEntity> savedArtifacts = local_db.artifactDao().getAllSavedArtifacts();
            List<ArtifactSaved> fetchedArtifacts = new ArrayList<>();
            for (SavedArtifactEntity savedArtifact : savedArtifacts) {
                ArtifactSaved UIartifact = new ArtifactSaved(savedArtifact.getName(),
                        savedArtifact.getLotNumber(), savedArtifact.getCulturalOrigin(),
                        savedArtifact.getImage(),
                        true);
                fetchedArtifacts.add(UIartifact);
            }
            if (fetchedArtifacts.isEmpty()) {
                view.showError("No saved artifacts found.");
            } else {
                view.showArtefacts(fetchedArtifacts);
            }
        }).start();
    }

    @Override
    public void detachView() {
        if (MyRef != null && listener != null) {
            MyRef.removeEventListener(listener);
        }
        this.savedArtifactsFragment = null;
    }

    public void clearLocalCache() {
        new Thread(() -> {
            AppDatabase local_db = AppDatabase.getInstance(context);
            local_db.artifactDao().clearAllSavedArtifacts();
        }).start();
    }
}
