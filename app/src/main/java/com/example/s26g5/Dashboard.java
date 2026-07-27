package com.example.s26g5;

import java.util.List;

public interface Dashboard {
    interface View {
        void showArtefacts(List<Artifact_basic> artefacts);
        void showError(String message);
    }

    // Methods the View can call ON the Presenter — the Presenter implements these
    interface Presenter {
        void loadSavedArtefacts();
        void detachView();
    }
}
