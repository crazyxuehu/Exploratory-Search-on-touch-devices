package com.example.gogoalfight.exploratory.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by GoGoalFight on 2017/10/30.
 */

public class Result {
    private HashMap<String,String>similarList;
    private List<FeatureButtonModel>featureList;
    private List<String>similarEntity;
    public Result(){
        this.similarList=similarList;
        this.featureList=featureList;
    }

    public HashMap<String, String> getSimilarList() {
        return similarList;
    }

    public List<FeatureButtonModel> getFeatureList() {
        return featureList;
    }

    public void setSimilarList(HashMap<String, String> similarList) {
        this.similarList = similarList;
    }

    public void setFeatureList(List<FeatureButtonModel> featureList) {
        this.featureList = featureList;
    }
    public void setSimilarEntity(List<String>similarEntity){
        this.similarEntity=similarEntity;
    }
    public List<String>getSimilarEntity(){
        return this.similarEntity;
    }
}
