package com.example.s26g5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ArtifactBrowserFragment extends Fragment {

    public ArtifactBrowserFragment() {
        super(R.layout.artifact_browser_fragment);
    }

    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference("artifacts");
    //private static final int PAGE_SIZE = 12;

    private ArtifactAdapter adapter;
    private final List<List<ArtifactStringField>> pageCache = new ArrayList<>();
    private int currentPageIndex = -1;
    //private Long lastTimeCreated = null;
    //private String lastKey = null;

    @Nullable
    @Override
    public View onCreateVeiw(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        RecyclerView recycler = view.findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 3));


        adapter = new ArtifactAdapter(artifact -> {
            // TODO: handle tapping an artifact
        });
        recycler.setAdapter(adapter);

        view.findViewById(R.id.button_bk).setOnClickListener(v ->
                        getParentFragmentManager().popBackStack()
        );

        view.findViewById(R.id.button_nxt).setOnClickListener(v -> goToNextPage());
        view.findViewById(R.id.button_prv).setOnClickListener(v -> goToPreviousPage());

        EditText searchBar = view.findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            search(searchBar.getText().toString());
            return true;
        });
}
