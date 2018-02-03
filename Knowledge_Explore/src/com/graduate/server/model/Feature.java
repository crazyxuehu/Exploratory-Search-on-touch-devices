package com.graduate.server.model;

public class Feature {
	private Entity query;
	private Relation relation;
	private Entity target;
	private double score;
	public Feature(){
		score=1;
	}
	public Entity getQuery() {
		return query;
	}
	public void setQuery(Entity query) {
		this.query = query;
	}
	public Feature(Entity query,Relation relation ,Entity target){
		this.query=query;
		this.relation=relation;
		this.target=target;
	}
	public Feature(Relation relation,Entity query,double score){
		this.relation=relation;
		this.query=query;
		this.score=score;
	}
	public Feature(Entity query,Relation relation,double score){
		this.relation=relation;
		this.query=query;
		this.score=score;
	}
	public Feature(Entity query,Relation relation,Entity target,double score){
		this.query=query;
		this.relation=relation;
		this.target=target;
		this.score=score;
	}
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	public Entity getTarget() {
		return target;
	}
	public void setTarget(Entity target) {
		this.target = target;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
