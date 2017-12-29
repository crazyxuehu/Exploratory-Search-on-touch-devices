package com.graduate.server.model;

import java.util.ArrayList;
import java.util.List;

public class SimEntity {
	private List<Entity> simEntityList;
	private List<Entity> simCategoryList;
	public SimEntity(List simEntityList,List simCategoryList){
		this.simCategoryList=simCategoryList;
		this.simEntityList=simEntityList;
	}
	public SimEntity(){
		this.simEntityList=new ArrayList<Entity>();
		this.simCategoryList=new ArrayList<Entity>();
	}
	public List<Entity> getSimEntityList() {
		return simEntityList;
	}
	public void setSimEntityList(List<Entity> simEntityList) {
		this.simEntityList = simEntityList;
	}
	public List<Entity> getSimCategoryList() {
		return simCategoryList;
	}
	public void setSimCategoryList(List<Entity> simCategoryList) {
		this.simCategoryList = simCategoryList;
	}
}
