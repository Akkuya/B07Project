package com.example.s26g5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s26g5.item_viewing.ItemDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArtifactBrowserFragment extends Fragment {

    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference("artifacts");
    private RecyclerView recycler;
    private ArtifactAdapter adapter;
    private List<Item> artifactList;
    private int sort_field = 0; // 0 = default, 1 = lotNumber, 2 = timestamp
    private List<Item> allArtifacts;
    private String filterCategory;
    private String filterDynasty;
    private String filterMaterials;
    private String filterCulturalOrigin;
    private String filterCurrentLocation;
    private String filterAcquisitionMethod;
    private String filterConditionReport;
    private final int ITEMS_PER_PAGE = 9;
    private int currPage = 0;
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

        adapter = new ArtifactAdapter(artifactList, new ArtifactAdapter.OnArtifactClickListener(){
            @Override
            public void onArtifactClick(Item item, int position){
                Toast.makeText(getContext(), "Clicked: " + item.getLotNumber(), Toast.LENGTH_SHORT).show();
                loadFragment(ItemDetails.display(item.getLotNumber()));
            }
        });

        recycler.setAdapter(adapter);

        Button button_bk = view.findViewById(R.id.button_bk);
        Button button_prv = view.findViewById(R.id.button_prv);
        Button button_nxt = view.findViewById(R.id.button_nxt);
        Button button_filters = view.findViewById(R.id.button_filters);
        pageNum = view.findViewById(R.id.page_num);

        Spinner spinner = view.findViewById(R.id.sort_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_options, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort_field = position;
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button_bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });

        button_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilters();
            }
        });

        button_prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currPage > 0) {
                    currPage--;
                    refresh();
                }
            }
        });

        button_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPage++;
                refresh();
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
                    for (DataSnapshot itemChild : snapshot.getChildren()) {

                        Item artifact = itemChild.getValue(Item.class);
                        if (artifact != null) {
                            allArtifacts.add(artifact);
                        }

                    }
                    refresh();
                } else {
                    Log.e("ArtifactBrowserFragment", "Failed to load artifacts", task.getException());
                }
            }
        });
    }

    private void refresh() {
        List<Item> filtered = new ArrayList<>();
        for (Item artifact : allArtifacts) {
            if (matchesFilters(artifact)) {
                filtered.add(artifact);
            }
        }

        switch (sort_field) {
            case 0: break;
            case 1:
                filtered.sort(new Comparator<Item>() {
                    public int compare(Item artifact1, Item artifact2) {
                        return artifact1.getLotNumber().compareTo(artifact2.getLotNumber());
                    }
                });
                break;
            case 2:
                filtered.sort(new Comparator<Item>() {
                    public int compare(Item artifact1, Item artifact2) {
                        return ((Long) artifact1.getTimestamp()).compareTo((Long) artifact2.getTimestamp());
                    }
                });
                break;
        }

        if (currPage * ITEMS_PER_PAGE >= filtered.size() && currPage > 0) {
            currPage = (filtered.size() - 1) / ITEMS_PER_PAGE;
        }
        int from = currPage * ITEMS_PER_PAGE;
        int to = Math.min(from + ITEMS_PER_PAGE, filtered.size());

        artifactList.clear();
        artifactList.addAll(filtered.subList(from, to));
        pageNum.setText(String.valueOf(currPage + 1));
        adapter.notifyDataSetChanged();
    }

    private boolean matchesFilters(Item artifact) {
        return matches(artifact.getCategory(), filterCategory)
                && matches(artifact.getDynasty(), filterDynasty)
                && matches(artifact.getMaterials(), filterMaterials)
                && matches(artifact.getCulturalOrigin(), filterCulturalOrigin)
                && matches(artifact.getCurrentLocation(), filterCurrentLocation)
                && matches(artifact.getAcquisitionMethod(), filterAcquisitionMethod)
                && matches(artifact.getConditionReport(), filterConditionReport);
    }

    private boolean matches(String itemValue, String filter) {
        return filter == null || (itemValue != null && itemValue.equals(filter));
    }

    private void openFilters() {
        FilterDialogFragment dialog = FilterDialogFragment.newInstance(allArtifacts);
        dialog.setOnFiltersAppliedListener(new FilterDialogFragment.OnFiltersAppliedListener() {
            @Override
            public void onFiltersApplied(String category, String dynasty, String materials,
                                         String culturalOrigin, String currentLocation,
                                         String acquisitionMethod, String conditionReport) {
                filterCategory = category;
                filterDynasty = dynasty;
                filterMaterials = materials;
                filterCulturalOrigin = culturalOrigin;
                filterCurrentLocation = currentLocation;
                filterAcquisitionMethod = acquisitionMethod;
                filterConditionReport = conditionReport;
                currPage = 0;
                refresh();
            }
        });
        dialog.show(getParentFragmentManager(), "filters");
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
