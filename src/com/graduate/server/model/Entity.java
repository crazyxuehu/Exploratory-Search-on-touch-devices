package com.graduate.server.model;

import com.graduate.server.common.DataUtil;

public class Entity {
	int type;
	int id;
	String name;
	String category;
	double score;
	public Entity(){
		type=1;
		score=1;
	}
	public Entity(int id,double score){
		this.id=id;
		this.score=score;
		this.name=DataUtil.getEntityName(id);
	}
	public Entity(int id){
		this.id=id;
		score=1;
	}
	public Entity(int id,String name){
		this.id=id;
		this.name=name;
		this.score=1;
	}
	public Entity(int id,String name,double score){
		this.id=id;
		this.name=name;
		this.score=score;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getScore() {
		return this.score;
	}
	public void setScore(double score) {
		this.score = score;
	}
/*	public static Entity setEntity(int id){
		return new Entity(id);
	}*/
/*	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Entity)){
			return false;
		}else if(this==obj){
			return true;			
		}
		return this.getId()==((Entity)obj).getId();
	}
	@Override
	public int hashCode(){
		return this.getId();
	}*/
}
