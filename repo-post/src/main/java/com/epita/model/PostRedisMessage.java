package com.epita.model;

public class PostRedisMessage {
    public String post_id;
    public String text;
    public String media;


    public PostRedisMessage(String post_id, String text, String media) {
        this.post_id = post_id;
        this.text = text;
        this.media = media;

    }
}
