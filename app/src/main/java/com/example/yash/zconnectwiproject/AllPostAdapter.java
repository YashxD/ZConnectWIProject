package com.example.yash.zconnectwiproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yash on 31/12/17.
 */

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.CustomViewHolder>{

    ArrayList<Post> posts;
    Context context;

    public AllPostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = new ArrayList<Post>(posts);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_allpost_postlayout, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        String author = "-by " + posts.get(position).getPostAuthor();
        holder.author.setText(author);
        holder.title.setText(posts.get(position).getPostContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchViewPost = new Intent(context, ViewPost.class);
                launchViewPost.putExtra("author", posts.get(position).getPostAuthor());
                launchViewPost.putExtra("content", posts.get(position).getPostContent());
                launchViewPost.putExtra("position", position);
                context.startActivity(launchViewPost);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textview_allpost_posttitle);
            author = (TextView) itemView.findViewById(R.id.textview_allpost_postauthor);
        }
    }
}
