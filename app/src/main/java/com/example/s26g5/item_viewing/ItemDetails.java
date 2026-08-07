package com.example.s26g5.item_viewing;

import com.example.s26g5.ArtifactSaved;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s26g5.HomeFragment;
import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.user.UICallbackInterface;
import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class ItemDetails extends Fragment implements UICallbackInterface {
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

    ImageButton saveButton;

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
        try {
            item = itemJson.getValue(Item.class);
        } catch (Exception e) {
            Log.e("ItemDetails", "Error parsing item data", e);
            onFailure("Item data is malformed");
            return;
        }

        if (item == null) {
            onFailure("Item not found");
            return;
        }

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
        saveButton.setEnabled(true);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item == null) return;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Toast.makeText(getContext(), "Please login to save artifacts", Toast.LENGTH_SHORT).show();
                    return;
                }
                String uid = user.getUid();

                DatabaseReference savedRef = FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/")
                        .getReference("users")
                        .child(uid)
                        .child("saved_artifacts");

                savedRef.child(item.getLotNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(getContext(), "Artifact already saved", Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();
                            db.insertInfo("users/" + uid + "/saved_artifacts/" + item.getLotNumber(), item);
                            Toast.makeText(getContext(), "Artifact saved", Toast.LENGTH_SHORT).show();
                            saveButton.setSelected(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Check if already saved to update button state
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference savedRef = FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/")
                    .getReference("users")
                    .child(uid)
                    .child("saved_artifacts");
            savedRef.child(item.getLotNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        saveButton.setSelected(true);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
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
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setEnabled(false);

        return view;
    }
//file


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
