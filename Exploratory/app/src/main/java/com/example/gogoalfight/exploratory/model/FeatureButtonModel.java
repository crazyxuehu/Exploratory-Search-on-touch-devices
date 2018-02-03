package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/10/23.
 */

public class FeatureButtonModel {
    private int type;//1 relation;2 category
    private String label;
    private String Entity;
    public FeatureButtonModel(){
        type=0;
        label="Relation";
        Entity="Entity";
    }
    public FeatureButtonModel(int type,String label,String Entity){
        this.type=type;
        this.label=label;
        this.Entity=Entity;
    }
    public void setType(int type){
        this.type=type;
    }
    public int getType(){
        return type;
    }
    public void setLabel(String label){
        this.label=label;
    }
    public String getLabel(){
        return label;
    }
    public void setEntity(String Entity){
        this.Entity=Entity;
    }
    public String getEntity(){
        return Entity;
    }
}
