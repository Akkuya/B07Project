package com.example.s26g5.item_viewing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;
import com.bumptech.glide.Glide;
import com.example.s26g5.AppDatabase;
import com.example.s26g5.ArtifactSaved;
import com.example.s26g5.R;

import java.util.List;

public class SavedArtifactAdapter extends RecyclerView.Adapter<SavedArtifactAdapter.ItemViewHolder> {
    private final List<ArtifactSaved> itemList;
    private final DatabaseReference firebaseRef;

    public SavedArtifactAdapter(List<ArtifactSaved> itemList) {
        this.itemList = itemList;
        this.firebaseRef = getFirebaseRef();
    }

    private DatabaseReference getFirebaseRef() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/")
                    .getReference("users")
                    .child(user.getUid())
                    .child("saved_artifacts");
        }
        return null;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artifact_layout, parent, false);
        return new SavedArtifactAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedArtifactAdapter.ItemViewHolder holder, int position) {
        ArtifactSaved item = itemList.get(position);
        AppDatabase localDb = AppDatabase.getInstance(holder.itemView.getContext());

        holder.textViewArtifactName.setText(item.getArtifactName());
        holder.textViewCulturalOrigin.setText(item.getCulturalOrigin());

        if (item.getImage() != null && !item.getImage().isEmpty()) {
            Picasso.get()
                    .load(item.getImage())
                    .fit()
                    .centerCrop()
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(holder.imageView);
        }

        holder.saveButton.setSelected(item.getIsSaved());
        holder.saveButton.setOnClickListener(v -> {
            boolean newState = !item.getIsSaved();
            item.setIsSaved(newState);
            holder.saveButton.setSelected(newState);

            new Thread(() -> {
                SavedArtifactEntity entity = new SavedArtifactEntity(
                        item.getArtifactName(), 
                        item.getLotNumber(), 
                        item.getCulturalOrigin(), 
                        item.getImage()
                );
                
                if (newState) {
                    localDb.artifactDao().insertSavedArtifact(entity);
                    if (firebaseRef != null) {
                        firebaseRef.child(item.getLotNumber()).setValue(true);
                    }
                } else {
                    localDb.artifactDao().deleteSavedArtifact(entity);
                    if (firebaseRef != null) {
                        firebaseRef.child(item.getLotNumber()).removeValue();
                    }
                }
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewArtifactName, textViewCulturalOrigin;
        ImageView imageView;
        ImageButton saveButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewArtifactName = itemView.findViewById(R.id.TextViewArtifactName);
            textViewCulturalOrigin = itemView.findViewById(R.id.TextViewCulturalOrigin);
            imageView = itemView.findViewById(R.id.supabase_image_view);
            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }
}
