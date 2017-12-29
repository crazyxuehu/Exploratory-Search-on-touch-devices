package com.graduate.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MetaPath {
	private HashMap<String,Visual>category;
	private HashMap<String,Visual>relation;
	
	public MetaPath(){
		this.category=new HashMap<String, Visual>();
		this.relation=new HashMap<String, Visual>();
	}
	public MetaPath(HashMap<String, Visual> category,
			HashMap<String, Visual> relation) {
		this.category = category;
		this.relation = relation;
	}
	public HashMap<String, Visual> getCategory() {
		return category;
	}
	public void setCategory(HashMap<String, Visual> category) {
		this.category = category;
	}
	public HashMap<String, Visual> getRelation() {
		return relation;
	}
	public void setRelation(HashMap<String, Visual> relation) {
		this.relation = relation;
	}
	
}