package com.example.yash.zconnectwiproject;

import android.content.Context;
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

    ArrayList<String> titleList;
    ArrayList<String> authorList;
    Context context;

    public AllPostAdapter(Context context, ArrayList<String> titleList, ArrayList<String> authorList) {
        this.context = context;
        this.authorList = authorList;
        this.titleList = titleList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partial_allpost_postlayout, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        String author = "-by " + authorList.get(position);
        holder.author.setText(author);
        holder.title.setText(titleList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add code for launching a new activity for the post to manually block it.
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
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
