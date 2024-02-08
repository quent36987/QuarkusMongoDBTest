package com.epita.data.model;

import java.util.ArrayList;
import java.util.List;

public class ActionsModel {

    public List<ActionModel> actions;

    public ActionsModel() {
        actions = new ArrayList<>();
    }

    public ActionsModel(List<ActionModel> actions) {
        this.actions = actions;
    }

    public ActionsModel(ActionModel actions) {
        this.actions = new ArrayList<>();
        this.actions.add(actions);
    }
}
