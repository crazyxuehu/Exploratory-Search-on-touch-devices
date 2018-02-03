package com.example.gogoalfight.exploratory.service;

import android.content.Context;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Created by GoGoalFight on 2017/11/12.
 */

public class ExchangeDataForJs {
    private Context mycontext;
    private JSONArray data;
    private JSONArray links;
    public ExchangeDataForJs(Context mycontext,JSONObject data){
        this.mycontext=mycontext;
        try{
            this.data=(JSONArray) data.get("vertexList");
            this.links=(JSONArray) data.get("edgeList");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @JavascriptInterface
    public String getData(){
        //System.out.println(data)
        return this.data.toString();
    }
    @JavascriptInterface
    public String getLinks(){
        return this.links.toString();
    }
    public void setData(JSONArray data){
        this.data=data;
    }
    public  void setLinks(JSONArray links){this.links=links;}
    public void setContext(Context mycontext){
        this.mycontext=mycontext;
    }
    public Context getMycontext(){
        return mycontext;
    }
}
