package com.example.s26g5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArtifactBrowserFragment extends Fragment {

    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference("categories");
    private RecyclerView recycler;
    private ArtifactAdapter adapter;
    private List<Item> artifactList;
    private int sort_field = 0; // 0 = default, 1 = lotNumber, 2 = timestamp
    private List<Item> allArtifacts;
    private final int ITEMS_PER_PAGE = 12;
    private int currPage = 0;
    private List<List<Item>> pageCache = new ArrayList<>();
    private String lastlotNumber = null;
    private String lastKey = null;
    private TextView pageNum;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artifact_browser_fragment, container, false);

        recycler = view.findViewById(R.id.recycler_artifacts);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        recycler.setLayoutManager(layoutManager);

        artifactList = new ArrayList<>(); // Will only hold artifacts that match the filters and sort.
        allArtifacts = new ArrayList<>(); // Will hold all artifacts

        loadAllArtifacts(); // Populate allArtifacts

        adapter = new ArtifactAdapter(artifactList);
        recycler.setAdapter(adapter);

        Button button_bk = view.findViewById(R.id.button_bk);
        Button button_prv = view.findViewById(R.id.button_prv);
        Button button_nxt = view.findViewById(R.id.button_nxt);
        pageNum = view.findViewById(R.id.page_num);


        button_bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });

        button_prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currPage > 0) {
                    currPage--;
                    loadPageFromFirebase(currPage);
                }
            }
        });

        button_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage++;
                loadPageFromFirebase(currPage);
            }
        });

        return view;
    }

    private void loadAllArtifacts() {
        db.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    allArtifacts.clear();
                    for (DataSnapshot category : snapshot.getChildren()) {
                        for (DataSnapshot itemChild : category.getChildren()) {
                            Item artifact = itemChild.getValue(Item.class);
                            if (artifact != null) {
                                artifact.setKey(itemChild.getKey());
                                allArtifacts.add(artifact);
                            }
                        }

                        refresh();
                    }
                } else {
                    Log.e("ArtifactBrowserFragment", "Failed to load artifacts", task.getException());
                }
            }
        });
    }

    private void refresh() {
        artifactList.clear();
        artifactList.addAll(allArtifacts);

        switch (sort_field) {
            case 0: break;
            case 1:
                artifactList.sort(new Comparator<Item>() {
                    public int compare(Item artifact1, Item artifact2) {
                        return artifact1.getLotNumber().compareTo(artifact2.getLotNumber());
                    }
                });
                break;
            case 2:
                artifactList.sort(new Comparator<Item>() {
                    public int compare(Item artifact1, Item artifact2) {
                        return ((Long) artifact1.getTimestamp()).compareTo((Long) artifact2.getTimestamp());
                    }
                });
                break;
        }




    }
    private void loadPageFromFirebase(int page) {

        if (page == 0) {
            if(!pageCache.isEmpty()){
                artifactList.clear();
                artifactList.addAll(pageCache.get(page));
                pageNum.setText(String.valueOf(currPage + 1));
                adapter.notifyDataSetChanged();
                return;
            }
            db.orderByChild("lotNumber")
                    .limitToFirst(ITEMS_PER_PAGE)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                DataSnapshot snapshot = task.getResult();
                                artifactList.clear();
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    Item artifact = child.getValue(Item.class);
                                    if (artifact != null) {
                                        artifact.setKey(child.getKey());
                                        artifactList.add(artifact);
                                        lastKey = child.getKey();
                                        lastlotNumber = artifact.getLotNumber();
                                    }
                                }
                                pageCache.add(new ArrayList<>(artifactList));
                                pageNum.setText(String.valueOf(currPage + 1));
                                adapter.notifyDataSetChanged();
                            }else {
                                Log.e("ArtifactBrowserFragment", "Failed to load page " + currPage, task.getException());
                            }
                        }
                    });
        } else if (page > 0 && page < pageCache.size()) {
            artifactList.clear();
            List<Item> cachedPage = pageCache.get(page);
            artifactList.addAll(cachedPage);
            if(!cachedPage.isEmpty()){
                int last_idx = cachedPage.size() - 1;
                Item last = cachedPage.get(last_idx);
                lastlotNumber = last.getLotNumber();
                lastKey = last.getKey();
            }
            pageNum.setText(String.valueOf(currPage + 1));
            adapter.notifyDataSetChanged();
            
        } else{
            db.orderByChild("lotNumber")
                    .startAt(lastlotNumber)
                    .limitToFirst(ITEMS_PER_PAGE + 1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                DataSnapshot snapshot = task.getResult();
                                artifactList.clear();
                                int count = 0;
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    Item artifact = child.getValue(Item.class);
                                    if(artifact != null) {
                                        if(count == 0 && child.getKey().equals(lastKey)){
                                            count++;
                                        }
                                        else{
                                            artifact.setKey(child.getKey());
                                            artifactList.add(artifact);
                                            lastKey = child.getKey();
                                            lastlotNumber = artifact.getLotNumber();
                                        }
                                    }
                                }
                                pageCache.add(new ArrayList<>(artifactList));
                                pageNum.setText(String.valueOf(currPage + 1));
                                adapter.notifyDataSetChanged();
                            }else {
                                Log.e("ArtifactBrowserFragment", "Failed to load page " + currPage, task.getException());
                            }
                        }
                    });
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
