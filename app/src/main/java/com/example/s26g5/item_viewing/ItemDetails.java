package com.example.s26g5.item_viewing;

import com.example.s26g5.user.SessionManager;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s26g5.HomeFragment;
import com.example.s26g5.Item;
import com.example.s26g5.R;
import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.user.UICallbackInterface;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemDetails extends Fragment implements UICallbackInterface {
    Item item = null;
    TextView itemName;
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

    EditText comment;
    AppCompatImageButton post;

    CommentAdapter commentAdapter;
    List<Comment> commentList;
    DatabaseReference commentsRef;
    FirebaseDBManager db;
    SessionManager sm;

    // Use ItemDetails.display(some_lot_number) to show customized page. Don't use `new`
    public static ItemDetails display(String lotNumber) {
        ItemDetails fragment = new ItemDetails();

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
        Picasso.get()
                .load(item.getImage())
                .fit()
                .centerCrop()
                .into(image);

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

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        db = FirebaseDBManager.getFirebaseDBInstance();
        sm = SessionManager.getSessionInstance();

        String lotNumber = getArguments().getString("lotNumber");
        db.getInfo("artifacts/"+lotNumber, ItemDetails.this);
        commentsRef = db.getDBRef().child("comments/"+lotNumber);

        // =======================Set Item Info==============================================
        itemName = view.findViewById(R.id.textViewItemDetTitle);
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



        // =======================Set Comments==============================================
        RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.comment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(commentAdapter);
        fetchComments(lotNumber);


        // =======================Post Comments==============================================
        post = view.findViewById(R.id.editTextCommentButton);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = view.findViewById(R.id.editTextCommentContent);
                String commentBody = comment.getText().toString().trim();
                if (commentBody.isEmpty() ) {
                    Toast.makeText( getContext(), "Empty comment can't be posted", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                Comment commentObj = new Comment();
                commentObj.setLotNumber(lotNumber);
                commentObj.setUsername(sm.getUsername());
                commentObj.setUID(sm.getUid());
                commentObj.setContent(commentBody);
                commentObj.setTimestamp(System.currentTimeMillis() / 1000L);
                String commentPath = "/comments/"+lotNumber+"/"+commentsRef.push().getKey()+"/";
                db.insertInfo(commentPath, commentObj);
                comment.setText("");
            }
        });


        return view;
    }

    private void fetchComments(String lotNumber) {
        commentsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    commentList.add(comment);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
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


