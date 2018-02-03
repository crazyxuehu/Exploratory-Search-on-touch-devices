package com.example.gogoalfight.exploratory.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class GlobalData extends Application {
    private List<String>similarEntity;
    private HashMap<String,String>similarList;
    private List<FeatureButtonModel>featureList;
    private List<String>queryList;
    private int count;
    private int simEntityCount;
    private int imageFlag;
    private int resultFlag;
    private Stack<String>touchList;
    public int getImageFlag() {
        return imageFlag;
    }

    public int getResultFlag() {
        return resultFlag;
    }

    public void setImageFlag(int imageFlag) {

        this.imageFlag = imageFlag;
    }

    public void setResultFlag(int resultFlag) {
        this.resultFlag = resultFlag;
    }

    public void initialPress(){
        this.imageFlag=0;
        this.resultFlag=0;
    }
    public GlobalData(){
        similarEntity=new ArrayList<String>();
        featureList=new ArrayList<FeatureButtonModel>();
        queryList=new ArrayList<String>();
        similarList=new HashMap<String,String>();
        count=0;
        simEntityCount=0;
        touchList=new Stack<String>();
    }

    public HashMap<String, String> getSimilarList() {
        return similarList;
    }

    public void initail(){
        similarEntity=new ArrayList<String>();
        featureList=new ArrayList<FeatureButtonModel>();
        queryList=new ArrayList<String>();
        similarList=new HashMap<String,String>();
        count=0;
        simEntityCount=0;

    }
    public void setSimEntityCount(int simEntityCount){
        this.simEntityCount=simEntityCount;
    }
    public int getSimEntityCount(){
        return simEntityCount;
    }
    public void setCount(int count){
        this.count=count;
    }
    public int getCount(){
        return count;
    }
    public void setSimilarEntity(List similarEntity){
        for(int i=0;i<similarEntity.size();i++){
            this.similarEntity.add((String)similarEntity.get(i));
        }
    }
    public void addSimilarEntityName(String entity){
        this.similarEntity.add(entity);
    }
    public void addSimilarEntity(String name,String src){
        //this.similarEntity.add(entity);
        this.similarList.put(name,src);
    }
    public String getEntityImage(String name){
        return similarList.get(name);
    }
    public void setQueryList(List queryList){
        for(int i=0;i<queryList.size();i++){
            this.queryList.add(queryList.get(i).toString());
        }
    }
    public void setFeatureList(List featureList){
        for(int i=0;i<featureList.size();i++){
            this.featureList.add((FeatureButtonModel)featureList.get(i));
        }
    }
    public void addFeatureEntity(FeatureButtonModel feature){
        this.featureList.add(feature);
    }
    public List<String>getSimilarEntity(){
        return similarEntity;
    }
    public List<FeatureButtonModel>getFeatureList(){
        return featureList;
    }
    public List<String>getQueryList(){
        return queryList;
    }
    public void addCount(){
        count++;
    }
    public int getTouchSize(){
        return this.touchList.size();
    }
    public void addTouchEntity(String tail){
        this.touchList.add(tail);
    }
    public void popTouchEntity(){
        this.touchList.pop();
    }
    public Stack<String> getTouchList(){
        return touchList;
    }
}
