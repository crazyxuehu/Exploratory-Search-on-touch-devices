package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class Entity {
    private String name;
    private String src;
    public Entity(String name,String src){
        this.name=name;
        this.src=src;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setSrc(String src){
        this.src=src;
    }
    public String getName(){
        return name;
    }
    public String getSrc(){
        return src;
    }
}
