package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Relation;
import com.graduate.server.model.RelationSize;

public class CommonService {
	//sort entity list and get top(k)
	public static List<Entity> RankEntity(List<Entity> target,int k) {
		int size=target.size()>k?k:target.size();
		//System.out.println(size);
		return target.parallelStream()
				.sorted((a,b)->a.getScore()<b.getScore()?1:a.getScore()==b.getScore()?0:-1)
				.limit(size)
				.collect(Collectors.toList());
	}
	//sort feature
	public static List<Feature> RankFeature(List<Feature>featureList){
		return featureList.parallelStream()
					.sorted((a,b)->a.getScore()<b.getScore()?1:a.getScore()==b.getScore()?0:-1)
					.collect(Collectors.toList());
	}
	//计算top(k)的relation
	public static List<Relation>getRelationTop(List<Entity>list){
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		for(Entity i:list){
			for(int j=0;j<2;j++){
				if(DataUtil.JuRelationByEntity(j,i.getId())){
					for(HashSet<Integer> relation:DataLoad.tripleHash.get(j).get(i.getId()).values()){
						int num=relation.size();//计算relation连接实体集的最大值和最小值
						min_size=min_size>num?num:min_size;
						max_size=max_size<num?num:max_size;
					}
				}
			}
		}
		HashMap<Integer, Relation> relationMap = new HashMap<>();
		for(Entity i:list){
				int size=min_size+max_size;
			for(int j=0;j<2;j++){//查询实体出发，在2个方向上寻找实体集
				if(DataUtil.JuRelationByEntity(j,i.getId())){
					for(Entry<Integer,HashSet<Integer>> relationEntity:DataLoad.tripleHash.get(j).get(i.getId()).entrySet()){
						int key=relationEntity.getKey();
						double score=0;
						Relation relation=new Relation(DataLoad.Relation_type,j,key);
						if(key==DataUtil.getRelationId("subject")){
							score=0.03;
						}else{
							score=(double)relationEntity.getValue().size()/size;//计算信息熵
							score = (- score * Math.log(score)) *i.getScore();
						}
						relation.setScore(score);
						if(relationMap.containsKey(key))//实现累加
							relationMap.get(key).setScore(relationMap.get(key).getScore()+score);
						else{
							relationMap.put(relationEntity.getKey(), relation);
						}
					}
				}
			}
		}//对获取的relaiton进行筛选和排序
		return RankRelation(new ArrayList<Relation>(relationMap.values()),Integer.MAX_VALUE);
	}
	
