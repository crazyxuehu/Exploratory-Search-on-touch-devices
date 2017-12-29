package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.dao.SearchDao;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Link;
import com.graduate.server.model.MetaPath;
import com.graduate.server.model.Node;
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.model.RelationSize;
import com.graduate.server.model.Visual;
import com.graduate.server.service.SearchService;

@Service
public class SearchServiceImp implements SearchService {

	@Autowired SearchDao dao;

	@Override//获取查询实体
	public List getQueryEntity(List<String>list) {
		List<Entity>ll=list.stream()
				.map(word->DataUtil.getEnityByName(word))
				.filter(word->word!=null)
				.collect(Collectors.toList());
		return ll;
	}

	@Override//获取查询实体的特征集
	public List<Feature>getQueryFeature(List<Entity> list) {
		List<Feature> featureList=new ArrayList<Feature>();
		int num=0;
		//根据信息熵对relation进行排序
		for(Relation relation:CommonService.getRelationTop(list)){
			int id=DataUtil.getRelationId("subject");
			//根据相似度对目标实体进行排序
			for(Entity entity:CommonService.RankEntity(CommonService.getTarget(list,relation),
			relation.getRelationId()==id?10:DataLoad.Out_Feature_Size)){
				for(Entity query:list){
					if(DataUtil.JuTriple(query, relation, entity)){
						featureList.add(new Feature(query,relation, entity
						, entity.getScore() * relation.getScore()));//feature的分数由信息熵与实体相似度的乘积决定
					}
				}
			}
		}
		return featureList.stream()//对特征集根据进行筛选和排序
		.sorted((a,b)->a.getQuery().getName().compareTo(b.getQuery().getName())==0?a.getRelation().getName().compareTo(b.getRelation().getName()):a.getQuery().getName().compareTo(b.getQuery().getName())).collect(Collectors.toList());
	}

	@Override
	public void saveQuery(List<Entity> list) {
		dao.saveQuery(list);	
	}

	@Override
	public HashMap<String,Visual> getMetaPathByCategory(List<Entity> queryList) {
		List<Entity>categoryList=CommonService.getCategory(queryList);
		HashMap<String,List<Relation>>categoryMap=new HashMap<String, List<Relation>>();
		HashMap<String,Visual>map=new HashMap<String, Visual>();
		for(Entity category:categoryList){
			List<Node>nodeList=new ArrayList<Node>();
			List<Link>linkList=new ArrayList<Link>();
			String[]name=category.getName().split(":");
			String cateName=category.getName();
			if(name.length>1){
				cateName=name[1];
			}
			for(Relation relation:CommonService.getTargetRelation(queryList, category)){
				nodeList.add(new Node(relation.getName(),1));
				linkList.add(new Link(cateName,relation.getName(),"relation"));
			}
			Visual vis=new Visual(nodeList,linkList);
			map.put(cateName, vis);
		}
		return map;
	}

	@Override
	public HashMap<String,Visual> getMetaPathByRelation(List<Entity> queryList) {
		List<MetaPath>pathList=new ArrayList<MetaPath>();
		HashMap<String,Visual>map=new HashMap<String, Visual>();
		for(Relation relation:CommonService.getRelationTop(queryList)){
			List<Entity>entityList=CommonService.getTarget(queryList, relation);
			List<Node>nodeList=new ArrayList<Node>();
			List<Link>linkList=new ArrayList<Link>();
			for(Entity entity:CommonService.getCategory(entityList)){
				String [] name=entity.getName().split(":");
				if(name.length>1){
					nodeList.add(new Node(name[1],0));
					linkList.add(new Link(relation.getName(),name[1],"subject"));
				}else{
					nodeList.add(new Node(entity.getName(),0));
					linkList.add(new Link(relation.getName(),entity.getName(),"subject"));
				}
			}
			Visual vis=new Visual(nodeList,linkList);
			map.put(relation.getName(),vis);
		}
		return map;
	}

	@Override
	public MetaPath getMetaPath(List<Entity> queryList) {
		MetaPath mtp=new MetaPath();
		mtp.setCategory(getMetaPathByCategory(queryList));
		mtp.setRelation(getMetaPathByRelation(queryList));
		return mtp;
	}

	@Override
	public List getQueryEntity(List<String> list, double[] weight) {
		List<Entity>ll=new ArrayList<Entity>();
		for(int i=0;i<list.size();i++){
			Entity entity=DataUtil.getEnityByName(list.get(i), weight[i]);
			if(entity!=null){
				ll.add(entity);
			}
		}
		return ll;
	}
}
