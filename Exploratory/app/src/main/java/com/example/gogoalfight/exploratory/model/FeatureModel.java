package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class FeatureModel {
    private EntityModel query;
    private RelationModel relation;
    private EntityModel target;
    private double score;

    public void setQuery(EntityModel query) {
        this.query = query;
    }

    public void setRelation(RelationModel relation) {
        this.relation = relation;
    }

    public void setTarget(EntityModel target) {
        this.target = target;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public EntityModel getQuery() {
        return query;

    }

    public RelationModel getRelation() {
        return relation;
    }

    public EntityModel getTarget() {
        return target;
    }

    public double getScore() {
        return score;
    }

    public FeatureModel(EntityModel query, RelationModel relation, EntityModel target, double score) {

        this.query = query;
        this.relation = relation;
        this.target = target;
        this.score = score;
    }



}
