package com.example.s26g5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;
import com.bumptech.glide.Glide;
import java.util.List;

public class SavedArtifactAdapter extends RecyclerView.Adapter<SavedArtifactAdapter.ItemViewHolder> {
        private List<ArtifactSaved> itemList;

    public SavedArtifactAdapter(List<ArtifactSaved> itemList) {
        this.itemList = itemList;
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
        AppDatabase db = AppDatabase.getInstance(holder.itemView.getContext());

        holder.textViewArtifactName.setText(item.getArtifactName());
        holder.textViewCulturalOrigin.setText(item.getCulturalOrigin());

        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .placeholder(R.drawable.placeholder) // Placeholder image
                .error(R.drawable.error_image)
                .into(holder.imageView);

        holder.saveButton.setSelected(item.getIsSaved());
        holder.saveButton.setOnClickListener(v -> {
            boolean state = !item.getIsSaved();
            item.setIsSaved(!item.getIsSaved());
            holder.saveButton.setSelected(item.getIsSaved());

            new Thread(() -> {
                if (state) {
                    db.artifactDao().insertSavedArtifact(new SavedArtifactEntity(item.getArtifactName(), item.getLotNumber(), item.getCulturalOrigin(), item.getImage()));
                } else {
                    db.artifactDao().deleteSavedArtifact(new SavedArtifactEntity(item.getArtifactName(), item.getLotNumber(), item.getCulturalOrigin(), item.getImage()));
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
