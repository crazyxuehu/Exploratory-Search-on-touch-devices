package com.graduate.server.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServlet;

public class DataLoad extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String path="F:\\graduate\\data\\instance_processed\\";
	public static int D_Entity=100;
	public static int D_Relation=100;
	public static int Relation_type=0;
	public static int Entity_type=1;
	public static int Error_type=-1;
	public static int Out_Feature_Size=2;
	public static int Out_Entity_Size=6;
	public static int Out_Path_Size=30;
	public static HashMap<String,Integer>InputType=new HashMap<String,Integer>();
	//数据集索引
	public static HashMap<Integer, HashMap<Integer,HashMap<Integer, HashSet<Integer>>>> tripleHash = new HashMap<>();//原始数据集
	public static HashMap<String, Integer> EntityId = new HashMap<>();
	public static HashMap<Integer, String> IdEntity = new HashMap<>();//双向索引
	public static HashMap<String, Integer> RelationId = new HashMap<>();
	public static HashMap<Integer, String> IdRelation = new HashMap<>();
	//向量集索引
	public static HashMap<Integer, double[]> EntityVector = new HashMap<>();//transE训练集
	public static HashMap<Integer, double[]> RelationVector = new HashMap<>();	
	public DataLoad(){
		createEntityIndex();
		createRelationIndex();
		createTripleIndex();
		createEntityVectorIndex();
		createRelationVectorIndex();
		//new IndexBuild();
	}
	public void createEntityIndex(){
		String data="entity2id.txt";
		BufferedReader reader;
		try {//读取文件
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path+data)), "UTF-8"));
			String tmpString = null;
			while((tmpString = reader.readLine()) != null) {
				String []ss=tmpString.split("\t");
				String name = URLDecoder.decode(ss[0].replaceAll("_", " "), "UTF-8");
				EntityId.put(name,Integer.parseInt(ss[1]));
				IdEntity.put(Integer.parseInt(ss[1]),name);
			}
			reader.close();	
		} catch (IOException e) {
			System.out.println("load Entity Error!");
			e.printStackTrace();
		}
	}
	public void createRelationIndex(){
		String data="relation2id.txt";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path+data)), "UTF-8"));
			String tmpString = null;
			while((tmpString = reader.readLine()) != null) {
				String []ss=tmpString.split("\t");
				RelationId.put(ss[0],Integer.parseInt(ss[1]));
				IdRelation.put(Integer.parseInt(ss[1]),ss[0]);
			}
			reader.close();	
		} catch (IOException e) {
			System.out.println("load relation Error!");
			e.printStackTrace();
		}
	}
	public void createTripleIndex(){
		String data="train.txt";
		tripleHash.put(0, new HashMap());
		tripleHash.put(1, new HashMap());
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path+data)), "UTF-8"));
			String tmpString = null;
			while((tmpString = reader.readLine()) != null) {
				String []ss=tmpString.split("\t");            	
            	String subject = URLDecoder.decode(ss[0], "UTF-8").replaceAll("_", " ");
            	String predicate = URLDecoder.decode(ss[2], "UTF-8");
            	String object = URLDecoder.decode(ss[1], "UTF-8").replaceAll("_", " ");
            	
            	if(RelationId.containsKey(predicate)){
            		int relationId = RelationId.get(predicate);
            		if(EntityId.containsKey(subject) && EntityId.containsKey(object)){
                		int subjectId = EntityId.get(subject);
                		int objectId = EntityId.get(object);
                		
                		//Put out direction
                		HashMap<Integer, HashMap<Integer,HashSet<Integer>>> tripleHash_out = tripleHash.get(0);
                		if(!tripleHash_out.containsKey(subjectId)){
                			tripleHash_out.put(subjectId , new HashMap());
                			tripleHash_out.get(subjectId).put(relationId, new HashSet());
                		}
                		else if(!tripleHash_out.get(subjectId).containsKey(relationId))
                			tripleHash_out.get(subjectId).put(relationId, new HashSet());

                		tripleHash_out.get(subjectId).get(relationId).add(objectId);
                		
                		HashMap<Integer,HashMap<Integer, HashSet<Integer>>> tripleHash_in = tripleHash.get(1);               		
                		if(!tripleHash_in.containsKey(objectId)){
                			tripleHash_in.put(objectId , new HashMap());
                			tripleHash_in.get(objectId).put(relationId, new HashSet());
                		}
                		else if(!tripleHash_in.get(objectId).containsKey(relationId))
                			tripleHash_in.get(objectId).put(relationId, new HashSet());

                		tripleHash_in.get(objectId).get(relationId).add(subjectId);
            		}
                	else 
                		System.out.println("Loading triples! Wrong entity dictionary\t" + subject + "\t" + object);	             
            	}else{
            		System.out.println("Loading triples! wrong entity dictionary\t"+predicate);
            	}
			}
			reader.close();	
		} catch (IOException e) {
			System.out.println("load Triple Error!");
			e.printStackTrace();
		}
	}
	public void createEntityVectorIndex(){//创新实体向量集索引
		String data="TransE/entity2vec.bern";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path+data)), "UTF-8"));
			String tmpString = null;
			int count=0;
			while((tmpString = reader.readLine()) != null) {
				if(IdEntity.containsKey(count)){
					EntityVector.put(count, new double[D_Entity]);
					String []ss=tmpString.split("\t");
					for(int i=0;i<ss.length;i++){
						EntityVector.get(count)[i]=Double.parseDouble(ss[i]);
					}
				}else{
					System.out.println("load vector! wrong Entity vector");
				}
				count++;
			}
			reader.close();	
		} catch (IOException e) {
			System.out.println("load Entity vector Error!");
			e.printStackTrace();
		}
	}
	public void createRelationVectorIndex(){
		String data="TransE/relation2vec.bern";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path+data)), "UTF-8"));
			String tmpString = null;
			int count=0;
			while((tmpString = reader.readLine()) != null) {
				if(IdRelation.containsKey(count)){
					RelationVector.put(count, new double[D_Relation]);
					String []ss=tmpString.split("\t");
					for(int i=0;i<ss.length;i++){
						RelationVector.get(count)[i]=Double.parseDouble(ss[i]);
					}
				}else{
					System.out.println("load vector! wrong Relation vector");
				}
				count++;
			}
			reader.close();	
		} catch (IOException e) {
			System.out.println("load Relation vector Error!");
			e.printStackTrace();
		}
	}
}
