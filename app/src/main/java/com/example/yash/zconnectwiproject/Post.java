package com.example.yash.zconnectwiproject;

/**
 * Created by yash on 2/1/18.
 */

public class Post {
    String postContent;
    String postAuthor;
    String postTitle;
    boolean reportedFlag;

    public Post(Post post) {
        this.postAuthor=post.postAuthor;
        this.postContent=post.postContent;
        this.reportedFlag = post.reportedFlag;
    }

    public Post() {
        this.postContent = "";
        this.postAuthor = "";
        this.reportedFlag = false;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public  boolean getReportedFlag() {
        return reportedFlag;
    }

    public  String getPostTitle() {
        return postTitle;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public void setReportedFlag(boolean reportedFlag) {
        this.reportedFlag = reportedFlag;
    }

    public  void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
}
