package com.example.s26g5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter
        extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.activity_item_adapater,
                        parent,
                        false
                );

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ItemViewHolder holder,
            int position
    ) {
        Item item = itemList.get(position);

        holder.textViewLotNumber.setText(item.getLotNumber());
        holder.textViewArtifactName.setText(item.getArtifactName());
        holder.textViewMaterials.setText(item.getMaterials());
        holder.textViewDynasty.setText(item.getDynasty());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewImage.setText(item.getImage());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewLotNumber;
        private final TextView textViewArtifactName;
        private final TextView textViewMaterials;
        private final TextView textViewDynasty;
        private final TextView textViewDescription;
        private final TextView textViewImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLotNumber =
                    itemView.findViewById(R.id.textViewLotNumber);

            textViewArtifactName =
                    itemView.findViewById(R.id.textViewArtifactName);

            textViewMaterials =
                    itemView.findViewById(R.id.textViewMaterials);

            textViewDynasty =
                    itemView.findViewById(R.id.textViewDynasty);

            textViewDescription =
                    itemView.findViewById(R.id.textViewDescription);

            textViewImage =
                    itemView.findViewById(R.id.textViewImage);
        }
    }
}