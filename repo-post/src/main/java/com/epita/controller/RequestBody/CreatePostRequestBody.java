package com.epita.controller.RequestBody;

public class CreatePostRequestBody {
    private String text;
    private String media;

    // Constructeur, getters et setters
    public CreatePostRequestBody() {
    }

    public CreatePostRequestBody(String text, String media) {
        this.text = text;
        this.media = media;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
