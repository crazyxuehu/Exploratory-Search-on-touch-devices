package com.graduate.server.service.imp;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduate.server.common.DataUtil;
import com.graduate.server.common.IndexBuild;
import com.graduate.server.dao.SearchDao;
import com.graduate.server.model.Entity;
import com.graduate.server.service.IndexService;

@Service
public class IndexServiceImp implements IndexService{
	@Autowired SearchDao dao;
	
	public List getHistory(){
		return dao.getPopular();
	}
	
	@Override
	public List<String> autoComplete(String query,int k) {
		ArrayList<String> entityList = new ArrayList<>();
		QueryParser parser_name = new QueryParser("context", new StandardAnalyzer());	
		Query query_name;
		IndexSearcher searcher=new IndexSearcher(IndexBuild.getIndexReader());
		try {
			query_name = parser_name.parse(query);
			for (ScoreDoc scoreDoc : searcher.search(query_name, k).scoreDocs){
				Document document = searcher.doc(scoreDoc.doc);
				//System.out.println(document.get("name"));
				entityList.add(URLDecoder.decode(document.get("name"), "UTF-8"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
			
		if(entityList.size() < k) {
			QueryParser parser_context = new QueryParser("name", new StandardAnalyzer());
			Query query_context;
			try {
				query_context = parser_context.parse(getHistory().get(0).toString());
				for (ScoreDoc scoreDoc : searcher.search(query_context, k).scoreDocs){
					Document document = searcher.doc(scoreDoc.doc);
					entityList.add(URLDecoder.decode(document.get("name"), "UTF-8"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*if(entityList.size()<k){
			List<String> list=dao.getPopular();
			for(int i=0;i<list.size();i++){
				entityList.add(list.get(i).toString());
				if(entityList.size()>=k) break;
			}
		}*/
		return entityList;
	}

	@Override
	public List getSearchHistory(int num) {
		return dao.getSearchAll(num);
	}

	@Override
	public List<String> saveEntity(List<String> list) {
		dao.saveEntity(list);
		return null;
	}

	@Override
	public void saveImg() {
		/*List<String>list=dao.getImg();
		String file="F:/graduate/img.txt";
		DataUtil.writeToFile(list,file);*/
	}

	@Override
	public void updateImg() {
		String addr="F:/graduate/data/instance_processed/entity2abstract.txt";
		HashMap<String,String>mp=DataUtil.readImg(addr);
		HashMap<String,String>urlmp=new HashMap();
		/*for(String name:mp.keySet()){
			if(!urlmp.containsKey(name)){
				urlmp.put(name, mp.get(name));
			}
		}*/
		//System.out.println(urlmp.size());
		dao.updateImg(mp);
	}

	@Override
	public String getImg(String name) {
		return dao.geturl(name);
	}

	@Override
	public String getAbstract(String query) {
		return dao.getAbstract(query);
	}
	
}
