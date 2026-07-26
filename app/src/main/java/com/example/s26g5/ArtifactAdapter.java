package com.example.s26g5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ArtifactAdapter extends RecyclerView.Adapter<ArtifactAdapter.ArtifactViewHolder> {

    public interface OnArtifactClickListener {
        void onClick(ArtifactStringField artifact);
    }

    private final List<ArtifactStringField> items = new ArrayList<>();
    private final OnArtifactClickListener listener;

    public ArtifactAdapter(OnArtifactClickListener listener) {
        this.listener = listener;
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ArtifactViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageArtifact);
            name = itemView.findViewById(R.id.textArtifactName);
        }
    }

    @NonNull
    @Override
    public ArtifactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artifact, parent, false);
        return new ArtifactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtifactViewHolder holder, int position) {
        ArtifactStringField item = items.get(position);
        holder.name.setText(item.getName());
//        Glide.with(holder.image.getContext())
//                .load(item.getImageUrl())
//                .placeholder(R.drawable.ic_default_image)
//                .centerCrop()
//                .into(holder.image);
        holder.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}