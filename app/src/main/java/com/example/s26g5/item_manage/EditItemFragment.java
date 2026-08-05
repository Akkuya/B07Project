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
    private EditText artifactName;
    private Spinner spinnerCategory;
    private EditText lotNum;

    private ImageButton buttonDelete;
    private Button buttonSave;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
//
//        spinnerCategory = view.findViewById(R.id.spinnerCategory);
//        buttonDelete = view.findViewById(R.id.buttonDelete);
//        buttonBack = view.findViewById(R.id.buttonBack_e);
//        lotNum = view.findViewById(R.id.lotNumber_d);
//        artifactName = view.findViewById(R.id.itemName_d);
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
