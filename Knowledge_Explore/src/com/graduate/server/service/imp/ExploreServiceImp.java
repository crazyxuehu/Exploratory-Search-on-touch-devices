package com.graduate.server.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.graduate.server.common.DataLoad;
import com.graduate.server.common.DataUtil;
import com.graduate.server.model.Edge;
import com.graduate.server.model.Entity;
import com.graduate.server.model.Feature;
import com.graduate.server.model.Link;
import com.graduate.server.model.Node;
import com.graduate.server.model.Path;
import com.graduate.server.model.Relation;
import com.graduate.server.model.Vertex;
import com.graduate.server.model.Visual;
import com.graduate.server.service.ExploreService;
@Service
public class ExploreServiceImp implements ExploreService{
	//推荐相似实体
		public List<Entity> getSimEntity(List<Entity>list){
			//System.out.println("vector size:"+DataLoad.EntityVector.size());
			List<Entity>simList=DataLoad.EntityVector.entrySet().parallelStream()//计算欧式距离
											.map(e->DataUtil.getScoreofEntity(e.getKey(),list))
											.collect(Collectors.toList());
			return CommonService.RankEntity(simList,DataLoad.Out_Entity_Size);
		}
		@Override
		public List<Entity> getSimEntity(List<Entity> query, List<Feature> feature) {
			// TODO Auto-generated method stub
			//for(Entity entity:query)System.out.println(entity.getId()+" "+entity.getName());
			List<Entity>simList=DataLoad.EntityVector.entrySet().parallelStream()//计算欧式距离
					.map(e->DataUtil.getScoreofEntity(e.getKey(),query,feature))
					.collect(Collectors.toList());
			return CommonService.RankEntity(simList,DataLoad.Out_Entity_Size);
		}
		public String changeString(String name){
			String[]categoryName = name.split(":");
			if(categoryName.length>1) {
				name=categoryName[1];
			}
			String ss[]=name.split(" ");
			name="";
			int sum=0;
			for(int j=0;j<ss.length;j++){
				if(j==0)name=name+ss[j];
				else name=name+" "+ss[j];
				sum+=ss[j].length();
				if(sum>7){
					name+="\n";
					sum=0;
				}
			}
			//System.out.println(name);
			return name;
		}
		@Override
		public List<Feature>getSimFeature(List<Entity>queryList){//计算相似实体的相似特征
			List<Feature>featureList=new ArrayList<Feature>();
			int count=0;
			for(Relation relation:CommonService.getRelationTop(queryList)){//获取相关的relation
				  if(DataLoad.RelationVector.containsKey(relation.getRelationId())){//获取关系向量
					  List<Entity>targetList=DataLoad.EntityVector.entrySet().parallelStream()
						.map(e->DataUtil.getScoreofFeature(e.getKey(),e.getValue(),relation,queryList))
						.filter(e->e!=null)
						.collect(Collectors.toList());//获取目标实体向量，计算欧氏距离
					  int id=DataUtil.getRelationId("subject");
					  CommonService.RankEntity(targetList,
					  relation.getRelationId()==id?4:DataLoad.Out_Feature_Size).stream()
					  .forEach(entity->{
						  int direction = DataUtil.getEntity(entity, relation);
						  relation.setDirection(direction);
						  featureList.add(new Feature(relation,entity,entity.getScore()));
					  });
				  }
			}
			return CommonService.RankFeature(featureList);
		}
		//for different path length we set the different step site for each node
		@Override
		public Visual getPath(String head, String tail){
			//System.out.println(head+tail);
			Entity headEntity=DataUtil.getEnityByName(head);
			Entity tailEntity=DataUtil.getEnityByName(tail);
			//System.out.println(headEntity.getId()+tailEntity.getId());
			List<Path>pathList=new ArrayList<Path>();
			int count=0;
			HashSet<Integer> mp=new HashSet<Integer>();
			getPathEntity(headEntity,tailEntity,new Path(headEntity),pathList,count,mp);
			int size=pathList.size();
			HashMap<String,Integer>map=new HashMap<>();
			int max=0;
			/*for(Path path:pathList){
				for(Entity entity:path.getEntityPath()){
					System.out.print(entity.getName()+" ");
				}
				System.out.println();
			}*/
			for(Path path:pathList){
				max=max<path.getEntityPath().size()?path.getEntityPath().size():max;
			}
			int iterator=0;
			List<Vertex>nodeList=new ArrayList<Vertex>();
			List<Edge>linkList=new ArrayList<Edge>();
			int y=0;
			int dy=100;
			int x=0;
			int dx=400;
			HashSet<Integer>setNode=new HashSet<Integer>();
			HashSet<String>setLink=new HashSet<String>();
			int max_x=0;
			int max_y=0;
			if(pathList.size()==1) {
				Path path=pathList.get(0);
				x=0;
				y=0;
				Stack<Entity>entityStack=path.getEntityPath();
				Stack<Relation>relationStack=path.getRelationPath();
				for(int i=0;i<entityStack.size();i++){
					Entity tnode=entityStack.get(i);
					if(i>0){
						Relation link=relationStack.get(i-1);
						Entity snode=entityStack.get(i-1);
						if(!setNode.contains(tnode.getId())&&tnode.getId()!=tailEntity.getId()){
							setNode.add(tnode.getId());
							int category=1;
							if(link.getName().equals("subject")&&tnode.getName().contains("category:")){
								category=0;
							}
							nodeList.add(new Vertex(tnode.getName(),x,y,category));
							if(link.getDirection()==0){
									linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
							}else{
									linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
							}
						}else{
							if(link.getDirection()==0){
								if(!setLink.contains(snode.getName()+tnode.getName())){
									linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
								}
							}else{
								if(!setLink.contains(tnode.getName()+snode.getName())){
									linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
									setLink.add(tnode.getName()+snode.getName());
								}
							}
						}
					}else{
						if(!setNode.contains(tnode.getId())){
							setNode.add(tnode.getId());
							nodeList.add(new Vertex(tnode.getName(),x,y,2));
						}
					}
					x+=dx;
					y+=dy;
				}
				max_x=max_x<x?x:max_x;
				max_y=max_y<y?y:max_y;
				max_x+=100;
				max_y+=100;
			}else if(pathList.size()==2) {
				dx=550;
				dy=120;
				for(Path path:pathList){
					x=0;
					y=850;
					Stack<Entity>entityStack=path.getEntityPath();
					Stack<Relation>relationStack=path.getRelationPath();
					for(int i=0;i<entityStack.size();i++){
						Entity tnode=entityStack.get(i);
						if(i>0){
							Relation link=relationStack.get(i-1);
							Entity snode=entityStack.get(i-1);
							if(!setNode.contains(tnode.getId())&&tnode.getId()!=tailEntity.getId()){
								setNode.add(tnode.getId());
								int category=1;
								if(link.getName().equals("subject")&&tnode.getName().contains("category:")){
									category=0;
								}
								nodeList.add(new Vertex(tnode.getName(),x,y,category));
								if(link.getDirection()==0){
										linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
										setLink.add(snode.getName()+tnode.getName());
								}else{
										linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
										setLink.add(snode.getName()+tnode.getName());
								}
							}else{
								if(link.getDirection()==0){
									if(!setLink.contains(snode.getName()+tnode.getName())){
										linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
										setLink.add(snode.getName()+tnode.getName());
									}
								}else{
									if(!setLink.contains(tnode.getName()+snode.getName())){
										linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
										setLink.add(tnode.getName()+snode.getName());
									}
								}
							}
						}else{
							if(!setNode.contains(tnode.getId())){
								setNode.add(tnode.getId());
								nodeList.add(new Vertex(tnode.getName(),x,y,2));
							}
						}
						x+=dx;
						y+=dy;
					}
					max_x=max_x<x?x:max_x;
					max_y=max_y<y?y:max_y;
					max_x-=200;
					max_y-=450;
					dx=500;
					dy=300;
				}
			}else {
				int round=0;
				dx=600;
				dy=180;
				int countPos=0;
				for(Path path:pathList){
					x=0;
					y=500;
					countPos=0;
					Stack<Entity>entityStack=path.getEntityPath();
					Stack<Relation>relationStack=path.getRelationPath();
					for(int i=0;i<entityStack.size();i++){
						Entity tnode=entityStack.get(i);
						if(i>0){
							Relation link=relationStack.get(i-1);
							Entity snode=entityStack.get(i-1);
							if(!setNode.contains(tnode.getId())&&tnode.getId()!=tailEntity.getId()){
								setNode.add(tnode.getId());
								int category=1;
								if(link.getName().equals("subject")&&tnode.getName().contains("category:")){
									category=0;
								}
								System.out.println("count"+countPos+" round"+round+" "+tnode.getName());
								if(countPos==0&&round==0) {
									y+=140;
									System.out.println("node name: "+tnode.getName());
								}else if(countPos==0&&round==2) {
									y+=250;
									System.out.println("node name "+tnode.getName());
								}
								nodeList.add(new Vertex(tnode.getName(),x,y,category));
								if(link.getDirection()==0){
										linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
										setLink.add(snode.getName()+tnode.getName());
								}else{
										linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
										setLink.add(snode.getName()+tnode.getName());
								}
								countPos++;
							}else{
								if(link.getDirection()==0){
									if(!setLink.contains(snode.getName()+tnode.getName())){
										linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
										setLink.add(snode.getName()+tnode.getName());
									}
								}else{
									if(!setLink.contains(tnode.getName()+snode.getName())){
										linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
										setLink.add(tnode.getName()+snode.getName());
									}
								}
							}
						}else{
							if(!setNode.contains(tnode.getId())){
								setNode.add(tnode.getId());
								nodeList.add(new Vertex(tnode.getName(),x,y,2));
							}
						}
						x+=dx;
						y+=dy;
					}
					max_x=max_x<x?x:max_x;
					max_y=max_y<y?y:max_y;
					/*System.out.println("max_x"+x);
					System.out.println("max_y"+y);*/
					max_x-=250;
					max_y-=450;
					round++;
					if(round%2==0) {
						System.out.println("round 1");
						dx=550;
						dy=350;
					}else if(round%2!=0) {
						System.out.println("round 2");
						dx=550;
						dy=50;
					}
				}
			}
			/*for(Path path:pathList){//
				x=0;
				Stack<Entity>entityStack=path.getEntityPath();
				Stack<Relation>relationStack=path.getRelationPath();
				for(int i=0;i<entityStack.size();i++){
					Entity tnode=entityStack.get(i);
					if(i>0){
						Relation link=relationStack.get(i-1);
						Entity snode=entityStack.get(i-1);
						if(!setNode.contains(tnode.getId())&&tnode.getId()!=tailEntity.getId()){
							setNode.add(tnode.getId());
							int category=1;
							if(link.getName().equals("subject")&&tnode.getName().contains("category:")){
								category=0;
							}
							nodeList.add(new Vertex(tnode.getName(),x,y,category));
							if(link.getDirection()==0){
									linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
							}else{
									linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
							}
						}else{
							if(link.getDirection()==0){
								if(!setLink.contains(snode.getName()+tnode.getName())){
									linkList.add(new Edge(snode.getName(),tnode.getName(),link.getName()));
									setLink.add(snode.getName()+tnode.getName());
								}
							}else{
								if(!setLink.contains(tnode.getName()+snode.getName())){
									linkList.add(new Edge(tnode.getName(),snode.getName(),link.getName()));
									setLink.add(tnode.getName()+snode.getName());
								}
							}
						}
					}else{
						if(!setNode.contains(tnode.getId())){
							setNode.add(tnode.getId());
							nodeList.add(new Vertex(tnode.getName(),x,y,2));
						}
					}
					x+=dx;
					y+=dy;
				}
				max_x=max_x<x?x:max_x;
				max_y=max_y<y?y:max_y;
				dx-=60;
				y+=dy;
				dy=-400;
				dx+=200;
				dy+=400;
			}*/
			nodeList.add(new Vertex(tail,max_x,max_y,2));
			//nodeList.add(new Node(tailEntity.getName(),x,300));
			Visual vis=new Visual();
			for(int i=0;i<nodeList.size();i++){
				String name=nodeList.get(i).getName();
				name=changeString(name);
				//System.out.println(name);
				nodeList.get(i).setName(name);
			}
			for(int i=0;i<linkList.size();i++){
				String name=linkList.get(i).getSource();
				name=changeString(name);
				linkList.get(i).setSource(name);
				name=linkList.get(i).getTarget();
				name=changeString(name);
				//System.out.println(name);
				linkList.get(i).setTarget(name);
			}
			vis.setEdgeList(linkList);
			vis.setVertexList(nodeList);
			return vis;
		}
		private boolean getPathEntity(Entity head,Entity tail,Path path,List<Path>pathList,int count,HashSet<Integer>mp){
			if(count>4) return false;
			if(pathList.size()>=3) return false;
			if(!mp.contains(head.getId())){
				mp.add(head.getId());
			}else return false;
			boolean flag=false;
			List<Entity>targetList=new ArrayList<Entity>();
			for(Relation relation:CommonService.getRelationTop(head)){
				targetList=CommonService.getTarget(head, relation,tail);
				int size=targetList.size();
				for(Entity entity:targetList){
					if(entity.getId()==tail.getId()){
						path.addEntity(entity);
						path.addRelation(relation);
						path.setScore(entity.getScore()/Math.log(size));
						pathList.add(new Path(path.getHead(),path.getRelationPath(),path.getEntityPath(),path.getScore()));
						path.popEntity();
						path.popRelation();
						return true;
					}else{
						path.addEntity(entity);
						path.addRelation(relation);
						getPathEntity(entity, tail, path, pathList, count+1,mp);
						path.popEntity();
						path.popRelation();
					}
				}
			}
			return flag;
		}
		@Override
		public Visual getAllPath(String head, List<Entity> tailList) {
			HashSet<Integer> targetMap=new HashSet<Integer>();
			Entity headEntity=DataUtil.getEnityByName(head);
			for(Entity target:tailList){
				targetMap.add(target.getId());
			}
			List<Path>pathList=new ArrayList<Path>();
			int count=0;
			HashSet<Integer> mp=new HashSet<Integer>();
			getAllPathEntity(headEntity,targetMap,new Path(headEntity),pathList,count,mp);
			/*System.out.println(pathList.size());
			for(Path path:pathList){
				for(Entity entity:path.getEntityPath()){
					System.out.print(entity.getName());
				}
				System.out.println();
			}*/
			int max=0;
			List<Node>ndlist=new ArrayList<Node>();
			List<Link>lklist=new ArrayList<Link>();
			HashSet<Integer>enSet=new HashSet<Integer>();
			HashSet<String>lkSet=new HashSet<String>();
			for(Path path:pathList){
				Stack<Entity>entityStack=path.getEntityPath();
				Stack<Relation>relationStack=path.getRelationPath();
				for(int i=0;i<entityStack.size();i++){
					if(i>0){
						Entity tnode=entityStack.get(i);
						Entity snode=entityStack.get(i-1);
						Relation relation=relationStack.get(i-1);
						if(!enSet.contains(tnode.getId())){
							//System.out.println(tnode.getId()+" "+tnode.getName());
							enSet.add(tnode.getId());
							int category=1;
							if(relation.getName().equals("subject")&&tnode.getName().contains("category:")){
								category=0;
							}
							if(targetMap.contains(tnode.getId())){
								category=3;
							}
							ndlist.add(new Node(tnode.getName(),category));
							if(relation.getDirection()==0){
								lkSet.add(snode.getName()+tnode.getName());
								lklist.add(new Link(snode.getName(),tnode.getName(),relation.getName()));
							}else{
								lkSet.add(tnode.getName()+snode.getName());
								lklist.add(new Link(tnode.getName(),snode.getName(),relation.getName()));
							}
							
						}else{
								if(relation.getDirection()==0){
									String tmp=snode.getName()+tnode.getName();
									if(!lkSet.contains(tmp)){
										lkSet.add(snode.getName()+tnode.getName());
										lklist.add(new Link(snode.getName(),tnode.getName(),relation.getName()));
									}
								}else{
									String tmp=tnode.getName()+snode.getName();
									if(!lkSet.contains(tmp)){
										lkSet.add(tnode.getName()+snode.getName());
										lklist.add(new Link(tnode.getName(),snode.getName(),relation.getName()));
									}
								}
						}
						
					}else{
						Entity node=entityStack.get(i);
						if(!enSet.contains(node.getId())){
							enSet.add(node.getId());
							ndlist.add(new Node(node.getName()));
						}
					}
				}
			}
			return new Visual(ndlist,lklist);
		}
		private boolean getAllPathEntity(Entity head,HashSet<Integer>tail,Path path,List<Path>pathList,int count,HashSet<Integer>mp){
			if(count>4) return false;
			if(pathList.size()>=9) return false;
			boolean flag=false;
			if(!mp.contains(head.getId())){
				mp.add(head.getId());
			}else return false;
			List<Entity>targetList=new ArrayList<Entity>();
			for(Relation relation:CommonService.getRelationTop(head)){
				targetList=CommonService.getTarget(head, relation);
				for(Entity entity:targetList){
					if(tail.contains(entity.getId())){
						//System.out.println("End Path:"+head.getName()+" "+relation.getName()+" "+entity.getName());
						path.addEntity(entity);
						path.addRelation(relation);
						pathList.add(new Path(path.getHead(),path.getRelationPath(),path.getEntityPath(),path.getScore()));
						path.popEntity();
						path.popRelation();
						return true;
					}else{
						//if(entity.getName().equals("category:1990s action films"))
						//System.out.println("start path:"+head.getName()+" "+relation.getName()+" "+entity.getName());
						path.addEntity(entity);
						path.addRelation(relation);
						getAllPathEntity(entity,tail, path, pathList, count+1,mp);
						path.popEntity();
						path.popRelation();
					}
				}
			}
			return flag;
		}
		
		
}
