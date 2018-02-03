package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class RelationModel {
    int direction;
    int type;
    int relationId;
    String name;

    public RelationModel(int direction, int type, int relationId, String name) {
        this.direction = direction;
        this.type = type;
        this.relationId = relationId;
        this.name = name;
    }

    public int getDirection() {
        return direction;
    }

    public int getType() {
        return type;
    }

    public int getRelationId() {
        return relationId;
    }

    public String getName() {
        return name;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
