package com.example.yash.zconnectwiproject;

/**
 * Created by yash on 2/1/18.
 */

public class Post {
    String postContent;
    String postAuthor;

    public Post(Post post) {
        this.postAuthor=post.postAuthor;
        this.postContent=post.postContent;
    }

    public Post() {
        this.postContent = "";
        this.postAuthor = "";
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }
}
