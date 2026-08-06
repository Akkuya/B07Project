package com.example.s26g5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


public class ArtifactAdapter extends RecyclerView.Adapter<ArtifactAdapter.ArtifactViewHolder> {
    private List<Item> artifactList;

    public ArtifactAdapter(List<Item> artifactList) {
        this.artifactList = artifactList;
    }

    @NonNull
    @Override
    public ArtifactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artifact, parent, false);
        return new ArtifactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtifactViewHolder holder, int position) {
        Item artifact = artifactList.get(position);
        Glide.with(holder.artifactImage.getContext())
                .load(artifact.getImage())
                .placeholder(R.drawable.ic_default_image)
                .centerCrop()
                .into(holder.artifactImage);
        holder.artifactName.setText(truncate(artifact.getArtifactName()));
    }

    private String truncate(String name) {
        if (name != null && name.length() > 15) {
            return name.substring(0, 15) + "...";
        }
        return name;
    }

    @Override
    public int getItemCount() {
        return artifactList.size();
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        ImageView artifactImage;
        TextView artifactName;


        public ArtifactViewHolder(@NonNull View itemView) {
            super(itemView);
            artifactImage = itemView.findViewById(R.id.imageArtifact);
            artifactName = itemView.findViewById(R.id.textArtifactName);

        }
    }
}