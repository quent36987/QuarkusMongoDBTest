package com.epita.model;

public class ActionModel {
    public String userId;
    public String postId;
    public String action;
    public String actionAt;

    public ActionModel() {
    }

    public ActionModel(String userId, String postId, String action) {
        this.userId = userId;
        this.postId = postId;
        this.action = action;
        this.actionAt = java.time.LocalDateTime.now().toString();
    }
}
