package com.example.s26g5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.s26g5.item_manage.AddItemFragment;
import com.example.s26g5.item_viewing.ArtifactBrowserFragment;
import com.example.s26g5.MainActivity;


public class AdminDashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ((MainActivity) requireActivity()).setNavigationVisible(true);
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        Button browseArtifactsButton = view.findViewById(R.id.browseArtifactsButton);
        Button addArtifactButton = view.findViewById(R.id.addArtifactButton);

        browseArtifactsButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).openArtifactBrowser();
        });
        addArtifactButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).openAddArtifactPage();
        });

        return view;
    }
}