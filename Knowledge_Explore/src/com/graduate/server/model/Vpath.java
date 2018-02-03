package com.graduate.server.model;

import java.util.Stack;

public class Vpath {
	private Stack<String> relationPath;
	private Stack<String>entityPath;
	public Vpath(){
		this.relationPath=new Stack<String>();
		this.entityPath=new Stack<String>();
	}
	public Vpath(Stack<String>relationPath,Stack<String>entityPath){
		this.relationPath=relationPath;
		this.entityPath=entityPath;
	}
	public void addEntity(String name){
		this.entityPath.add(name);
	}
	public void addRelation(String name){
		this.relationPath.add(name);
	}
}
