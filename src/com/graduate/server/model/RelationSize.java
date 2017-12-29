package com.graduate.server.model;

public class RelationSize {
	private Entity entity;
	private int Direction;
	private int relation_size_min;
	private int relation_size_max;
	public RelationSize(){
		relation_size_min=Integer.MAX_VALUE;
		relation_size_max=0;
	}
	public RelationSize(Entity entity,int min,int max){
		this.entity=entity;
		//this.Direction=Direction;
		relation_size_max=max;
		relation_size_min=min;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	public int getRelation_size_min() {
		return relation_size_min;
	}
	public void setRelation_size_min(int relation_size_min) {
		this.relation_size_min = relation_size_min;
	}
	public int getRelation_size_max() {
		return relation_size_max;
	}
	public void setRelation_size_max(int relation_size_max) {
		this.relation_size_max = relation_size_max;
	}
	
}
