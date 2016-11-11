package com.youtube.sorcjc.tabdeportes.model;

public class Category {
/*
{
    "id": 27,
    "slug": "baseball",
    "title": "Baseball",
    "description": "",
    "parent": 0,
    "post_count": 844
}
*/
    private int id;
    private String slug;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
