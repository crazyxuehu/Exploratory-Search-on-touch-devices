package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class EntityModel {
    int type;
    int id;
    String name;
    String category;
    double score;

    public EntityModel(int type, int id, String name, String category, double score) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.category = category;
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getScore() {
        return score;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
