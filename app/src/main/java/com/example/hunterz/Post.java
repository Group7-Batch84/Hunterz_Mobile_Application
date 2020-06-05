package com.example.hunterz;

import android.graphics.Bitmap;

import java.sql.Blob;

public class Post {

    private String post_id,post_date,post_subject,post_message;
    private Bitmap post_image;

    public Post(String post_id, String post_date, String post_subject, String post_message, Bitmap post_image) {
        this.post_id = post_id;
        this.post_date = post_date;
        this.post_subject = post_subject;
        this.post_message = post_message;
        this.post_image = post_image;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_subject() {
        return post_subject;
    }

    public void setPost_subject(String post_subject) {
        this.post_subject = post_subject;
    }

    public String getPost_message() {
        return post_message;
    }

    public void setPost_message(String post_message) {
        this.post_message = post_message;
    }

    public Bitmap getPost_image() {
        return post_image;
    }

    public void setPost_image(Bitmap post_image) {
        this.post_image = post_image;
    }
}
