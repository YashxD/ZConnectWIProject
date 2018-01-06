package com.example.yash.zconnectwiproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ViewPost extends AppCompatActivity {

    TextView viewPostTitleTextView, viewPostAuthorTextView, viewPostContentTextView;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        viewPostAuthorTextView = (TextView) findViewById(R.id.textview_viewpost_author);
        viewPostContentTextView = (TextView) findViewById(R.id.textview_viewpost_postcontent);
        viewPostTitleTextView = (TextView) findViewById(R.id.textview_viewpost_title);

        viewPostTitleTextView.setText("Default Title");
        viewPostContentTextView.setText(getIntent().getStringExtra("content"));
        viewPostAuthorTextView.setText(getIntent().getStringExtra("author"));
        getIntent().getIntExtra("position", position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewpostmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewpostmenu_report:
                // Report item was selected.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
