package com.graduate.server.model;

public class Node {
	private String name;
	private int category;
	private boolean draggable;
	public Node(String name, int category) {
		this.name = name;
		this.category = category;
		this.draggable =true;
	}
	public Node(String name){
		this.name=name;
		this.draggable=true;
		this.category=2;
	}
	public Node(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public boolean isDraggable() {
		return draggable;
	}
	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}
}
