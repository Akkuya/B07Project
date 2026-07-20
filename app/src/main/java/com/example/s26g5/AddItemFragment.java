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

import android.util.Log;

public class AddItemFragment extends Fragment {
    private EditText editTextLotNumber;
    private EditText editTextArtifactName;
    private EditText editTextDescription;
    private EditText editTextImage;

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

        db = FirebaseDatabase.getInstance();

        // Set up the spinner with categories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        ArrayAdapter<CharSequence> materialAdapter =
                ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.materials_array,
                        android.R.layout.simple_spinner_item);
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaterial.setAdapter(materialAdapter);

        ArrayAdapter<CharSequence> dynastyAdapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.dynasties_array,
                        android.R.layout.simple_spinner_item);
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
                    "Please fill out all fields",
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
                id,
                lotNumber,
                materials,
                artifactName,
                dynasty,
                image,
                description
        );

        itemsRef.child(id)
                .setValue(item)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(
                                requireContext(),
                                "Item added",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        String message = task.getException() == null
                                ? "Failed to add item"
                                : task.getException().getMessage();

                        Toast.makeText(
                                requireContext(),
                                message,
                                Toast.LENGTH_LONG
                        ).show();
                    }
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
    }

}
