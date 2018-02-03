package com.example.gogoalfight.exploratory.service;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Response;
import com.example.gogoalfight.exploratory.InfoFragment;
import com.example.gogoalfight.exploratory.MainFragement;
import com.example.gogoalfight.exploratory.SearchActivity;
import com.example.gogoalfight.exploratory.model.FeatureButtonModel;
import com.example.gogoalfight.exploratory.model.GlobalData;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class ResponseListener implements Response.Listener<JSONObject> {
    private final int AutoComplete=1;
    private int type;
    private AutoCompleteTextView myView;
    private Context mycontext;
    private String Entity;
    private GlobalData globalData;
    private InfoFragment myFragment;
    private MainFragement mainFragement;
    private FragmentManager fragmentManager;
    private SearchActivity searchActivity;
    private Bundle bundle;
    private String film;
    public ResponseListener(AutoCompleteTextView myView,Context mycontext,int type){
        this.myView=myView;
        this.mycontext=mycontext;
        this.type=type;
    }
    public ResponseListener(String Entity,int type,Context mycontext,MainFragement mainFragement){
        this.Entity=Entity;
        this.type=type;
        this.mycontext=mycontext;
        this.mainFragement=mainFragement;
        //this.bundle=bundle;
    }
    public ResponseListener(MainFragement fragement,int type){
        this.mainFragement=fragement;
        this.type=type;
    }
    public ResponseListener(String Entity,int type,Context mycontext){
        this.Entity=Entity;
        this.type=type;
        this.mycontext=mycontext;
    }
    public ResponseListener(String Entity,int type,Context mycontext,FragmentManager fragmentManager,MainFragement mainFragement,String film){
        this.Entity=Entity;
        this.type=type;
        this.mycontext=mycontext;
        this.mainFragement=mainFragement;
        this.fragmentManager=fragmentManager;
        this.film=film;
    }
    public ResponseListener(String Entity,int type,Context mycontext,FragmentManager fragmentManager,MainFragement mainFragement){
        this.Entity=Entity;
        this.type=type;
        this.mycontext=mycontext;
        this.mainFragement=mainFragement;
        this.fragmentManager=fragmentManager;
    }
    public ResponseListener(Context mycontext,int type){
        this.type=type;
        this.mycontext=mycontext;
    }
    public ResponseListener(Context mycontext, int type, SearchActivity searchActivity){
        this.type=type;
        this.mycontext=mycontext;
        this.searchActivity=searchActivity;
    }
    public ResponseListener(Context mycontext, int type, FragmentManager fragmentManager,MainFragement mainFragement){
        this.type=type;
        this.mycontext=mycontext;
        this.fragmentManager=fragmentManager;
        this.mainFragement=mainFragement;
    }
    public ResponseListener(MainFragement mainFragement, Context mycontext, int type){
        this.myFragment=myFragment;
        this.mycontext=mycontext;
        this.type=type;
        this.mainFragement=mainFragement;
        //this.bundle=bundle;
    }
    @Override
    public void onResponse(JSONObject response) {
       if(type==1){
             String []list=AnalyzeData.getAutoComplete(response);
                   ArrayAdapter aAdapter = new ArrayAdapter<String>(
                           mycontext.getApplicationContext(),
                           android.R.layout.simple_dropdown_item_1line,
                           list);
                   myView.setAdapter(aAdapter);
                   aAdapter.notifyDataSetChanged();
       }else if(type==2){
           //GlobalData data=(GlobalData) mycontext.getApplicationContext();
           System.out.println("response set");
           //searchActivity.setCount(10);

           if(searchActivity!=null) {
               AnalyzeData.getSearchResult(response,mycontext,searchActivity);
               searchActivity.dealsearch();
           }else{
               AnalyzeData.getSearchResult(response,mycontext);
           }
       }else if(type==3){
           GlobalData data=(GlobalData) mycontext.getApplicationContext();
           AnalyzeData.getImageSrc(response,data,Entity);
           data.addCount();
           //System.out.println(data.getSimEntityCount());
       }else if(type==4){
           String desc=AnalyzeData.getDescption(response);
           //System.out.println(desc);
           List<FeatureButtonModel>feature=AnalyzeData.getSearchFeature(response);
          List<FeatureButtonModel>pressFeature=mainFragement.getPressFeature();
           for(int i=0;i<feature.size();i++){
               FeatureButtonModel myFeature=feature.get(i);
               pressFeature.add(myFeature);
           }
           mainFragement.setPressFeature(pressFeature);
           mainFragement.setDescription(desc);
           GlobalData mydata=(GlobalData) mainFragement.getActivity().getApplicationContext();
           mydata.setResultFlag(1);
           System.out.println("finish entity info");
       }else if(type==5){
          // bundle.putString("url",AnalyzeData.getImageSrc(response));
           //myFragment.setArguments(bundle);
           String img=AnalyzeData.getImageSrc(response);
           //if(img==null){
             //  createConnection.getLocalImage(film,mycontext);
           //}
           mainFragement.setImageUrl(img);
           GlobalData mydata=(GlobalData) mainFragement.getActivity().getApplicationContext();
           mydata.setImageFlag(1);
           //System.out.println(AnalyzeData.getImageSrc(response));
       }else if(type==6){
           AnalyzeData.getSearchResult(response,mycontext,fragmentManager,mainFragement);

       }else if(type==7){
           GlobalData data=(GlobalData) mycontext.getApplicationContext();
           AnalyzeData.getImageSrc(response,data,Entity);
           data.addCount();
           if(data.getCount()==data.getSimEntityCount()){
               FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();;
               mainFragement.changeSearchResult();
               fragmentTransaction.show(mainFragement);
               fragmentTransaction.commit();
           }
       }else if(type==8){
            mainFragement.setPath(response);
       }
    }

}

