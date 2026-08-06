package com.example.s26g5;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

public class ArtifactBrowserFragment extends Fragment {

    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference("artifacts");
    private RecyclerView recycler;
    private ArtifactAdapter adapter;
    private List<Item> artifactList;
    private final int ITEMS_PER_PAGE = 12;
    private int currPage = 0;
    private List<List<Item>> pageCache = new ArrayList<>();
    private String lastlotNumber = null;
    private String lastKey = null;
    private TextView pageNum;
    private boolean isSearchActive = false;
    private EditText searchCriteria;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artifact_browser_fragment, container, false);

        recycler = view.findViewById(R.id.recycler_artifacts);
        searchCriteria = view.findViewById(R.id.search_bar);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2); //temp change from span = 3 to 2 to match saved artifacts
        recycler.setLayoutManager(layoutManager);

        artifactList = new ArrayList<>();
        searchCriteria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                if (query.isEmpty()) {
                    isSearchActive = false;
                    loadPageFromFirebase(currPage);
                } else {
                    isSearchActive = true;
                    search(query);
                }

            }
        });





        loadPageFromFirebase(currPage);//Get First Page of data from database

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

    private void search(String query){
        query = query.toLowerCase();
        db.orderByChild("artifactNameLower")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .limitToFirst(ITEMS_PER_PAGE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("ArtifactBrowserFragment", "Error getting data", task.getException());
                            return;
                        }
                        if (!isSearchActive) return;

                        DataSnapshot snapshot = task.getResult();
                        if (snapshot == null) return;

                        Log.d("ArtifactBrowserFragment", "Search results: " + snapshot.getChildrenCount());
                        artifactList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Item artifact = child.getValue(Item.class);
                            if (artifact != null) {
                                artifact.setKey(child.getKey());
                                artifactList.add(artifact);
                            }
                        }
                        pageNum.setText(artifactList.isEmpty() ? "0" : String.valueOf(currPage + 1));
                        adapter.notifyDataSetChanged();
                    }
                    });
                }
}
