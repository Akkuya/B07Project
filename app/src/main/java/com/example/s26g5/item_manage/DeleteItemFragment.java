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

import com.example.s26g5.ArtifactBrowserFragment;
import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//public class DeleteItemFragment extends Fragment {
//    private TextView artifactName;
//    private Button buttonDelete;
//    private Button buttonCancel;
//    private Button buttonBack;
//    private TextView lotNum;
//    String lotNumber;
//
//    private FirebaseDatabase db;
//    private DatabaseReference itemsRef;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_delete_item, container, false);
//
//        buttonDelete = view.findViewById(R.id.buttonDelete_d);
//        buttonCancel = view.findViewById(R.id.buttonCancel_d);
//        buttonBack = view.findViewById(R.id.buttonBack_d);
//        lotNum = view.findViewById(R.id.lotNumber_d);
//        artifactName = view.findViewById(R.id.itemName_d);
//
//        db = FirebaseDatabase.getInstance();
//
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteItemByLotNumber(String.valueOf(lotNum));
//            }
//        });
//        buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getParentFragmentManager().popBackStack();
//            }
//        });
//        buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadFragment(new ArtifactBrowserFragment());
//            }
//        });
//
//        return view;
//    }
//    @override
//    public void onViewCreated(View view, Bundle savedInstanceState){
//        super.onViewCreated(view, savedInstanceState);
//
//        lotNumber = getArguments() != null ? getArguments().getString("lotNumber") : null;
//        artifactName.setText(getArguments().getString("artifactName"));
//        lotNum.setText(getArguments().getString("lotNumber"));
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
//                    if (item != null && item.getLotNumber().equalsIgnoreCase(lotNumber)) {
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