package com.example.s26g5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserDashboardFragment extends Fragment implements Dashboard.View{
    private Dashboard.Presenter presenter;
    private ArtefactAdapter adapter;
    private RecyclerView recyclerView;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_dashboard, container, false);
    }

    @Override
    public void onViewCreated(android.view.View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewSaved);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // adapter setup goes here once you've built it
    }

    @Override
    public void showArtefacts(List<Artifact_basic> artefacts) {

    }

    @Override
    public void showError(String message) {

    }
}
