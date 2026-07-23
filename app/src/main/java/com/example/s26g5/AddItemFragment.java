package com.example.s26g5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.net.Uri;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ServerValue;

import android.util.Log;

public class AddItemFragment extends Fragment {
    private EditText editTextLotNumber;
    private EditText editTextArtifactName;
    private EditText editTextDescription;
    private EditText editTextImage;
    private EditText editTextCulturalOrigin;
    private EditText editTextDimensions;
    private EditText editTextConditionReport;
    private EditText editTextCurrentLocation;
    private EditText editTextAcquisitionMethod;
    private EditText editTextProvenance;
    private EditText editTextAccessionNumber;
    private EditText editTextNotes;


    private Spinner spinnerCategory;
    private Spinner spinnerMaterial;
    private Spinner spinnerDynasty;
    private Button buttonAdd;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    private UploadImagePicker uploadImagePicker;
    private String uploadedImageUrl;

    private static final String TAG = "AddItemFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uploadImagePicker = new UploadImagePicker(
                requireContext(),
                requireActivity().getActivityResultRegistry(),
                getLifecycle(),
                "add-item-image-picker",
                new UploadImagePicker.Callback() {
                    @Override
                    public void onImageSelected(Uri imageUri) {
                        if (editTextImage != null) {
                            editTextImage.setText("Uploading...");
                        }

                        if (buttonAdd != null) {
                            buttonAdd.setEnabled(false);
                        }
                    }

                    @Override
                    public void onUploadSuccess(String publicUrl) {
                        uploadedImageUrl = publicUrl;

                        Log.d(TAG, "Uploaded image URL: " + publicUrl);

                        if (editTextImage != null) {
                            editTextImage.setText("Image selected");
                        }

                        if (buttonAdd != null) {
                            buttonAdd.setEnabled(true);
                        }
                    }

                    @Override
                    public void onError(String message) {
                        uploadedImageUrl = null;

                        Log.e(TAG, "Image upload error: " + message);
                        if (editTextImage != null) {
                            editTextImage.setText("Upload failed — tap to retry");
                        }

                        if (buttonAdd != null) {
                            buttonAdd.setEnabled(true);
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
                        if (buttonAdd != null) {
                            buttonAdd.setEnabled(true);
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        editTextLotNumber = view.findViewById(R.id.editTextLotNumber);
        editTextArtifactName = view.findViewById(R.id.editTextArtifactName);
        spinnerMaterial = view.findViewById(R.id.spinnerMaterial);
        spinnerDynasty = view.findViewById(R.id.spinnerDynasty);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        editTextImage = view.findViewById(R.id.editTextImage);
        editTextImage.setOnClickListener(v -> {
            String lotNumber =
                    editTextLotNumber.getText().toString().trim();
            uploadImagePicker.selectAndUpload(lotNumber);
        });
        buttonAdd = view.findViewById(R.id.buttonAdd);
        editTextCulturalOrigin = view.findViewById(R.id.editTextCulturalOrigin);
        editTextDimensions = view.findViewById(R.id.editTextDimensions);
        editTextCurrentLocation = view.findViewById(R.id.editTextCurrentLocation);
        editTextAcquisitionMethod = view.findViewById(R.id.editTextAcquisitionMethod);
        editTextProvenance = view.findViewById(R.id.editTextProvenance);
        editTextAccessionNumber = view.findViewById(R.id.editTextAccessionNumber);
        editTextNotes = view.findViewById(R.id.editTextNotes);
        editTextConditionReport = view.findViewById(R.id.editTextConditionReport);



        db = FirebaseDatabase.getInstance();

        // Set up the spinner with categories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        ArrayAdapter<CharSequence> materialAdapter = ArrayAdapter.createFromResource(requireContext(),
                        R.array.materials_array, android.R.layout.simple_spinner_item);
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaterial.setAdapter(materialAdapter);

        ArrayAdapter<CharSequence> dynastyAdapter = ArrayAdapter.createFromResource(requireContext(),
                        R.array.dynasties_array, android.R.layout.simple_spinner_item);
        dynastyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDynasty.setAdapter(dynastyAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        return view;
    }

    private void addItem() {
        String lotNumber = editTextLotNumber.getText().toString().trim();
        String artifactName = editTextArtifactName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String materials = spinnerMaterial.getSelectedItem().toString().trim();
        String dynasty = spinnerDynasty.getSelectedItem().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString().toLowerCase();
        String image = uploadedImageUrl == null
                ? ""
                : uploadedImageUrl;
        Log.d(TAG, "Image value being saved: " + image);
        String CulturalOrigin = editTextCulturalOrigin.getText().toString().trim();
        String Dimensions = editTextDimensions.getText().toString().trim();
        String CurrentLocation = editTextCurrentLocation.getText().toString().trim();
        String AcquisitionMethod = editTextAcquisitionMethod.getText().toString().trim();
        String Provenance = editTextProvenance.getText().toString().trim();
        String AccessionNumber = editTextAccessionNumber.getText().toString().trim();
        String ConditionReport = editTextConditionReport.getText().toString().trim();
        String Notes = editTextNotes.getText().toString().trim();
        Object timestamp = ServerValue.TIMESTAMP;
        int numberOfLikes = 0;


        boolean materialNotSelected = spinnerMaterial.getSelectedItemPosition() == 0;
        boolean dynastyNotSelected = spinnerDynasty.getSelectedItemPosition() == 0;
        boolean categoryNotSelected = spinnerCategory.getSelectedItemPosition() == 0;

        if (lotNumber.isEmpty()
                || artifactName.isEmpty()
                || description.isEmpty()
                || materialNotSelected
                || dynastyNotSelected
                || categoryNotSelected) {

            Toast.makeText(
                    requireContext(),
                    "Please fill out all the Mandatory fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        DatabaseReference categoriesRef = db.getReference("categories");

        categoriesRef.get().addOnCompleteListener(checkTask -> {
            if (!checkTask.isSuccessful()) {
                String message = checkTask.getException() == null
                        ? "Could not check lot number"
                        : checkTask.getException().getMessage();
                Toast.makeText(
                        requireContext(),
                        message,
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            boolean duplicateFound = false;

            for (DataSnapshot categorySnapshot
                    : checkTask.getResult().getChildren()) {
                for (DataSnapshot itemSnapshot
                        : categorySnapshot.getChildren()) {
                    String existingLotNumber = itemSnapshot.child("lotNumber").getValue(String.class);
                    if (existingLotNumber != null && existingLotNumber.equalsIgnoreCase(lotNumber)) {
                        duplicateFound = true;
                        break;
                    }
                }

                if (duplicateFound) {
                    break;
                }
            }

            if (duplicateFound) {
                Toast.makeText(
                        requireContext(),
                        "This lot number already exists",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            itemsRef = db.getReference("categories").child(category);
            String id = itemsRef.push().getKey();

            if (id == null) {
                Toast.makeText(
                        requireContext(),
                        "Could not generate item ID",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Item item = new Item(
                    lotNumber,
                    materials,
                    artifactName,
                    dynasty,
                    image,
                    description,
                    CulturalOrigin,
                    Dimensions,
                    CurrentLocation,
                    AcquisitionMethod,
                    Provenance,
                    AccessionNumber,
                    ConditionReport,
                    Notes,
                    timestamp,
                    numberOfLikes
            );

            itemsRef.child(id)
                    .setValue(item)
                    .addOnCompleteListener(saveTask -> {
                        if (saveTask.isSuccessful()) {
                            Toast.makeText(
                                    requireContext(),
                                    "Item added",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            String message = saveTask.getException() == null
                                            ? "Failed to add item"
                                            : saveTask.getException().getMessage();
                            Toast.makeText(
                                    requireContext(),
                                    message,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        editTextLotNumber = null;
        editTextArtifactName = null;
        spinnerMaterial = null;
        spinnerDynasty = null;
        editTextDescription = null;
        editTextImage = null;
        spinnerCategory = null;
        buttonAdd = null;
        editTextCulturalOrigin = null;
        editTextDimensions = null;
        editTextCurrentLocation = null;
        editTextAcquisitionMethod = null;
        editTextProvenance = null;
        editTextAccessionNumber = null;
        editTextNotes = null;
        editTextConditionReport = null;

    }

}
