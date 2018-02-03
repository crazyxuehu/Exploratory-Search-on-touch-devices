package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/12/3.
 */

public class Node {
    private String name;
    private int x;
    private int y;
    private int category;

    public Node(String name, int x, int y, int category) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
