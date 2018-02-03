package com.graduate.server.service;

import java.util.List;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Path;
import com.graduate.server.model.Visual;

public interface ExploreService {
	public List<Entity>getSimEntity(List<Entity>query);
	public List<Entity>getSimEntity(List<Entity>query,List<Feature>feature);
	List<Feature> getSimFeature(List<Entity> queryList);
	public Visual getPath(String head,String tail);
	public Visual getAllPath(String head,List<Entity> list);
}
