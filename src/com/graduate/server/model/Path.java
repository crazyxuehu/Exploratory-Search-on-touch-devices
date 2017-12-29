package com.graduate.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Path {
	private Entity head;
	private Relation relation;
	private ArrayList<Relation> relationList;
	private ArrayList<Entity> entityList;
	private Stack<Relation> relationPath;
	private Stack<Entity>entityPath;
	private double score;
	public Path(){
		relationList = new ArrayList<>();
		entityList = new ArrayList<>();
		relationPath=new Stack<Relation>();
		entityPath=new Stack<Entity>();
		score = 0;
	}
	public Path(Entity category, List<Relation> list) {
		this.head=category;
		relationList=(ArrayList<Relation>) list;
	}
	public Path(Relation relation, Entity category, double score) {
		this.relation=relation;
		this.head=category;
		this.score=score;
	}
	public Path(Entity headEntity) {
		this.head=headEntity;
		relationList = new ArrayList<>();
		entityList = new ArrayList<>();
		relationPath=new Stack<Relation>();
		entityPath=new Stack<Entity>();
		entityPath.add(headEntity);
		score = 0;
	}
	public Path(Entity head, Stack<Relation> relationPath,
			Stack<Entity> entityPath, double score) {
		this.head=head;
		this.relationPath=new Stack<Relation>();
		this.entityPath=new Stack<Entity>();
		int i;
		for(i=0;i<relationPath.size();i++){
			this.relationPath.add(relationPath.get(i));
			this.entityPath.add(entityPath.get(i));
		}
		this.entityPath.add(entityPath.get(i));
		this.score=score;
	}
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	public Entity getHead() {
		return head;
	}
	public void setHead(Entity head) {
		this.head = head;
	}
	public ArrayList<Relation> getRelationList() {
		return relationList;
	}
	public void setRelationList(ArrayList<Relation> relationList) {
		this.relationList = relationList;
	}
	public ArrayList<Entity> getEntityList() {
		return entityList;
	}
	public void setEntityList(ArrayList<Entity> entityList) {
		this.entityList = entityList;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void addEntity(Entity entity){
		entityPath.add(entity);
	}
	public void addRelation(Relation relation){
		relationPath.add(relation);
	}
	public void popEntity(){
		entityPath.pop();
	}
	public void popRelation(){
		relationPath.pop();
	}
	public Stack<Relation> getRelationPath() {
		return relationPath;
	}
	public void setRelationPath(Stack<Relation> relationPath) {
		this.relationPath = relationPath;
	}
	public Stack<Entity> getEntityPath() {
		return entityPath;
	}
	public void setEntityPath(Stack<Entity> entityPath) {
		this.entityPath = entityPath;
	}
}
