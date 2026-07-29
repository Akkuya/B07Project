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
    private List<Artifact> artifactList;

    public ArtifactAdapter(List<Artifact> artifactList) {
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
        Artifact artifact = artifactList.get(position);
        Glide.with(holder.imageViewArtifact.getContext())
                .load(artifact.getImage())
                .placeholder(R.drawable.ic_default_image)
                .centerCrop()
                .into(holder.imageViewArtifact);
        holder.textViewName.setText(artifact.getArtifactName());
    }

    @Override
    public int getItemCount() {
        return artifactList.size();
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewArtifact;
        TextView textViewName;

        public ArtifactViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewArtifact = itemView.findViewById(R.id.imageArtifact);
            textViewName = itemView.findViewById(R.id.textArtifactName);
        }
    }
}