	//计算1个entity的top(K)的relation
/*	public static List<Relation>getRelationTop(Entity queryEntity){
		int min_size,max_size;
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		List<RelationSize>Relation_size=new ArrayList<RelationSize>();
		for(int j=0;j<2;j++){
			if(DataUtil.JuRelationByEntity(j,queryEntity.getId())){
				for(HashSet<Integer> relation:DataLoad.tripleHash.get(j).get(queryEntity.getId()).values()){
					int num=relation.size();
					min_size=min_size>num?num:min_size;
					max_size=max_size<num?num:max_size;
				}
			}
		}
		HashMap<Integer, Relation> relationMap = new HashMap<>();
		//int count=0;
			int size=min_size+max_size;
			for(int j=0;j<2;j++){
				if(DataUtil.JuRelationByEntity(j,queryEntity.getId())){
					for(Entry<Integer,HashSet<Integer>> relationEntity:DataLoad.tripleHash.get(j).get(queryEntity.getId()).entrySet()){
						int key=relationEntity.getKey();
						double score=0;
						Relation relation=new Relation(DataLoad.Relation_type,j,key);
						if(key==DataUtil.getRelationId("subject")){
							score=1;
						}else{
							score=(double)relationEntity.getValue().size()/size;
							score = (- score * Math.log(score)) *queryEntity.getScore();
						}
						relation.setScore(score);
						if(relationMap.containsKey(key))
							relationMap.get(key).setScore(relationMap.get(key).getScore()+score);
						else{
							relationMap.put(relationEntity.getKey(), relation);
						}
					}
				}
			}
		//System.out.println("Entity Num:"+count);
		//System.out.println("Entity Num:"+relationMap.size());
		return RankRelation(new ArrayList<Relation>(relationMap.values()),Integer.MAX_VALUE);
	}*/
	public static List<Relation>getRelationTop(Entity queryEntity){
		HashMap<Integer, Relation> relationMap = new HashMap<>();
		for(int i=0;i<2;i++){
			if(DataUtil.JuRelationByEntity(i,queryEntity.getId())){
				for(Entry<Integer,HashSet<Integer>> relationEntity:DataLoad.tripleHash.get(i).get(queryEntity.getId()).entrySet()){
						if(!relationMap.containsKey(relationEntity.getKey())){
							relationMap.put(relationEntity.getKey(),DataUtil.getRelationById(relationEntity.getKey(),i));
						}
					}
				}
			}
		return new ArrayList<Relation>(relationMap.values());
	}
	//根据relation获取目标实体
	public static List<Entity> getTarget(List<Entity>queryList,Relation relation){
		HashMap<Integer, Entity> targetEntityMap = new HashMap<>();
		queryList.stream()
			.forEach(f->{
			if(DataUtil.JuRelationByEntity(relation.getDirection(),f.getId())){
				if(DataUtil.JuRelationByRelation(relation.getDirection(),f.getId(),relation.getRelationId())){
					double []queryVector=DataUtil.getEntityVector(f.getId());
					DataLoad.tripleHash.get(relation.getDirection()).get(f.getId())
					.get(relation.getRelationId()).stream()//获取和query存在relation关系的目标实体集
							.map(e->DataUtil.getScoreofEntity(f.getId(),queryVector,DataUtil.getEntityVector(e),e,relation))
							.filter(e->e!=null)
							.forEach(e->{//通过向量计算相似度
								if(targetEntityMap.containsKey(e.getId())){//多个实体时，相似度求和
									targetEntityMap.get(e.getId()).setScore(targetEntityMap.get(e.getId()).getScore()+e.getScore());
								}else{
									targetEntityMap.put(e.getId(),e);
								}
							});
				}
			}
		});
		return new ArrayList<Entity>(targetEntityMap.values());//返回所有的目标实体集
	}
	public static List<Entity> getTarget(Entity queryEntity,Relation relation){
		if(DataUtil.JuRelationByEntity(relation.getDirection(),queryEntity.getId())){
			if(DataUtil.JuRelationByRelation(relation.getDirection(),queryEntity.getId(),relation.getRelationId())){
				return DataLoad.tripleHash.get(relation.getDirection()).get(queryEntity.getId())
				.get(relation.getRelationId()).stream()
				.map(id->DataUtil.getEntityById(id))
				.filter(id->id!=null)
				.collect(Collectors.toList());
			}
		}
		return null;
	}
	//根据relation和查询实体获取相似实体
	public static List<Entity> getTarget(Entity queryEntity,Relation relation,Entity targetEntity){
		HashMap<Integer, Entity> targetEntityMap = new HashMap<>();
			if(DataUtil.JuRelationByEntity(relation.getDirection(),queryEntity.getId())){
				if(DataUtil.JuRelationByRelation(relation.getDirection(),queryEntity.getId(),relation.getRelationId())){
					double []queryVector=DataUtil.getEntityVector(queryEntity.getId());
					DataLoad.tripleHash.get(relation.getDirection()).get(queryEntity.getId())
					.get(relation.getRelationId()).stream()
							.map(e->DataUtil.getScoreofEntity(queryVector,DataUtil.getEntityVector(e),e))
							.forEach(e->{
								if(targetEntityMap.containsKey(e.getId())){
									targetEntityMap.get(e.getId()).setScore(targetEntityMap.get(e.getId()).getScore()+e.getScore());
								}else{
									targetEntityMap.put(e.getId(),e);
								}
							});
				}
			}
		return new ArrayList<Entity>(targetEntityMap.values());
	}
	//对relation序列排序
	public static List<Relation> RankRelation(ArrayList<Relation>list,int k){
		int size=list.size()<k?list.size():k;
		//System.out.println("Relation Size:"+size);
		return list.parallelStream().sorted((a,b)->a.getScore()>b.getScore()?1:-1)
							.limit(size)
							.collect(Collectors.toList());
		/*for(int i=0;i<ll.size();i++){
			System.out.println(ll.get(i).getRelationId()+" "+ll.get(i).getScore());
		}*/
	}
	public static void test(List<Entity>list){
		List<Entity> ll=RankEntity(list, 10);
		/*for(Entity entity:ll){
			System.out.println(entity.getId()+" "+entity.getScore());
		}*/
		ll=RankEntity(list, 15);
		/*for(Entity entity:ll){
			System.out.println(entity.getId()+" "+entity.getScore());
		}*/
	}
	public static List<Entity>getCategory(List<Entity>queryList){
		HashMap<Integer,Entity> categoryMap=new HashMap<Integer,Entity>();
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		int relationId=DataUtil.getRelationId("subject");
		for(Entity entity:queryList){
			if(DataUtil.JuRelationByEntity(0,entity.getId())){
				if(DataUtil.JuRelationByRelation(0, entity.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(0).get(entity.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1,category)){
							int size=DataLoad.tripleHash.get(1).get(category).values().size();
							min_size=min_size>size?size:min_size;
							max_size=max_size<size?size:max_size;
						}
					}
				}
			}else if(DataUtil.JuRelationByEntity(1,entity.getId())){
				int size=DataLoad.tripleHash.get(1).get(entity.getId()).values().size();
				min_size=min_size>size?size:min_size;
				max_size=max_size<size?size:max_size;
			}
		}
		int size=min_size+max_size;
		for(Entity entity:queryList){
			if(DataUtil.JuRelationByEntity(0,entity.getId())){
				if(DataUtil.JuRelationByRelation(0, entity.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(0).get(entity.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1,category)){
							double score=DataLoad.tripleHash.get(1).get(category).values().size()/(size*1.0);
							//System.out.println("score1"+DataLoad.tripleHash.get(1).get(category).values().size()+" "+size);
							score=-score*Math.log(score)*1;
							//System.out.println("1//"+score);
							score+=entity.getScore();
							//System.out.println("2//"+score);
							if(categoryMap.containsKey(category)){
								//System.out.println("categorymap:"+categoryMap.get(category).getScore());
								categoryMap.get(category).setScore(categoryMap.get(category).getScore()+score);
							}else{
								categoryMap.put(category,new Entity(category,score));
							}
						}
					}
				}
			}else if(DataUtil.JuRelationByEntity(1,entity.getId())){
				double score=DataLoad.tripleHash.get(1).get(entity.getId()).values().size()/(size*1.0);
				score=-score*Math.log(score)*entity.getScore();
				if(categoryMap.containsKey(entity.getId())){
					categoryMap.get(entity.getId()).setScore(categoryMap.get(entity.getId()).getScore()+score);
				}else{
					categoryMap.put(entity.getId(),new Entity(entity.getId(),score));
				}
			}
		}
		return RankEntity(new ArrayList<Entity>(categoryMap.values()),10);
	}
	
	public static List<Relation>getTargetRelation(List<Entity>queryList,Entity category){
		int relationId=DataUtil.getRelationId("subject");
		List<Relation>relationList=new ArrayList<Relation>();
			if(DataUtil.JuRelationByEntity(1,category.getId())){
				if(DataUtil.JuRelationByRelation(1, category.getId(), relationId)){
					List<Entity>targetList=DataLoad.tripleHash.get(1).get(category.getId()).get(relationId).stream()
							.map(entity->DataUtil.getScoreofEntity(entity, queryList))
							.collect(Collectors.toList());
					targetList=RankEntity(targetList,DataLoad.Out_Feature_Size);
					relationList=getRelationTop(targetList);
				}
			}
		return relationList;
	}
	public static List<Entity> getCategory(Entity query){
		HashMap<Integer,Entity> categoryMap=new HashMap<Integer,Entity>();
		int min_size=Integer.MAX_VALUE;
		int max_size=0;
		int relationId=DataUtil.getRelationId("subject");
			if(DataUtil.JuRelationByEntity(0,query.getId())){
				if(DataUtil.JuRelationByRelation(0, query.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(0).get(query.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1,category)){
							int size=DataLoad.tripleHash.get(1).get(category).values().size();
							min_size=min_size>size?size:min_size;
							max_size=max_size<size?size:max_size;
						}
					}
				}
			}else if(DataUtil.JuRelationByEntity(1,query.getId())){
				int size=DataLoad.tripleHash.get(1).get(query.getId()).values().size();
				min_size=min_size>size?size:min_size;
				max_size=max_size<size?size:max_size;
			}
		int size=min_size+max_size;
		for(int j=0;j<1;j++){
			if(DataUtil.JuRelationByEntity(j,query.getId())){
				if(DataUtil.JuRelationByRelation(j, query.getId(), relationId)){
					for(Integer category:DataLoad.tripleHash.get(j).get(query.getId()).get(relationId)){
						if(DataUtil.JuRelationByEntity(1-j,category)){
							double score=DataLoad.tripleHash.get(1-j).get(category).values().size()/(size*1.0);
							//System.out.println("score1"+DataLoad.tripleHash.get(1).get(category).values().size()+" "+size);
							score=-score*Math.log(score)*1;
							//System.out.println("1//"+score);
							score+=query.getScore();
							//System.out.println("2//"+score);
							if(categoryMap.containsKey(category)){
								//System.out.println("categorymap:"+categoryMap.get(category).getScore());
								categoryMap.get(category).setScore(categoryMap.get(category).getScore()+score);
							}else{
								categoryMap.put(category,new Entity(category,score));
							}
						}
					}
				}
			}
		}
		return RankEntity(new ArrayList<Entity>(categoryMap.values()),DataLoad.Out_Entity_Size);
	}
}
