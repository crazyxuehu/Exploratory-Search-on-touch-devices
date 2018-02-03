package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/10/23.
 */

public class FeedBackModel {
    private FeatureButtonModel feature;
    private int weight;
    public FeedBackModel(FeatureButtonModel feature){
       this.feature=feature;
        weight=50;
    }
    public FeedBackModel(FeatureButtonModel feature,int weight){
        this.feature=feature;
        this.weight=weight;
    }
    public FeedBackModel(){
        feature=new FeatureButtonModel();
        weight=50;
    }
    public void setFeature(FeatureButtonModel feature){
        this.feature=feature;
    }
    public FeatureButtonModel getFeature(){
        return feature;
    }
    public void setWeight(int weight){
        this.weight=weight;
    }
    public int getWeight(){
        return weight;
    }
}
