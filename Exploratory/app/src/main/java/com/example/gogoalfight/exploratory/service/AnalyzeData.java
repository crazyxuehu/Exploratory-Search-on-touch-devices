package com.example.gogoalfight.exploratory.service;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import com.example.gogoalfight.exploratory.MainFragement;
import com.example.gogoalfight.exploratory.SearchActivity;
import com.example.gogoalfight.exploratory.model.FeatureButtonModel;
import com.example.gogoalfight.exploratory.model.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class AnalyzeData {
    public static String[] getAutoComplete(JSONObject data){
        String[]list=null;
        try {
            JSONArray mydata = (JSONArray) data.get("result");
            list=new String[mydata.length()];
            for(int i=0;i<mydata.length();i++){
                list[i]=mydata.get(i).toString();
            }
           /* for(int i=0;i<list.length;i++){
                System.out.println(list[i]);
            }*/
        }catch (JSONException e){
            System.out.println(e);
            System.out.println("analyze autocomplete word error!");
        }
        return list;
    }
    public static void getSearchResult(JSONObject data, Context mycontext, FragmentManager fragmentManager, MainFragement mainFragement){
        GlobalData mydata=(GlobalData) mycontext.getApplicationContext();
        try {
            JSONArray simEntitylist = (JSONArray) data.get("simEntityList");
            for(int i=0;i<simEntitylist.length();i++){
                JSONObject myobject=(JSONObject)simEntitylist.get(i);
                mydata.addSimilarEntity(myobject.get("name").toString(),null);//put the similar Entity name to Global Data
                mydata.addSimilarEntityName(myobject.get("name").toString());
               createConnection.getImageUrl(myobject.getString("name").toString(),mycontext,fragmentManager,mainFragement);
            }
            System.out.println("simailar Entity:"+mydata.getSimilarEntity().size());
            mydata.setSimEntityCount(simEntitylist.length());
            //System.out.println("recommend data:"+data.get("recomendFeatureList"));
            JSONArray recommendList=(JSONArray)data.get("recomendFeatureList");
            int []type={2,1};
            for(int i=0;i<recommendList.length();i++){
                //FeatureModel myfeature=(FeatureModel)recommendList.get(i);
                JSONObject feature=(JSONObject)recommendList.get(i);
                JSONObject target=(JSONObject)feature.get("target");
                feature=(JSONObject)feature.get("relation");
                int featureType=type[Integer.parseInt(feature.get("direction").toString())];
                String label=feature.get("name").toString();
                String entity=target.get("name").toString();
                if(featureType==2){
                    String[]list=entity.split("\\:");
                    if(list.length>1){
                        entity=list[1];
                    }
                }
                mydata.addFeatureEntity(new FeatureButtonModel(featureType,label,entity));
            }
        }catch (JSONException e){
            System.out.println(e);
        }
        if(mydata.getCount()==mydata.getSimEntityCount()){
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            mainFragement.changeSearchResult();
            fragmentTransaction.show(mainFragement);
            fragmentTransaction.commit();
        }
    }

    public static void getSearchResult(JSONObject data, Context mycontext, SearchActivity searchActivity){
        GlobalData mydata=(GlobalData) mycontext.getApplicationContext();
        try {
            JSONArray simEntitylist = (JSONArray) data.get("simEntityList");
            for(int i=0;i<simEntitylist.length();i++){
                JSONObject myobject=(JSONObject)simEntitylist.get(i);
                //System.out.println("first name:"+myobject.get("name").toString());
                //mydata.addSimilarEntity(myobject.get("name").toString(),null);//put the similar Entity name to Global Data
                mydata.addSimilarEntityName(myobject.get("name").toString());
                //createConnection.getImageUrl(myobject.getString("name").toString(),mycontext);
                System.out.println("img:"+myobject.get("img").toString());
                mydata.addSimilarEntity(myobject.get("name").toString(),myobject.get("img").toString());
            }
            mydata.setSimEntityCount(simEntitylist.length());
            mydata.setCount(simEntitylist.length());
            //searchActivity.setCount(10);
            System.out.println("recommend data:"+data.get("recomendFeatureList"));
            JSONArray recommendList=(JSONArray)data.get("recomendFeatureList");
            int []type={2,1};
            for(int i=0;i<recommendList.length();i++){
                //FeatureModel myfeature=(FeatureModel)recommendList.get(i);
                JSONObject feature=(JSONObject)recommendList.get(i);
                JSONObject target=(JSONObject)feature.get("target");
                feature=(JSONObject)feature.get("relation");
                int featureType=type[Integer.parseInt(feature.get("direction").toString())];
                String label=feature.get("name").toString();
                String entity=target.get("name").toString();
                if(featureType==2){
                    String[]list=entity.split("\\:");
                    if(list.length>1){
                        entity=list[1];
                    }
                }
                mydata.addFeatureEntity(new FeatureButtonModel(featureType,label,entity));
            }
        }catch (JSONException e){
            System.out.println(e);
        }
        System.out.println("finish search data:"+String.valueOf(mydata.getSimEntityCount()));
    }
    public static void getSearchResult(JSONObject data, Context mycontext){
        GlobalData mydata=(GlobalData) mycontext.getApplicationContext();
        try {
            JSONArray simEntitylist = (JSONArray) data.get("simEntityList");
            for(int i=0;i<simEntitylist.length();i++){
                JSONObject myobject=(JSONObject)simEntitylist.get(i);
                //System.out.println("first name:"+myobject.get("name").toString());
                mydata.addSimilarEntity(myobject.get("name").toString(),null);//put the similar Entity name to Global Data
                mydata.addSimilarEntityName(myobject.get("name").toString());
                createConnection.getImageUrl(myobject.getString("name").toString(),mycontext);
                //System.out.println("img:"+myobject.get("img").toString());
                //mydata.addSimilarEntity(myobject.get("name").toString(),myobject.get("img").toString());
            }
            mydata.setSimEntityCount(simEntitylist.length());
            //mydata.setCount(simEntitylist.length());
            //searchActivity.setCount(10);
            System.out.println("recommend data:"+data.get("recomendFeatureList"));
            JSONArray recommendList=(JSONArray)data.get("recomendFeatureList");
            int []type={2,1};
            for(int i=0;i<recommendList.length();i++){
                //FeatureModel myfeature=(FeatureModel)recommendList.get(i);
                JSONObject feature=(JSONObject)recommendList.get(i);
                JSONObject target=(JSONObject)feature.get("target");
                feature=(JSONObject)feature.get("relation");
                int featureType=type[Integer.parseInt(feature.get("direction").toString())];
                String label=feature.get("name").toString();
                String entity=target.get("name").toString();
                if(featureType==2){
                    String[]list=entity.split("\\:");
                    if(list.length>1){
                        entity=list[1];
                    }
                }
                mydata.addFeatureEntity(new FeatureButtonModel(featureType,label,entity));
            }
        }catch (JSONException e){
            System.out.println(e);
        }
        System.out.println("finish search data:"+String.valueOf(mydata.getSimEntityCount()));
    }
    public static void getImageSrc(JSONObject data,GlobalData gdata,String entity){
        try {
           /* String imgSrc = data.get("Poster").toString();
            if(imgSrc.equals("N/A")){
                imgSrc=null;
            }*/
            String imgSrc=data.get("img").toString();
            //System.out.println("img:"+entity+" "+imgSrc);
            gdata.addSimilarEntity(entity,imgSrc);
        }catch (JSONException e){
            System.out.println();
        }
    }
    public static String getImageSrc(JSONObject data){
        String imgSrc=null;
        try{
            imgSrc=data.get("img").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        /*try {
            imgSrc = data.get("Poster").toString();
            if(imgSrc.equals("N/A")){
                imgSrc=null;
            }
        }catch (JSONException e){
            System.out.println();
        }*/
        return imgSrc;
    }
    public static String getDescption(JSONObject data){
        String desc=null;
        try {
            desc=data.get("description").toString();
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("analyze description error!");
        }
        return desc;
    }
    public static List<FeatureButtonModel> getSearchFeature(JSONObject data){
        List<FeatureButtonModel>list=new ArrayList<FeatureButtonModel>();
        try{
            JSONArray datalist=(JSONArray) data.get("queryFeatureList");
            for(int i=0;i<datalist.length();i++){
                JSONObject feature=(JSONObject) datalist.get(i);
               JSONObject labelObject=(JSONObject) feature.get("relation");
                JSONObject entityObject=(JSONObject) feature.get("target");
                String label=(String)labelObject.get("name");
                String entity=(String) entityObject.get("name");
                int type=1;
                if(label.equals("subject")) type=0;
                list.add(new FeatureButtonModel(type,label,entity));
            }
        }catch (Exception e) {
                e.printStackTrace();
                System.out.println("Analyze press response data wrong!");
        }
        return list;
    }
}
