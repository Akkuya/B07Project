package com.example.s26g5.item_viewing;

import android.app.AlertDialog;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.s26g5.ArtifactBrowserFragment;
//import com.example.s26g5.item_manage.DeleteItemFragment;
import com.example.s26g5.item_manage.EditItemFragment;
import com.example.s26g5.HomeFragment;
import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.user.SessionManager;
import com.example.s26g5.user.UICallbackInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemDetails extends Fragment implements UICallbackInterface {
    Item item = null;
    TextView name;
    TextView category;
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

    ImageButton buttonEdit;
    ImageButton buttonDelete;
    private DatabaseReference itemsRef;

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

        name.setText(item.getArtifactName());
        category.setText(item.getCategory());
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
        //image.setImageURI(Uri.parse(item.getImage()));
        if(item.getImage() != null){
            Glide.with(this).load(item.getImage()).into(image);
        }

        if(SessionManager.getSessionInstance().isAdmin()) {//TODO: Make sure up to date with correct session syntax
            buttonEdit.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.VISIBLE);

            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(EditItemFragment.display(item.getLotNumber()));
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Delete Artifact")
                            .setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                deleteItemByLotNumber(item.getLotNumber());
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }
        else{
            buttonEdit.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
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

        name = view.findViewById(R.id.textViewItemDetTitle);
        category = view.findViewById(R.id.textViewItemDetCategory);
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
        buttonEdit = view.findViewById(R.id.editItem);
        buttonDelete = view.findViewById(R.id.deleteItem);

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void deleteItemByLotNumber(String lotNumber) {
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();

        if (lotNumber.isEmpty() || lotNumber == null) {
            Toast.makeText(getContext(), "Unable to delete Item", Toast.LENGTH_SHORT).show();
            return;
        }

        itemsRef = database.getReference("artifacts/" + lotNumber);
        itemsRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!isAdded() || getView() == null) {
                    return;
                }
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                loadFragment(new ArtifactBrowserFragment());
            } else {
                if (!isAdded() || getView() == null) {
                    return;
                }
                Toast.makeText(getContext(), "Failed to delete item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
