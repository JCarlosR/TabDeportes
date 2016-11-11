package com.youtube.sorcjc.tabdeportes.io.response;

import com.youtube.sorcjc.tabdeportes.model.Post;

import java.util.ArrayList;

public class CategoryPostsResponse {

    private String status;
    private int count;
    private int pages;
    private ArrayList<Post> posts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

}
