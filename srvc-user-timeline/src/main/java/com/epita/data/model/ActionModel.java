package com.epita.data.model;

public class ActionModel {
    public String userId;
    public String postId;
    public String action;
    public String actionAt;

    public ActionModel() {
    }

    public ActionModel(String userId, String postId, String action, String actionAt) {
        this.userId = userId;
        this.postId = postId;
        this.action = action;
        this.actionAt = actionAt;
    }
}
