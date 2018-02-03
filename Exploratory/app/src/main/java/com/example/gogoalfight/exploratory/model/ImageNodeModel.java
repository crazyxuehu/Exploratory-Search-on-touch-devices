package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/10/23.
 */

public class ImageNodeModel {
    private String src;
    private String entity;
    public ImageNodeModel(){
        src="";
        entity="Noodles";
    }
    public void setSrc(String src){
        this.src=src;
    }
    public String getSrc(){
        return src;
    }
    public void setEntity(String entity){
        this.entity=entity;
    }
    public String getEntity(){
        return entity;
    }
}
