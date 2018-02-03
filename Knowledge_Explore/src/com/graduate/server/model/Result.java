package com.graduate.server.model;

import java.util.ArrayList;
import java.util.List;


public class Result {
	private List<Entity>  queryEntity ;
	private List<Feature> queryFeatureList ;
	private List<Entity> simEntityList ;
	private List<Feature>recomendFeatureList;
	private MetaPath metaPathList;
	private Visual vis;
	private String description;
	public String getDescription() {
		return description;
	}
	public Result(){
		queryEntity=new ArrayList<Entity>();
		queryFeatureList=new ArrayList<Feature>();
		simEntityList=new ArrayList<Entity>();
		recomendFeatureList=new ArrayList<Feature>();
		metaPathList=new MetaPath();
	}
	public List<Entity>getQueryEntity() {
		return queryEntity;
	}
	public void setQueryEntity(List queryEntity) {
		this.queryEntity = queryEntity;
	}
	public List<Feature> getQueryFeatureList() {
		return queryFeatureList;
	}
	public void setQueryFeatureList(List<Feature> queryFeatureList) {
		this.queryFeatureList = queryFeatureList;
	}
	public List<Feature> getRecomendFeatureList() {
		return recomendFeatureList;
	}
	public void setRecomendFeatureList(List<Feature> recomendFeatureList) {
		this.recomendFeatureList = recomendFeatureList;
	}
	public MetaPath getMetaPathList() {
		return metaPathList;
	}
	public void setMetaPathList(MetaPath metaPathList) {
		this.metaPathList = metaPathList;
	}
	public Visual getVis() {
		return vis;
	}
	public void setVis(Visual vis) {
		this.vis = vis;
	}
	public List<Entity> getSimEntityList() {
		return simEntityList;
	}
	public void setSimEntityList(List<Entity> simEntityList) {
		this.simEntityList = simEntityList;
	}
	public void setDescription(String desc) {
		this.description=desc;
	}
	

}
