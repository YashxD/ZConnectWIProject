package com.example.yash.zconnectwiproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Layout Declarations
    RecyclerView allPostRecyclerView;
    TextView allPostTitleTextView;
    EditText newPostInputEditText;
    EditText newPostTitleEditText;
    Button newPostSubmitButton;
    Button newPostCheckButton;

    //Variable Delarations
    AllPostAdapter allPostAdapter;
    ArrayList<CharSequence> sensorWords;
    LinearLayoutManager allPostLinearLayoutManager;
    View.OnClickListener newPostCheckListener;
    View.OnClickListener newPostSubmitListener;
    Post post;
    public static ArrayList<Post> allPosts;
    ArrayList<Post> nonReportedPosts;
    String input, readerInput;
    CharSequence temp;
    private DatabaseReference databaseReference;
    ValueEventListener allPostsListener;
    ValueEventListener allPostsInitialListener;
    AssetManager assetManager;
    InputStream inputStream;
    BufferedReader bufferedReader;
    boolean sensorFlag = false;
    SharedPreferences postsStore;
    SharedPreferences.Editor postsStoreEditor;
    boolean initializeFlag = false;
    String json;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_newpost:
                    allPostRecyclerView.setVisibility(View.GONE);
                    allPostTitleTextView.setVisibility(View.GONE);
                    newPostCheckButton.setVisibility(View.VISIBLE);
                    newPostInputEditText.setVisibility(View.VISIBLE);
                    newPostSubmitButton.setVisibility(View.VISIBLE);
                    newPostTitleEditText.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_allpost:
                    refreshPostsList();
                    newPostCheckButton.setVisibility(View.GONE);
                    newPostInputEditText.setVisibility(View.GONE);
                    newPostSubmitButton.setVisibility(View.GONE);
                    newPostTitleEditText.setVisibility(View.GONE);
                    allPostRecyclerView.setVisibility(View.VISIBLE);
                    allPostTitleTextView.setVisibility(View.VISIBLE);
                    allPostRecyclerView.setAdapter(new AllPostAdapter(MainActivity.this, nonReportedPosts));
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

        allPostRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_allpost);
        allPostTitleTextView = (TextView) findViewById(R.id.textView_allpost_title);
        newPostCheckButton = (Button) findViewById(R.id.button_newpost_check);
        newPostInputEditText = (EditText) findViewById(R.id.edittext_newpost_content);
        newPostSubmitButton = (Button) findViewById(R.id.button_newpost_submit);
        newPostTitleEditText = (EditText) findViewById(R.id.edittext_newpost_title);
        allPostLinearLayoutManager = new LinearLayoutManager(getApplicationContext());

        allPosts = new ArrayList<Post>();
        nonReportedPosts = new ArrayList<Post>();
        post = new Post();
        input = new String("");
        sensorWords = new ArrayList<CharSequence>();


        assetManager = getApplicationContext().getAssets();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("SensorWords.txt")));
            readerInput = bufferedReader.readLine();

            while (readerInput != null) {
                temp = readerInput;
                sensorWords.add(temp);
                readerInput = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        refreshPostsList();
        allPostRecyclerView.setLayoutManager(allPostLinearLayoutManager);
        allPostAdapter = new AllPostAdapter(MainActivity.this, nonReportedPosts);
        allPostRecyclerView.setAdapter(allPostAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://zconnect-wi-project.firebaseio.com/");
        final GenericTypeIndicator<ArrayList<Post>> t = new GenericTypeIndicator<ArrayList<Post>>() {
        };

        newPostCheckListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfInappropriateInput();
            }
        };

        newPostSubmitListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfInappropriateInput();
                if(sensorFlag) {
                    Toast.makeText(MainActivity.this, "Please enter a non inappropriate text", Toast.LENGTH_LONG).show();
                }
                else {
                    newPost();
                }
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
        newPostCheckButton.setOnClickListener(newPostCheckListener);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        json =
        outState.putStringArrayList("posts", allPosts);
    }

    public void initialize() {
        postsStore = getSharedPreferences("PostsStore", MODE_PRIVATE);
        postsStoreEditor = postsStore.edit();
        Gson gson = new Gson();
        if(postsStore.contains("posts")) {
            json = gson.toJson(allPosts);
            postsStoreEditor.putString("posts", json);

            json = postsStore.getString("posts", "");
            allPosts = gson.fromJson(json, ArrayList<Post.class>);
        }
    }

    public Post returnPost(int index) {
        return allPosts.get(index);
    }

    public void refreshPostsList() {
        nonReportedPosts = new ArrayList<Post>();
        for(int i = 0; i< allPosts.size(); i++) {
            if(!allPosts.get(i).getReportedFlag()) {
                nonReportedPosts.add(allPosts.get(i));
            }
        }
    }

    public void checkIfInappropriateInput() {
        input = newPostInputEditText.getText().toString();
        int i;
        for (i = 0; i < sensorWords.size(); i++) {
            if (input.contains(sensorWords.get(i))) {
                sensorFlag = true;
                Toast.makeText(MainActivity.this, "The text you have entered contains inappropriate words/phrases", Toast.LENGTH_LONG).show();
                break;
            }
        }
        if (i == sensorWords.size()) {
            sensorFlag = false;
            Toast.makeText(MainActivity.this, "Entered text is clean!", Toast.LENGTH_SHORT).show();
        }
    }

    public void newPost() {
        post = new Post();
        post.setPostAuthor("DefaultAuthor");
        post.setPostContent(newPostInputEditText.getText().toString());
        post.setPostTitle(newPostTitleEditText.getText().toString());
        newPostInputEditText.setText("");
        newPostTitleEditText.setText("");
        allPosts.add(post);
        databaseReference.child("allPosts").setValue(allPosts);
        post = new Post();
        refreshPostsList();
    }
}
