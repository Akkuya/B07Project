package com.example.s26g5.item_manage;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.s26g5.ArtifactBrowserFragment;
import com.example.s26g5.data.UploadImagePicker;
import com.example.s26g5.item_manage.EditItemFragment;
import com.example.s26g5.HomeFragment;
import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.item_viewing.ItemDetails;
import com.example.s26g5.user.UICallbackInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemFragment extends Fragment implements UICallbackInterface{
    String lotNumber;
    Item item = null;
    EditText e_name;
    Spinner e_category;
    Spinner e_material;
    Spinner e_dynasty;
    EditText e_culturalOrigin;
    EditText e_dimensions;
    EditText e_conditionReport;
    EditText e_desc;
    EditText e_currentLocation;
    EditText e_acquisition;
    EditText e_provenance;
    EditText e_accession;
    EditText e_notes;
    ImageView e_image;

    private Button buttonSave;
    private Button buttonCancel;
    private UploadImagePicker uploadImagePicker;
    private String editedImageUrl;

    private static final String TAG = "EditItemFragment";


    public static EditItemFragment display(String lotNumber) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle parameters = new Bundle();
        parameters.putString("lotNumber", lotNumber);
        fragment.setArguments(parameters);
        return fragment;
    }

    @Override
    public void onSuccess(Object result) {
        if (!isAdded() || getView() == null) {
            return;
        }
        DataSnapshot itemJson = (DataSnapshot) result;
        item = itemJson.getValue(Item.class);

        e_name.setText(item.getArtifactName());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, R.layout.thin_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e_category.setAdapter(adapter);

        ArrayAdapter<CharSequence> materialAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.materials_array, R.layout.thin_spinner);
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e_material.setAdapter(materialAdapter);

        ArrayAdapter<CharSequence> dynastyAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.dynasties_array, R.layout.thin_spinner);
        dynastyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e_dynasty.setAdapter(dynastyAdapter);

        int pos_cat = adapter.getPosition(item.getCategory());
        if (pos_cat >= 0) {
            e_category.setSelection(pos_cat);
        }

        int pos_mat = materialAdapter.getPosition(item.getMaterials());
        if (pos_mat >= 0) {
            e_material.setSelection(pos_mat);
        }

        int pos_dyn = dynastyAdapter.getPosition(item.getDynasty());
        if (pos_dyn >= 0) {
            e_dynasty.setSelection(pos_dyn);
        }

        e_culturalOrigin.setText(item.getCulturalOrigin());
        e_dimensions.setText(item.getDimensions());
        e_conditionReport.setText(item.getConditionReport());
        e_desc.setText(item.getDescription());
        e_currentLocation.setText(item.getCurrentLocation());
        e_acquisition.setText(item.getAcquisitionMethod());
        e_provenance.setText(item.getProvenance());
        e_accession.setText(item.getAccessionNumber());
        e_notes.setText(item.getNotes());
        if(item.getImage() != null){
            Glide.with(this).load(item.getImage()).into(e_image);
        }
    }

    @Override
    public void onFailure(Object result) {
        if (!isAdded()) {
            return;
        }
        loadFragment(new ArtifactBrowserFragment());
        Toast.makeText(getContext(), "Error loading artifact for editing", Toast.LENGTH_SHORT).show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();

        lotNumber = getArguments().getString("lotNumber");
        db.getInfo("artifacts/" + lotNumber, EditItemFragment.this);

        e_name = view.findViewById(R.id.editItemDetTitle);
        e_category = view.findViewById(R.id.editItemDetCategory);
        e_material = view.findViewById(R.id.editItemDetMaterial);
        e_dynasty = view.findViewById(R.id.editItemDetDynasty);
        e_culturalOrigin = view.findViewById(R.id.editItemDetCulturalOrigin);
        e_dimensions = view.findViewById(R.id.editItemDetDimensions);
        e_conditionReport = view.findViewById(R.id.editItemDetConditionReport);
        e_desc = view.findViewById(R.id.editItemDetDesc);
        e_currentLocation = view.findViewById(R.id.editItemDetCurrentLocation);
        e_acquisition = view.findViewById(R.id.editItemDetAcquisition);
        e_provenance = view.findViewById(R.id.editItemDetProvenance);
        e_accession = view.findViewById(R.id.editItemDetAccession);
        e_notes = view.findViewById(R.id.editItemDetNotes);
        e_image = view.findViewById(R.id.imageViewItemD);
        buttonSave = view.findViewById(R.id.saveEdit);
        buttonCancel = view.findViewById(R.id.cancelEdit);

        // Set up the spinner with categories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, R.layout.thin_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e_category.setAdapter(adapter);

        ArrayAdapter<CharSequence> materialAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.materials_array, R.layout.thin_spinner);
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e_material.setAdapter(materialAdapter);

        ArrayAdapter<CharSequence> dynastyAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.dynasties_array, R.layout.thin_spinner);
        dynastyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e_dynasty.setAdapter(dynastyAdapter);

        uploadImagePicker = new UploadImagePicker(
                requireContext(),
                requireActivity().getActivityResultRegistry(),
                getLifecycle(),
                "edit-item-image-picker",
                new UploadImagePicker.Callback() {
                    @Override
                    public void onImageSelected(Uri imageUri) {
                        if (e_image != null) {

                        }
                        if (buttonSave != null) {
                            buttonSave.setEnabled(false);
                        }
                    }

                    @Override
                    public void onUploadSuccess(String publicUrl) {
                        editedImageUrl = publicUrl;

                        Log.d(TAG, "Uploaded image URL: " + publicUrl);

                        if (buttonSave != null) {
                            buttonSave.setEnabled(true);
                        }
                        Glide.with(EditItemFragment.this).load(publicUrl).into(e_image);
                    }

                    @Override
                    public void onError(String message) {
                        editedImageUrl = null;

                        Log.e(TAG, "Image upload error: " + message);

                        if (buttonSave != null) {
                            buttonSave.setEnabled(true);
                        }

                        if (isAdded()) {
                            Toast.makeText(
                                    requireContext(),
                                    message,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onSelectionCancelled() {
                        if (buttonSave != null) {
                            buttonSave.setEnabled(true);
                        }
                    }
                }
        );
        e_image.setOnClickListener(v -> uploadImagePicker.selectAndUpload(lotNumber));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdits();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
             public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }
    private void saveEdits(){
        if (item == null){
            return;
        }
        if((e_name.getText().toString()).isEmpty()
                || (e_desc.getText().toString()).isEmpty()
                || e_category.getSelectedItemPosition() == 0
                || e_material.getSelectedItemPosition() == 0
                || e_dynasty.getSelectedItemPosition() == 0){
            Toast.makeText(
                    requireContext(),
                    "Please fill out all the Mandatory fields",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        item.setArtifactName(e_name.getText().toString());
        item.setCategory(e_category.getSelectedItem().toString());
        item.setMaterials(e_material.getSelectedItem().toString().trim());
        item.setDynasty(e_dynasty.getSelectedItem().toString().trim());
        item.setCulturalOrigin(e_culturalOrigin.getText().toString());
        item.setDimensions(e_dimensions.getText().toString());
        item.setConditionReport(e_conditionReport.getText().toString());
        item.setDescription(e_desc.getText().toString());
        item.setCurrentLocation(e_currentLocation.getText().toString());
        item.setAcquisitionMethod(e_acquisition.getText().toString());
        item.setProvenance(e_provenance.getText().toString());
        item.setAccessionNumber(e_accession.getText().toString());
        item.setNotes(e_notes.getText().toString());

        if(editedImageUrl != null) {
            item.setImage(editedImageUrl);
        }
        buttonSave.setEnabled(false);

        FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();
        db.updateItem("artifacts/" + lotNumber, item, new UICallbackInterface() {
            @Override
            public void onSuccess(Object result) {
                if (!isAdded() || getView() == null) return;

                Toast.makeText(requireContext(), "Item updated", Toast.LENGTH_SHORT).show();
                loadFragment(ItemDetails.display(lotNumber));
            }

            @Override
            public void onFailure(Object result) {
                if (!isAdded() || getView() == null) return;
                Toast.makeText(requireContext(), "Failed to save changes", Toast.LENGTH_SHORT).show();
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
