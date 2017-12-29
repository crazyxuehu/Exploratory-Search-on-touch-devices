package com.graduate.server.model;

import java.util.ArrayList;
import java.util.List;

public class Visual {
	private List<Node>nodeList;
	private List<Link>linkList;
	private List<Vertex>vertexList;
	private List<Edge>edgeList;
	public Visual(List<Node>nodeList,List<Link>linkList){
		this.nodeList=nodeList;
		this.linkList=linkList;
	}
	public Visual(){
		nodeList=new ArrayList<Node>();
		linkList=new ArrayList<Link>();
		vertexList=new ArrayList<Vertex>();
		edgeList=new ArrayList<Edge>();
	}
	public List<Vertex> getVertexList() {
		return vertexList;
	}
	public void setVertexList(List<Vertex> vertexList) {
		this.vertexList = vertexList;
	}
	public List<Edge> getEdgeList() {
		return edgeList;
	}
	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}
	public List<Node> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	public List<Link> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<Link> linkList) {
		this.linkList = linkList;
	}
}
