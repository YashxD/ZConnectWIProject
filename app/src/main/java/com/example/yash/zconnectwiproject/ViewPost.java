package com.example.yash.zconnectwiproject;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewPost extends MainActivity {

    TextView viewPostTitleTextView, viewPostAuthorTextView, viewPostContentTextView;
    Button viewPostReportButton;
    int position;
    Post post;
    AlertDialog.Builder builder;
    View.OnClickListener viewPostReportButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        viewPostAuthorTextView = findViewById(R.id.textview_viewpost_author);
        viewPostContentTextView = findViewById(R.id.textview_viewpost_postcontent);
        viewPostTitleTextView = findViewById(R.id.textview_viewpost_title);
        viewPostReportButton = findViewById(R.id.button_viewpost_report);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }


        position = getIntent().getIntExtra("position", 0);


        //position -=1;

        //this.post = new Post(super.returnPost(position));

        //if(post.getReportedFlag()) {
          //  viewPostTitleTextView.setText("Sorry, the post has been reported inappropriate");
        //}
        //else {
            viewPostTitleTextView.setText(getIntent().getStringExtra("title"));
            viewPostContentTextView.setText(getIntent().getStringExtra("content"));
            viewPostAuthorTextView.setText(getIntent().getStringExtra("author"));
            //viewPostContentTextView.setText(this.post.getPostContent());
            //viewPostAuthorTextView.setText(this.post.getPostAuthor());
        //}

        viewPostReportButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Report Post")
                        .setMessage("Are you sure you want to report this post?")
                        .setPositiveButton("Yes, Report", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //allPosts.get(position).setReportedFlag(true);
                                //post.setReportedFlag(true);
                                //refreshPostsList();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        };

        viewPostReportButton.setOnClickListener(viewPostReportButtonListener);
    }
}
