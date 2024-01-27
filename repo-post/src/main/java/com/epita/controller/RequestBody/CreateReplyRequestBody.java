package com.epita.controller.RequestBody;

public class CreateReplyRequestBody {
    private String text;
    private String media;

    // Constructeur, getters et setters
    public CreateReplyRequestBody() {
    }

    public CreateReplyRequestBody(String text, String media) {
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
