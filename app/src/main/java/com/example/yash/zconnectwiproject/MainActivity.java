package com.example.yash.zconnectwiproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Layout Declarations
    //ScrollView allPostScrollView;
    RecyclerView allPostRecyclerView;
    TextView allPostTitleTextView;
    EditText newPostInputEditText;
    Button newPostSubmitButton;
    Button newPostCheckButton;


    //Variable Delarations
    AllPostAdapter allPostAdapter;
    ArrayList allPostTitles;
    ArrayList<String> allPostAuthors;
    LinearLayoutManager allPostLinearLayoutManager;
    View.OnClickListener newPostCheckListener;
    View.OnClickListener newPostSubmitListener;
    Post post;
    ArrayList<Post> allPosts;
    String input;
    private DatabaseReference databaseReference;
    ValueEventListener allPostsListener;
    ValueEventListener allPostsInitialListener;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_newpost:
                    //allPostScrollView.setVisibility(View.GONE);
                    allPostRecyclerView.setVisibility(View.GONE);
                    allPostTitleTextView.setVisibility(View.GONE);
                    newPostCheckButton.setVisibility(View.VISIBLE);
                    newPostInputEditText.setVisibility(View.VISIBLE);
                    newPostSubmitButton.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_allpost:
                    newPostCheckButton.setVisibility(View.GONE);
                    newPostInputEditText.setVisibility(View.GONE);
                    newPostSubmitButton.setVisibility(View.GONE);
                    allPostRecyclerView.setVisibility(View.VISIBLE);
                    allPostTitleTextView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        //allPostScrollView = findViewById(R.id.scrollview_allpost);
        allPostRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_allpost);
        allPostTitleTextView = (TextView) findViewById(R.id.textView_allpost_title);
        newPostCheckButton = (Button) findViewById(R.id.button_newpost_check);
        newPostInputEditText = (EditText) findViewById(R.id.editext_newpost);
        newPostSubmitButton = (Button) findViewById(R.id.button_newpost_submit);
        allPostLinearLayoutManager = new LinearLayoutManager(getApplicationContext());

        allPostTitles = new ArrayList<>(Arrays.asList("Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"));
        allPostAuthors = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
        allPosts = new ArrayList<Post>();
        post = new Post();
        input = new String("");
        allPosts.add(post);

        allPostRecyclerView.setLayoutManager(allPostLinearLayoutManager);
        allPostAdapter = new AllPostAdapter(MainActivity.this, allPosts);
        allPostRecyclerView.setAdapter(allPostAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://zconnect-wi-project.firebaseio.com/");
        final GenericTypeIndicator<ArrayList<Post>> t = new GenericTypeIndicator<ArrayList<Post>>() {};

        newPostCheckListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };

        newPostSubmitListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = newPostInputEditText.getText().toString();
                newPostInputEditText.setText("");
                post.setPostAuthor("DefaultAuthor");
                post.setPostContent(input);
                allPosts.add(post);
                databaseReference.child("allPosts").setValue(allPosts);
                post = new Post();
            }
        };

        allPostsInitialListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allPosts = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                allPosts.add(post);
            }
        };

        allPostsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allPosts = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Retreive Error", Toast.LENGTH_SHORT);
            }
        };

        databaseReference.addValueEventListener(allPostsListener);
        databaseReference.addListenerForSingleValueEvent(allPostsInitialListener);
        newPostSubmitButton.setOnClickListener(newPostSubmitListener);
    }

}
