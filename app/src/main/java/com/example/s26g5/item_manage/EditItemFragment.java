package com.example.s26g5.item_manage;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s26g5.item_manage.EditItemFragment;
import com.example.s26g5.HomeFragment;
import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.user.UICallbackInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemFragment extends Fragment{
    Item item = null;
    //TextView category;
    EditText material;
    EditText dynasty;
    EditText culturalOrigin;
    EditText dimensions;
    EditText conditionReport;
    EditText desc;
    EditText currentLocation;
    EditText acquisition;
    EditText provenance;
    EditText accession;
    EditText notes;
    ImageView image;

    ImageButton buttonEdit;
    private Button buttonSave;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        material = view.findViewById(R.id.editItemDetMaterial);
        dynasty = view.findViewById(R.id.editItemDetDynasty);
        culturalOrigin = view.findViewById(R.id.editItemDetCulturalOrigin);
        dimensions = view.findViewById(R.id.editItemDetDimensions);
        conditionReport = view.findViewById(R.id.editItemDetConditionReport);
        desc = view.findViewById(R.id.editItemDetDesc);
        currentLocation = view.findViewById(R.id.editItemDetCurrentLocation);
        acquisition = view.findViewById(R.id.editItemDetAcquisition);
        provenance = view.findViewById(R.id.editItemDetProvenance);
        accession = view.findViewById(R.id.editItemDetAccession);
        notes = view.findViewById(R.id.editItemDetNotes);
        image = view.findViewById(R.id.imageViewItemD);
        buttonEdit = view.findViewById(R.id.editItem_e);
//
//        db = FirebaseDatabase.getInstance();
//
//        // Set up the spinner with categories
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.categories_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(adapter);
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteItemByTitle();
//            }
//        });
//        buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadFragment(new ManageItemsFragment());
//            }
//        });
//
        return view;
    }
//    private void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}
