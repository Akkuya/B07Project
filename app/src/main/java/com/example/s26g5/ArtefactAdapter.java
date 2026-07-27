package com.example.s26g5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ArtefactAdapter extends RecyclerView.Adapter<com.example.s26g5.ArtefactAdapter.ItemViewHolder> {
        private List<Artifact_basic> itemList;

        public ArtefactAdapter(List<Artifact_basic> itemList) {
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
            Artifact_basic item = itemList.get(position);
            holder.textViewArtifactName.setText(item.getArtifactName());
            holder.textViewCulturalOrigin.setText(item.getCulturalOrigin());
            holder.textViewDescription.setText(item.getDescription());
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView textViewArtifactName, textViewCulturalOrigin, textViewDescription;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewArtifactName = itemView.findViewById(R.id.TextViewArtifactName);
                textViewCulturalOrigin = itemView.findViewById(R.id.TextViewCulturalOrigin);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
            }
        }
}
