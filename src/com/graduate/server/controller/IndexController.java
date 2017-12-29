package com.graduate.server.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.MetaPath;
import com.graduate.server.model.Path;
import com.graduate.server.model.Result;
import com.graduate.server.model.SimEntity;
import com.graduate.server.model.Visual;
import com.graduate.server.service.ExploreService;
import com.graduate.server.service.IndexService;
import com.graduate.server.service.SearchService;
import com.graduate.server.service.imp.CommonService;

@Controller
@RequestMapping("/")
@Scope("prototype")
public class IndexController {
	@Autowired SearchService service;
	@Autowired ExploreService exservice;
	@Autowired IndexService inservice;
	@ResponseBody
	@RequestMapping(value="/getIndex",method=RequestMethod.GET)
	public Result getIndex(String[]query){
		DataLoad.InputType.clear();;
		Result res=new Result();
		long time=System.currentTimeMillis();
		List<String>list=null;
		if(query.length==0){
			list=inservice.getSearchHistory(1);
		}else{
			list=Arrays.asList(query);
		}
		for(String name:list){
			DataLoad.InputType.put(name, 1);
		}
		//List<Entity> querylist=new ArrayList<Entity>();
		List<Entity>entityList=service.getQueryEntity(list);
		//System.out.println(System.currentTimeMillis()-time);
		//querylist.add(entityList.get(0));
		//List<Feature>featureList=service.getQueryFeature(querylist);
		//System.out.println(System.currentTimeMillis()-time);
		List<Entity>simList=exservice.getSimEntity(entityList);
		//System.out.println(System.currentTimeMillis()-time);
		List<Feature>simFeaturelist=exservice.getSimFeature(simList);
		for(int i=0;i<simFeaturelist.size();i++){
			System.out.println(simFeaturelist.get(i).getTarget().getName()+" "+String.valueOf(simFeaturelist.get(i).getTarget().getScore())+" "+simFeaturelist.get(i).getRelation().getName());
		}
		//System.out.println(System.currentTimeMillis()-time);
		//Visual vis=exservice.getAllPath(queryli.get(0).getName(),simList);
		//System.out.println(System.currentTimeMillis()-time);
		//MetaPath mtph=service.getMetaPath(querylist);
		//res.setQueryEntity(entityList);
		//res.setQueryFeatureList(featureList);
		res.setSimEntityList(simList);
		res.setRecomendFeatureList(simFeaturelist);
		//res.setMetaPathList(mtph);
		//res.setVis(vis);
		//System.out.println(System.currentTimeMillis()-time);
		return res;
	}
	@ResponseBody
	@RequestMapping(value="/getWeightIndex",method=RequestMethod.GET)
	public Result getWeightIndex(String[]query,double[]weight){
		Result res=new Result();
		//long time=System.currentTimeMillis();
		for(int i=0;i<weight.length;i++){
			System.out.println(query[i]+" "+String.valueOf(weight[i]));
		}
		List<String>list=null;
		if(query.length==0){
			list=inservice.getSearchHistory(1);
		}else{
			list=Arrays.asList(query);
		}
		List<Entity>entityList=service.getQueryEntity(list,weight);
		List<Entity>simList=exservice.getSimEntity(entityList);
		List<Feature>simFeaturelist=exservice.getSimFeature(simList);
		res.setSimEntityList(simList);
		res.setRecomendFeatureList(simFeaturelist);
		return res;
	}
	@ResponseBody
	@RequestMapping(value="/getFeedback",method=RequestMethod.GET)
	public Result getIndex(String[]query,String[]nquery){
		System.out.println("begin search now");
		Result res=new Result();
		DataLoad.InputType.clear();
		//long time=System.currentTimeMillis();
		List<String>list=new ArrayList<String>();
		List<String>nlist=null;
		List<Entity>entityList=null;
		List<Entity>simList=null;
		List<Feature>simFeaturelist=null;
		if(query==null){
			list.add(inservice.getSearchHistory(1).get(0).toString());
		}else{
			for(int i=0;i<query.length;i++){
				list.add(query[i]);
			}
		}
		for(String name:list){
			DataLoad.InputType.put(name, 1);
		}
		if(nquery!=null){
			 nlist=Arrays.asList(nquery);
			 for(String name:nlist){
				 DataLoad.InputType.put(name, -1);
				 list.add(name);
			 }
		}
		entityList=service.getQueryEntity(list);
		simList=exservice.getSimEntity(entityList);
		simFeaturelist=exservice.getSimFeature(simList); 
		//for(int i=0;i<simList.size();i++) System.out.println(simList.get(i).getName()+" "+simList.get(i).getScore());
		//for(int i=0;i<simFeaturelist.size();i++) System.out.println(simFeaturelist.get(i).getRelation().getName()+" "+String.valueOf(simFeaturelist.get(i).getTarget().getScore())+" "+simFeaturelist.get(i).getTarget().getName());
		res.setSimEntityList(simList);
		res.setRecomendFeatureList(simFeaturelist);
		System.out.println("finish search now");
		return res;
	}
	@ResponseBody
	@RequestMapping(value="getcategory",method=RequestMethod.POST)
	public List<Entity>getCategory(@RequestBody String query){
		//if(query.equals("virginia o'brien")) System.out.println("Equals");
		Entity queryEntity=DataUtil.getEnityByName(query);
		List <Entity>list=new ArrayList<Entity>();
		if(queryEntity!=null){
			list=CommonService.getCategory(queryEntity);
			/*for(int i=0;i<list.size();i++){
				System.out.println(list.get(i).getName());
			}*/
		}
		return list;
	}
	@ResponseBody
	@RequestMapping(value="saveImg",method=RequestMethod.POST)
	public int saveImg(){
		inservice.saveImg();
		return 1;
	}
	@ResponseBody
	@RequestMapping(value="updateImg",method=RequestMethod.POST)
	public int updateImg(){
		inservice.updateImg();
		return 1;
	}
	@ResponseBody
	@RequestMapping(value="getImg",method=RequestMethod.GET)
	public HashMap<String,String> getImage(String name){
		//System.out.println(inservice.getImg(name));
		//System.out.println(name);
		String ss=inservice.getImg(name);
		//System.out.println(ss);
		HashMap<String,String>mp=new HashMap<String,String>();
		mp.put("img",ss);
		return mp;
	}
}
