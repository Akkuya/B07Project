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

public class ArtefactAdapter extends RecyclerView.Adapter<com.example.s26g5.ArtefactAdapter.ItemViewHolder> {
        private List<ArtifactStringField> itemList;

        public ArtefactAdapter(List<ArtifactStringField> itemList) {
            this.itemList = itemList;
        }

        @NonNull
        @Override
        public com.example.s26g5.ArtefactAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artifact_layout, parent, false);
            return new com.example.s26g5.ArtefactAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            ArtifactStringField item = itemList.get(position);

            holder.textViewArtifactName.setText(item.getValue());
            holder.textViewCulturalOrigin.setText(item.getValue());

            Glide.with(holder.itemView.getContext())
                    .load(item.getValue())
                    .placeholder(R.drawable.placeholder) // Placeholder image
                    .error(R.drawable.error_image)
                    .into(holder.supabase_image_view);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView textViewArtifactName, textViewCulturalOrigin;
            ImageView supabase_image_view;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewArtifactName = itemView.findViewById(R.id.TextViewArtifactName);
                textViewCulturalOrigin = itemView.findViewById(R.id.TextViewCulturalOrigin);
                ImageView imageView = itemView.findViewById(R.id.supabase_image_view);
            }
        }
}
