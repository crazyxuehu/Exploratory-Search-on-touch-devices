package com.graduate.server.service;

import java.util.HashMap;
import java.util.List;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.MetaPath;
import com.graduate.server.model.Path;
import com.graduate.server.model.Visual;



public interface SearchService {
	public List getQueryEntity(List<String>list);
	public List getQueryEntity(List<String>list,List<Double>weight);
	public List getFeature(List<String> featureList,List<Integer>directionList,List<String>relationList,List<Double>featureWeight);
	public List<Feature>getQueryFeature(List<Entity>list);
	public void saveQuery(List<Entity>list,List<Feature>featureList);
	public void saveQuery(List<Entity>list);
	public HashMap<String,Visual>getMetaPathByCategory(List<Entity>queryList);
	public HashMap<String,Visual>getMetaPathByRelation(List<Entity>queryList);
	public MetaPath getMetaPath(List<Entity>queryList);
}
