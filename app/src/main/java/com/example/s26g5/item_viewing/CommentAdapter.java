package com.example.s26g5.item_viewing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.s26g5.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;
    private String lotNumber;

    public CommentAdapter(List<Comment> commentList, String lotNumber) {
        this.commentList = commentList;
        this.lotNumber = lotNumber;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.activity_comment_adapater,
                        parent,
                        false
                );

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CommentViewHolder holder,
            int position
    ) {
        Comment comment = commentList.get(position);

        holder.textViewCommentContent.setText(comment.getContent());
        holder.textViewCommentUsername.setText(comment.getUsername());
        Date date = new Date(comment.getTimestamp() * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        holder.textViewCommentTimestamp.setText(formatter.format(date));

        holder.buttonDeleteComment.setOnClickListener(v -> {

            if (comment.getKey() == null) {
                Toast.makeText(
                        v.getContext(),
                        "Could not identify this comment",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete comment")
                    .setMessage(
                            "Are you sure you want to delete this comment?"
                    )
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialog, which) -> {

                        DatabaseReference commentRef =
                                FirebaseDatabase.getInstance()
                                        .getReference("comments")
                                        .child(lotNumber)
                                        .child(comment.getKey());

                        commentRef.removeValue()
                                .addOnSuccessListener(unused ->
                                        Toast.makeText(
                                                v.getContext(),
                                                "Comment deleted",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                )
                                .addOnFailureListener(exception ->
                                        Toast.makeText(
                                                v.getContext(),
                                                "Failed to delete comment",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                );
                    })
                    .show();
        });
    }



    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCommentContent;
        private final TextView textViewCommentUsername;
        private final TextView textViewCommentTimestamp;
        private final ImageButton buttonDeleteComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
            textViewCommentUsername = itemView.findViewById(R.id.textViewCommentUsername);
            textViewCommentTimestamp = itemView.findViewById(R.id.textViewCommentTimestamp);
            buttonDeleteComment = itemView.findViewById(R.id.buttonDeleteComment);
        }
    }
}
