package com.example.gogoalfight.exploratory.service;

import android.app.FragmentManager;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.gogoalfight.exploratory.FeedBack;
import com.example.gogoalfight.exploratory.MainFragement;
import com.example.gogoalfight.exploratory.QueryButton;
import com.example.gogoalfight.exploratory.SearchActivity;
import com.example.gogoalfight.exploratory.model.FeatureButtonModel;
import com.example.gogoalfight.exploratory.model.FeedBackModel;
import com.example.gogoalfight.exploratory.model.GlobalData;
import com.example.gogoalfight.exploratory.model.QueryModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class beginSearch {
    private String query;
    private GlobalData mydata;
    private Context mycontext;
    public beginSearch(String query,GlobalData mydata,Context mycontext){
        this.query=query;
        this.mydata=mydata;
        this.mycontext=mycontext;
    }
    public beginSearch(GlobalData mydata,Context mycontext){
        this.mydata=mydata;
        this.mycontext=mycontext;
    }
    public beginSearch(){}
    public void search(SearchActivity searchActivity){
        //System.out.println("query:"+query);
        String[]list=query.split(",\\s");
        mydata.setQueryList(Arrays.asList(list));
        String query="&query=";
        String relation="&relation=";
        String direction="&direction=";
        String search="getIndex?";
        for(String entity:list){
            System.out.println("entity:"+entity);
            if(entity.contains("#")){
                String []feature=entity.split("##");
                query+=feature[1]+",";
                relation+=feature[0]+",";
                direction+="0,";
            }else{
               query+=entity+",";
                relation+=" ,";
                direction+="0,";
            }
        }
        System.out.println(search+query+relation+direction);
        createConnection.getSearchResult(search+query+relation+direction,mycontext,searchActivity);
    }
    public void search(List<FeedBackModel> queryList, List<FeedBackModel>feedbackList, FragmentManager fragmentManager, MainFragement mainFragement){
        System.out.println("begin search");
        List<String>ll=new ArrayList<String>();
        List<String>nll=new ArrayList<String>();
        String psearch="getFeedback?";
        String nsearch="";
        for(int i=0;i<queryList.size();i++){
            if(queryList.get(i)!=null){
                FeedBackModel query=queryList.get(i);
               psearch+="&query="+query.getFeature().getEntity();
            }
            if(feedbackList.get(i)!=null){
                FeedBackModel query=feedbackList.get(i);
                //nll.add(query.getFeature().getEntity());
                if(query.getFeature()!=null) {
                    nsearch += "&nquery=" + query.getFeature().getEntity();
                }
            }
        }
        createConnection.getRelavenceFeedback(psearch+nsearch,mycontext,fragmentManager,mainFragement);
    }
    public void search(ScrollView myscrollView,ScrollView negscrollView,MainFragement mainFragement){
       mainFragement.updateQueryNum();
       int queryNum=mainFragement.getQueryNum();
       int count=myscrollView.getChildCount();
       List<QueryModel>querylist=new ArrayList<QueryModel>();
       double sum=0;
       int weight=0;
       List<FeedBackModel>positiveQuery=new ArrayList<FeedBackModel>();
       if(count==1){
           LinearLayout layout=(LinearLayout) myscrollView.getChildAt(0);
           //System.out.println("childcount:"+String.valueOf(layout.getChildCount()));
           for(int i=0;i<layout.getChildCount();i++){
               try {
                   QueryButton queryButton=(QueryButton) layout.getChildAt(i);
                   weight=queryButton.getWeight();
                   String text=queryButton.getText();
                   String []list=text.split(",\\s");
                   for(int j=0;j<list.length;j++){
                       querylist.add(new QueryModel((list[j]),weight," ",-1));
                       positiveQuery.add(new FeedBackModel(new FeatureButtonModel(1,null,list[j]),weight));
                       sum+=weight;
                   }
                   //querylist.add(new QueryModel(queryButton.getText(),weight));
               }catch (Exception e){
                   FeedBack feedBack=(FeedBack) layout.getChildAt(i);
                   weight=feedBack.getWeight();
                   String label = feedBack.getLabel();
                   String text = feedBack.getText();
                   if(label.equals("subject")){
                       text="category:"+text;
                   }
                   positiveQuery.add(new FeedBackModel(new FeatureButtonModel(feedBack.getDirection(),label,feedBack.getText()),weight));
                   //System.out.println("weight:"+String.valueOf(weight));
                   sum+=weight;
                   querylist.add(new QueryModel(text,weight,feedBack.getLabel(),feedBack.getDirection()));
               }
           }
       }
       mainFragement.setQueryList(queryNum,positiveQuery);
       List<FeedBackModel>negetiveQuery=new ArrayList<FeedBackModel>();
       count=negscrollView.getChildCount();
       if(count==1){
           LinearLayout layout=(LinearLayout) negscrollView.getChildAt(0);
           for(int i=0;i<layout.getChildCount();i++){
               try {
                   QueryButton queryButton=(QueryButton) layout.getChildAt(i);
                   weight=queryButton.getWeight();
                   String text=queryButton.getText();
                   String []list=text.split(",\\s");
                   for(int j=0;j<list.length;j++){
                       querylist.add(new QueryModel((list[j]),-1*weight," ",-1));
                       negetiveQuery.add(new FeedBackModel(new FeatureButtonModel(1,null,list[j]),weight));
                       sum+=weight;
                   }
               }catch (Exception e){
                   FeedBack feedBack=(FeedBack) layout.getChildAt(i);
                   weight=feedBack.getWeight();
                   String label = feedBack.getLabel();
                   String text = feedBack.getText();
                   if(label.equals("subject")){
                       text="category:"+text;
                   }
                   negetiveQuery.add(new FeedBackModel(new FeatureButtonModel(feedBack.getDirection(),label,feedBack.getText()),weight));
                   sum+=weight;
                   querylist.add(new QueryModel(text,-1*weight,feedBack.getLabel(),feedBack.getDirection()));
               }
           }
       }
       mainFragement.setFeedbackList(queryNum,negetiveQuery);
        //System.out.println("positive size:"+String.valueOf(positiveQuery.size()));
       String query="getWeightIndex?query=";
       String score="&weight=";
       String relation="&relation=";
       String direction="&direction=";
       for(int i=0;i<querylist.size();i++){
           QueryModel queryModel=querylist.get(i);
           query+=queryModel.getQuery()+",";
           score+=String.valueOf(queryModel.getWeight()/sum)+",";
           relation+=queryModel.getRelation()+",";
           direction+=String.valueOf(queryModel.getDirection())+",";
       }
       //System.out.println("query url:"+query+score+relation+direction);
       mainFragement.dealScrollQuery(query+score+relation+direction,mainFragement.getContext());
    }
}
