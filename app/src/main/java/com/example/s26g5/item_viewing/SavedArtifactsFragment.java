package com.example.s26g5.item_viewing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s26g5.ArtifactSaved;
import com.example.s26g5.R;

import java.util.List;

/**
 * Displays saved artifacts in a grid
 * Implements {@link Dashboard.View} to interact with the {@link Dashboard.Presenter}.
 */
public class SavedArtifactsFragment extends Fragment implements Dashboard.View {
    
    private Dashboard.Presenter presenter;
    private SavedArtifactAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_artifacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recyclerViewSaved);

        // grid layout with 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        // Initialize the presenter
        presenter = new DashboardPresenter(this, requireContext());
        presenter.loadSavedArtefacts();
    }

    @Override
    public void showArtefacts(List<ArtifactSaved> artefacts) {
        if (isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                adapter = new SavedArtifactAdapter(artefacts);
                recyclerView.setAdapter(adapter);
            });
        }
    }

    @Override
    public void showError(String message) {
        if (isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
