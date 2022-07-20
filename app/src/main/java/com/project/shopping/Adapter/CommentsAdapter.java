package com.project.shopping.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.shopping.Model.Comments;
import com.project.shopping.Model.Users;
import com.project.shopping.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Activity context;
    private List<Comments> commentsList;

    public CommentsAdapter(Activity context , List<Comments> commentsList){
        this.context = context;
        this.commentsList = commentsList;
    }

    public void addList(List<Comments> comments){
        commentsList = comments;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_comment , parent , false);
        return new CommentsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comments comments = commentsList.get(position);
        holder.setmComment(comments.getComment());

        holder.setmUserName(comments.getUserName());
        holder.setCircleImageView(comments.getUserImage());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder{
        TextView mComment , mUserName;
        CircleImageView circleImageView;
        View mView;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setmComment(String comment){
            mComment = mView.findViewById(R.id.comment_tv);
            mComment.setText(comment);
        }
        public void setmUserName(String userName){
           mUserName = mView.findViewById(R.id.comment_user);
           if(userName.isEmpty())
               mUserName.setText("user");
           else
           mUserName.setText(userName);
        }
        public void setCircleImageView(String profilePic){
            circleImageView = mView.findViewById(R.id.comment_Profile_pic);
            if(profilePic == null|| profilePic.equals("null")){
                circleImageView.setImageResource(R.drawable.profile);
            }else
                Glide.with(context).load(profilePic).into(circleImageView);
        }
    }
}
