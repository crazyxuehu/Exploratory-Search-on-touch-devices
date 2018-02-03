package com.graduate.server.model;

public class Vertex {
	private String name;
	private int x;
	private int y;
	private int category;
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Vertex(String name,int x,int y,int category){
		this.name=name;
		this.x=x;
		this.y=y;
		this.category=category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
