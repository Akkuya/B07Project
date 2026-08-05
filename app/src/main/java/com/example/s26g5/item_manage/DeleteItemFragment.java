package com.example.s26g5.item_manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//public class DeleteItemFragment extends Fragment {
//    private EditText artifactName;
//    private Spinner spinnerCategory;
//    private Button buttonDelete;
//    private Button buttonBack;
//    private EditText lotNum;
//
//    private FirebaseDatabase db;
//    private DatabaseReference itemsRef;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_delete_item, container, false);
//
//        spinnerCategory = view.findViewById(R.id.spinnerCategory);
//        buttonDelete = view.findViewById(R.id.buttonDelete);
//        buttonBack = view.findViewById(R.id.buttonBack_d);
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
//                deleteItemByLotNumber();
//            }
//        });
//        buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadFragment(new ManageItemsFragment());
//            }
//        });
//
//        return view;
//    }
//    private void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
//
//    private void deleteItemByLotNumber(String lotNumber) {
//
//        if (lotNumber.isEmpty()) {
//            Toast.makeText(getContext(), "Unable to delete Item", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        itemsRef = db.getReference("artifacts/"+lotNumber);
//        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                boolean itemFound = false;
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Item item = snapshot.getValue(Item.class);
//                    if (item != null && item.getLotNumber().equalsIgnoreCase(lotNumber) && item.getArtifactName().equalsIgnoreCase(name)) {
//                        snapshot.getRef().removeValue().addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getContext(), "Failed to delete item", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        itemFound = true;
//                        break;
//                    }
//                }
//                if (!itemFound) {
//                    Toast.makeText(getContext(), "Item not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}