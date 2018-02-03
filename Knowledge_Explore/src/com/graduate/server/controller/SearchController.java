package com.graduate.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import com.graduate.server.common.DataUtil;
import com.graduate.server.common.IndexBuild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.*;

import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Link;
import com.graduate.server.model.MetaPath;
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.model.Result;
import com.graduate.server.model.SimEntity;
import com.graduate.server.model.Vertex;
import com.graduate.server.model.Visual;
import com.graduate.server.model.Node;
import com.graduate.server.service.ExploreService;
import com.graduate.server.service.IndexService;
import com.graduate.server.service.SearchService;
import com.graduate.server.service.imp.CommonService;

@Controller
@RequestMapping("/")
@Scope("prototype")
public class SearchController {
	private static long time;
	@Autowired SearchService service;
	@Autowired ExploreService exservice;
	@Autowired IndexService inservice;
	@ResponseBody
	@RequestMapping(value="test",method=RequestMethod.GET)
	public HashMap getQueryResult(String query){
		long time=System.currentTimeMillis();
		System.out.println(query);
		List<String>ll=inservice.autoComplete(query, 6);
		HashMap<String,List<String>>mp=new HashMap<String,List<String>>();
		mp.put("result",ll);
		return mp;
		/*System.out.println(System.currentTimeMillis());	
		//list=inservice.getHistory();
		List ll=service.getQueryEntity((List<String>)(mp.get("mylist")));
		String head=mp.get("head").toString();
		String tail=mp.get("tail").toString();
		service.saveQuery(ll);
		res.setQueryEntity(ll);
		List<Feature> flist=service.getQueryFeature(ll);
		System.out.println("featureList:");
		for(Feature feature:flist){
			System.out.println(feature.getQuery().getName()
					+" "+feature.getRelation()
					.getName()+" "+feature
					.getTarget().getName());
		}
		List<Entity>simlist=exservice.getSimEntity(ll);
		System.out.println("similar entity:");
		for(Entity entity:simlist){
			System.out.println(entity.getName()+" "+entity.getScore());
		}
		System.out.println("similar feature");
		System.out.println(System.currentTimeMillis()-time);
		List<Feature>featureList=exservice.getSimFeature(simlist);
		for(Feature feature:featureList){
			System.out.println(feature.getRelation().getRelationId()+" "
					+feature.getRelation()
					.getName()+" "+feature
					.getTarget().getName());
		}
		
		//String head="iskul bukol";
		//String tail="category:intercontinental broadcasting corporation";
		List<Path>pathList;
		pathList=exservice.getPath(head, tail);
		System.out.println(pathList.size());
		for(Path path:pathList){
			System.out.print(path.getHead().getName()+" ");
			for(int i=0;i<path.getRelationPath().size();i++){
				Relation relation=path.getRelationPath().get(i);
				Entity entity=path.getEntityPath().get(i);
				System.out.print(relation.getName()+" "+entity.getName());
			}
			System.out.println();
		}
		//CommonService.test(DataUtil.getEnityByName(head));
		//CommonService.getRelationTop(ll);
		//System.out.println(System.currentTimeMillis()-time);
		pathList=service.getMetaPathByCategory(ll);
		for(Path path:pathList){
			for(Relation relation:path.getRelationList()){
				System.out.print(path.getHead().getName()+" ");
				System.out.println(relation.getName()+" ");
			}
		}
		pathList=service.getMetaPathByRelation(ll);
		for(Path path:pathList){
			for(Entity entity:path.getEntityList()){
				System.out.print(path.getRelation().getName()+" ");
				System.out.println(entity.getName()+" ");
			}
		}*/
		//System.out.println(System.currentTimeMillis()-time);
	}
	@ResponseBody
	@RequestMapping(value="getSearchResult",method=RequestMethod.GET)
	public Result getSearchResult(String query){
		//time=System.currentTimeMillis();
		List<String>queryList=new ArrayList<String>();
		queryList.add(query);
		List ll=service.getQueryEntity(queryList);
		String desc=inservice.getAbstract(query);
		Result res=null;
		if(ll!=null){
			//service.saveQuery(ll);
			res=new Result();
			//res.setQueryEntity(ll);
			List<Feature>list=service.getQueryFeature(ll);
			//List<String>entityList=new ArrayList<String>();
			/*for(Feature feature:list){
				//System.out.println(feature.getQuery().getName()+" "+feature.getRelation().getName()+" "+feature.getTarget().getName());
				entityList.add(feature.getTarget().getName());
			}
			inservice.saveEntity(entityList);*/
			System.out.print("Query feature:");
			System.out.println(System.currentTimeMillis()-time);
			res.setQueryFeatureList(list);
			res.setDescription(desc);
		}
		return res;
	}
	@ResponseBody
	@RequestMapping(value="getSimEntity",method=RequestMethod.POST)
	public List<Entity> getSimEntityResult(@RequestBody List<String>queryList){
		List<Entity>ll=service.getQueryEntity(queryList);
		//System.out.println(ll.size());
		List<String>list=new ArrayList<String>();
		ll=exservice.getSimEntity(ll);
		/*for(Entity entity:ll){
			list.add(entity.getName());
		}
		inservice.saveEntity(list);*/
		System.out.print("sim Entity:");
		System.out.println(System.currentTimeMillis()-time);
		return ll;
	}
	@ResponseBody
	@RequestMapping(value="getSimFeature",method=RequestMethod.POST)
	public List<Feature> getSimFeatureResult(@RequestBody List<String>queryList){
		List ll=service.getQueryEntity(queryList);
		List<Entity>simlist=exservice.getSimEntity(ll);
		List<Feature>featureList=exservice.getSimFeature(ll);
		List<String>list=new ArrayList<String>();
		for(Feature feature:featureList){
			//list.add(feature.getTarget().getName());
			//System.out.println(feature.getScore());
		}
		inservice.saveEntity(list);
		System.out.print("Similar feature:");
		System.out.println(System.currentTimeMillis()-time);
		return featureList;
	}
	@ResponseBody
	@RequestMapping(value="getExAllPath",method=RequestMethod.POST)
	public Visual getExplainAllPath(@RequestBody Map mp){
		List<String>ll=(List<String>)mp.get("tail");
		List<Entity>list=new ArrayList<Entity>();
		String head=(String) mp.get("head");
		Visual vis=new Visual();
		Entity headEntity=DataUtil.getEnityByName(head);
		if(headEntity==null) return vis;
		if(ll.size()>0){
			list=service.getQueryEntity(ll);
		}else{
			list.add(headEntity);
			list=exservice.getSimEntity(list);
		}
		/*for(Entity entity:list){
			//System.out.println();
		}*/
		//System.out.println(head);
		//System.out.println(list.size());
		
		//long time=System.currentTimeMillis();
		//long time=System.currentTimeMillis();
		vis=exservice.getAllPath(head, list);
		/*for(Node nd:vis.getNodeList()){
			System.out.println(nd.getName()+" "+nd.getCategory());
		}*/System.out.print("path explain:");
		System.out.println(System.currentTimeMillis()-time);
		//System.out.println(System.currentTimeMillis()-time);
		/*for(Node node :vis.getNodeList()){
			System.out.println(node.getName()+" "+node.getX()+" "+node.getY());
		}
		for(Link link:vis.getLinkList()){
			System.out.println(link.getSource()+" "+link.getTarget());
		}*/
		return vis;
	}
	@ResponseBody
	@RequestMapping(value="getExPath",method=RequestMethod.GET)
	public Visual getExplainPath(String head,String tail){
		System.out.println("yese we can");
		/*Entity headEntity=DataUtil.getEnityByName(head);
		Entity tailEntity=DataUtil.getEnityByName(tail);*/
		//long time=System.currentTimeMillis();
		long time=System.currentTimeMillis();
		Visual vis=new Visual();
		if(head!=null&&tail!=null){
			vis=exservice.getPath(head,tail);
		}
		/*for(Vertex nd:vis.getVertexList()){
			System.out.println(nd.getName()+" "+nd.getX()+" "+nd.getY());
		}System.out.print("path explain:");
		System.out.println(System.currentTimeMillis()-time);
		//System.out.println(System.currentTimeMillis()-time);*/
		for(Node node :vis.getNodeList()){
			System.out.println(node.getName());
		}
		for(Link link:vis.getLinkList()){
			System.out.println(link.getSource()+" "+link.getTarget());
		}
		return vis;
	}
	@ResponseBody
	@RequestMapping(value="getMetaPath",method=RequestMethod.POST)
	public MetaPath getMetaPath(@RequestBody List<String>list){
		List<Entity>queryList=service.getQueryEntity(list);
		MetaPath metp=service.getMetaPath(queryList);
		/*for(String name:metp.getCategory().keySet()){
			System.out.print(name+" ");
			System.out.print(metp.getCategory().get(name).getNodeList().size()+" ");
			System.out.println(metp.getCategory().get(name).getLinkList().size());
			
		}
		for(String name:metp.getRelation().keySet()){
			System.out.println(name+" ");
			System.out.print(metp.getRelation().get(name).getNodeList().size()+" ");
			System.out.println(metp.getRelation().get(name).getLinkList().size());
		}*/
		System.out.print("path explore:");
		System.out.println(System.currentTimeMillis()-time);
		return metp;
	}
}
