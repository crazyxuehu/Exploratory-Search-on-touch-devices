package com.graduate.server.service;

import java.util.List;

import com.graduate.server.model.Entity;

public interface IndexService {
	public List getHistory();
	public List getSearchHistory(int num);
	public List<String>autoComplete(String query,int k);
	public List<String>saveEntity(List<String>list);
	public void saveImg();
	public void updateImg();
	public String getImg(String name);
	public String getAbstract(String query);
}
