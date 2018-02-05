package com.example.gogoalfight.exploratory.service;

import android.app.FragmentManager;
import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.gogoalfight.exploratory.InfoFragment;
import com.example.gogoalfight.exploratory.MainFragement;
import com.example.gogoalfight.exploratory.SearchActivity;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class createConnection {
    //private static String baseUrl = "http://10.77.50.28:8080/Knowledge_Explore/";



    private static String baseUrl="http://10.77.40.30:8080/Knowledge_Explore/";
    //private static String baseUrl="http://139.199.25.205:8080/Knowledge_Explore/";
    private static String imageUrl="http://www.omdbapi.com/?&apikey=707b812e&t=";
    public static void getImageUrl(String url, Context ui,FragmentManager fragmentManager,MainFragement mainFragement){
        String entity=url;
        //System.out.println("image url:"+entity);
        url=baseUrl+"getImg?name="+url;
        JsonObjectRequest jsObjRequest=new JsonObjectRequest
                    (Request.Method.GET, url, null, new ResponseListener(entity, 7, ui, fragmentManager, mainFragement,entity), new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //System.out.println(error);
                            //System.out.println("get image error");
                        }
                    });

        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void getImageUrl(String url, Context ui){
        String entity=url;
       // url=url.split("\\(")[0];
        //System.out.println("image url:"+entity);
        url=baseUrl+"getImg?name="+url;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(entity,3,ui),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("get image error");
                    }
                });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void getEntityImageUrl(String url, Context ui,MainFragement mainFragement){
        String entity=url;
        //url=url.split("\\(")[0];
        //System.out.println("image url:"+entity);
        url=baseUrl+"getImg?name="+url;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(entity,5,ui,mainFragement),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("get image error");
                    }
                });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void getAutoCompleteWord(String url, Context ui, AutoCompleteTextView myview){
        url=baseUrl+url;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(myview,ui,1),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("get word error");
                    }
                });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void getSearchResult(String url,Context ui){
        url=baseUrl+url;
        System.out.println(url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(ui,2),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //System.out.println(error);
                        //System.out.println("get word error");
                    }
                });
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                 }
            });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void getSearchResult(String url, Context ui, SearchActivity searchActivity){
        url=baseUrl+url;
        System.out.println(url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(ui,2,searchActivity),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("get response error");
                    }
                });
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void getRelavenceFeedback(String url, Context ui, FragmentManager fragmentManager,MainFragement mainFragement){
        url=baseUrl+url;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(ui,6,fragmentManager,mainFragement),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("get word error");
                    }
                });
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void loadImage(String url, Context ui, ImageView myImage){
        Glide.with(ui).load(url).into(myImage);
    }
    public static void getEntityInfo(String url, Context ui, InfoFragment infoFragment, MainFragement mainFragement){
        url=baseUrl+"getSearchResult?query="+url;
        System.out.println(url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(mainFragement,ui,4),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
System.out.println(error);
                        System.out.println("get word error");
                    }
                });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }
    public static void getPath(String head,String tail, Context ui,MainFragement mainFragement){
        String url=baseUrl+"getExPath?head="+head+"&tail="+tail;
        System.out.println(url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(mainFragement,8),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("get path error");
                    }
                });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }/*
    public static void getLocalImage(String url, Context ui){
        String entity=url;
        url=baseUrl+"getImg?name="+url;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,new ResponseListener(entity,9,ui),new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("get image error");
                    }
                });
        InternetConnect.getInstance(ui).addToRequestQueue(jsObjRequest);
    }*/
}
