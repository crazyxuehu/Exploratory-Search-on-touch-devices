package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/11/3.
 */

public class QueryModel {
    private String query;
    private double weight;
    private String relation;
    private int direction;
    public QueryModel(String query,double weight){
        this.query=query;
        this.weight=weight;
    }
    public QueryModel(String query,double weight,String relation,int direction){
        this.query=query;
        this.weight=weight;
        this.relation=relation;
        this.direction=direction;
    }
    public void setQuery(String query){
        this.query=query;
    }
    public String getQuery(){
        return query;
    }
    public void setWeight(double weight){
        this.weight=weight;
    }
    public double getWeight(){
        return weight;
    }
    public void  setRelation(String relation){
        this.relation=relation;
    }
    public String getRelation(){
        return this.relation;
    }
    public void setDirection(int direction){
        this.direction=direction;
    }
    public int getDirection(){
        return this.direction;
    }
}
