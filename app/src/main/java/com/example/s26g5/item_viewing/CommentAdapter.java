package com.example.s26g5.item_viewing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s26g5.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
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
    }



    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCommentContent;
        private final TextView textViewCommentUsername;
        private final TextView textViewCommentTimestamp;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
            textViewCommentUsername = itemView.findViewById(R.id.textViewCommentUsername);
            textViewCommentTimestamp = itemView.findViewById(R.id.textViewCommentTimestamp);
        }
    }
}
