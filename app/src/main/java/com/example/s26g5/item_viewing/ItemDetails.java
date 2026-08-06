package com.example.s26g5.item_viewing;

import com.example.s26g5.ArtifactAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s26g5.HomeFragment;
import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.user.UICallbackInterface;
import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ItemDetails extends Fragment implements UICallbackInterface {
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference("artifacts");
    Item item = null;
    TextView itemName;
    //TextView category;
    TextView material;
    TextView dynasty;
    TextView culturalOrigin;
    TextView dimensions;
    TextView conditionReport;
    TextView desc;
    TextView currentLocation;
    TextView acquisition;
    TextView provenance;
    TextView accession;
    TextView notes;
    ImageView image;

    private ArrayList<Item> items;

    private ArtifactAdapter adapter;

    // Use ItemDetails.display(some_lot_number) to show customized page. Don't use `new`
    public static ItemDetails display(String lotNumber) {
        ItemDetails fragment = new ItemDetails();

        // add params to savedInstanceState
        Bundle parameters = new Bundle();
        parameters.putString("lotNumber", lotNumber);
        fragment.setArguments(parameters);

        return fragment;
    }

    @Override
    public void onSuccess(Object result) {
        DataSnapshot itemJson = (DataSnapshot) result;
        item = itemJson.getValue(Item.class);

        itemName.setText(item.getArtifactName());
//        category.setText(item.get);
        material.setText(item.getMaterials());
        dynasty.setText(item.getDynasty());
        culturalOrigin.setText(item.getCulturalOrigin());
        dimensions.setText(item.getDimensions());
        conditionReport.setText(item.getConditionReport());
        desc.setText(item.getDescription());
        currentLocation.setText(item.getCurrentLocation());
        acquisition.setText(item.getAcquisitionMethod());
        provenance.setText(item.getProvenance());
        accession.setText(item.getAccessionNumber());
        notes.setText(item.getNotes());
        Picasso.get()
                .load(item.getImage())
                .fit()
                .centerCrop()
                .into(image);
        loadRelated(item);

    }

    @Override
    public void onFailure(Object result) {
        loadFragment(new HomeFragment()); // TODO: Change from homepage to artifact broswer once merged
        Toast.makeText(
                        getContext(),
                        "Error finding artifact. Check again later",
                        Toast.LENGTH_SHORT)
                .show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();
        RecyclerView related = view.findViewById(R.id.related_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        related.setLayoutManager(layoutManager);


        items = new ArrayList<Item>();

        adapter = new ArtifactAdapter(items);
        related.setAdapter(adapter);


        String lotNumber = getArguments().getString("lotNumber");
        db.getInfo("artifacts/"+lotNumber, ItemDetails.this);

        itemName = view.findViewById(R.id.textViewItemDetTitle);
        //        TextView category = view.findViewById(R.id.textViewItemDetCategory);
        material = view.findViewById(R.id.textViewItemDetMaterial);
        dynasty = view.findViewById(R.id.textViewItemDetDynasty);
        culturalOrigin = view.findViewById(R.id.textViewItemDetCulturalOrigin);
        dimensions = view.findViewById(R.id.textViewItemDetDimensions);
        conditionReport = view.findViewById(R.id.textViewItemDetConditionReport);
        desc = view.findViewById(R.id.textViewItemDetDesc);
        currentLocation = view.findViewById(R.id.textViewItemDetCurrentLocation);
        acquisition = view.findViewById(R.id.textViewItemDetAcquisition);
        provenance = view.findViewById(R.id.textViewItemDetProvenance);
        accession = view.findViewById(R.id.textViewItemDetAccession);
        notes = view.findViewById(R.id.textViewItemDetNotes);
        image = view.findViewById(R.id.imageViewItemD);
        return view;
    }

    private void loadRelated(Item artifact) {
        String selected_category = artifact.getCategory();
        db.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    items.clear();
                    for (DataSnapshot itemChild : snapshot.getChildren()) {
                        Item db_artifact = itemChild.getValue(Item.class);
                        if (db_artifact != null && selected_category != null && selected_category.equals(db_artifact.getCategory()) && !Objects.equals(db_artifact.getLotNumber(), artifact.getLotNumber())) {
                            items.add(db_artifact);
                        }
                    }

                    while (items.size() > 3) {
                        items.remove(items.size() - 1);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("ItemDetails", "There was a problem querying the DB", task.getException());
                }
            }
        });

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
