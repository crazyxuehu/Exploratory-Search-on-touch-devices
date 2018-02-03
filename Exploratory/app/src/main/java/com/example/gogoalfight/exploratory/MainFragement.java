package com.example.gogoalfight.exploratory;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.gogoalfight.exploratory.SVG.SvgDecoder;
import com.example.gogoalfight.exploratory.SVG.SvgDrawableTranscoder;
import com.example.gogoalfight.exploratory.SVG.SvgSoftwareLayerSetter;
import com.example.gogoalfight.exploratory.model.FeatureButtonModel;
import com.example.gogoalfight.exploratory.model.FeedBackModel;
import com.example.gogoalfight.exploratory.model.GlobalData;
import com.example.gogoalfight.exploratory.model.Result;
import com.example.gogoalfight.exploratory.service.createConnection;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import static com.example.gogoalfight.exploratory.R.id.feadback;

/**
 * Created by GoGoalFight on 2017/10/21.
 */

public class MainFragement extends Fragment  {
    private  Context mycontext;
    private  int QueryNum;
    private  HashMap<Integer,List<FeedBackModel>>QueryList;
    private  HashMap<Integer,List<FeedBackModel>>FeedbackList;
    private  List<FeatureButtonModel>FeatureList;
    private String imageUrl;
    private List<FeatureButtonModel>pressFeature;
    private String description;
    private HashMap<Integer,Result>historyList;
    public Stack<String> touchList;
    private JSONObject path;
    private Set<String>querySet;
    private int touchHPointers=0;
    private int touchVPointers=0;
    public boolean touchFlag=false;
    //private RequestBuilder<PictureDrawable> requestBuilder;
    public GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this.getContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
        return inflater.inflate(R.layout.mainfragment,container,false);
    }
    public int getQueryNum(){
        return QueryNum;
    }
    public void updateQueryNum(){
        QueryNum++;
    }
    public void addQuerySet(String text){
        querySet.add(text);
    }
    public List<FeedBackModel>getQueryList(int queryNum){
        return QueryList.get(queryNum);
    }
    public void setQueryList(int queryNum,List<FeedBackModel>list){
        this.QueryList.put(queryNum,list);
    }
    public void setFeedbackList(int queryNum,List<FeedBackModel>list){
        this.FeedbackList.put(queryNum,list);
    }
    public List<FeedBackModel>getFeedbackList(int queryNum){
        return FeedbackList.get(queryNum);
    }
    public HashMap<Integer, Result> getHistoryList(){
        return historyList;
    }
    public void setHistoryList(HashMap<Integer,Result>mp){
        this.historyList=mp;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPressFeature(List<FeatureButtonModel> pressFeature) {
        this.pressFeature = pressFeature;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public List<FeatureButtonModel> getPressFeature() {
        return pressFeature;
    }

    public String getDescription() {
        return description;
    }
    public void setTouchHPointers(int pointers){
        this.touchHPointers=pointers;
    }
    public int getTouchHPointers(){
        return this.touchHPointers;
    }
    public void setTouchVPointers(int pointers){
        this.touchVPointers=pointers;
    }
    public int getTouchVPointers(){
        return this.touchVPointers;
    }
    public void initial(Context ui, int num){
        mycontext=ui;
        QueryNum=num;
        QueryList=new HashMap<Integer, List<FeedBackModel>>();
        FeedbackList=new HashMap<Integer, List<FeedBackModel>>();
        FeatureList=new ArrayList<FeatureButtonModel>();
        pressFeature=new ArrayList<FeatureButtonModel>();
        touchList=new Stack<String>();
        querySet=new HashSet<String>();
       // pressFeature=new
        //System.out.println(QueryList.size());
    }
    public Stack<String> getTouchList() {
        return touchList;
    }
    public void setPath(JSONObject path){
        this.path=path;
    }
    public JSONObject getPath(){
        return this.path;
    }
    public void clearPath(){
        this.path=null;
    }
    public void addQuery(String data,String label){
        if(data==null&&label==null){
            //QueryList.add(null);
        }else {
            FeatureButtonModel feature = new FeatureButtonModel();
            feature.setType(1);
            feature.setLabel(label);
            feature.setEntity(data);
            FeedBackModel Querybtn = new FeedBackModel(feature);
            //System.out.println("queryEntity:"+Querybtn.getFeature().getEntity());
            //QueryList.add(Querybtn);
        }
    }
    public void addFeature(String name,String label,int type){
        FeatureButtonModel feature=new FeatureButtonModel();
        feature.setEntity(name);
        feature.setLabel(label);
        feature.setType(type);
        //FeatureList.add(feature);
        addFeedBack(feature,1);
    }
    public void addFeedBack(FeatureButtonModel feature,int weight){
        //System.out.print("add feedback now");
        FeedBackModel feedback = new FeedBackModel();
        feedback.setFeature(feature);
        feedback.setWeight(weight);
        //FeedbackList.add(feedback);
    }

    public void DealTouchAction(View view,String label,int action){
        Button btn=null;
        String text=null;
        if(action!=5&&action!=6){
            btn=(Button)view;
            text=(String)btn.getText();
        }
        //System.out.println(String.valueOf(action)+text);
        MainActivity mainActivity=(MainActivity)getActivity();
        switch (action) {
            case 1://swipe left
                btn = (Button) view;
                text = (String) btn.getText();
                /*if (label.equals("subject")) {
                    text = "category:" + text;
                }*/
                if (querySet.contains(text)) {
                    return;
                }else{
                    querySet.add(text);
                }
                addQuery(text, label);
                addFeedBack(null, 50);
                //System.out.println("swipe left now");
                createQueryComponent(mycontext, text, label);
               // mainActivity.updateSpinner(QueryNum);
                /*GlobalData mydata=(GlobalData) mycontext.getApplicationContext();
                mydata.initail();
                FragmentManager fragmentManger=getFragmentManager();
                new beginSearch(mydata,mycontext).search(QueryList,FeedbackList,fragmentManger,this);*/
                break;
            case 2://siwpe right
                btn = (Button) view;
                text = (String) btn.getText();
                /*if (label.equals("subject")) {
                    text = "category:" + text;
                }*/
                if(querySet.contains(text)){
                    return;
                }else{
                    querySet.add(text);
                }
               // addFeature(text, "Relation", 1);
                //addQuery(null, null);
                //createQueryComponent(mycontext,QueryNum);
                createFeedBackComponent(mycontext, QueryNum, label, text);
                //mainActivity.updateSpinner(QueryNum);
               /* mydata=(GlobalData) mycontext.getApplicationContext();
                mydata.initail();
                fragmentManger=getFragmentManager();
                new beginSearch(mydata,mycontext).search(QueryList,FeedbackList,fragmentManger,this);*/
                break;
            case 3:
            case 4:
                if(label!=null){
                    btn = (Button) view;
                    text = (String) btn.getText();
                    /*if (label.equals("subject")) {
                        text = "category:" + text;
                    }*/
                    if(querySet.contains(text))
                    querySet.remove(text);
                    View parent =(View) view.getParent();
                    View grandParent=(View) parent.getParent();
                    ViewGroup ggrandParent=(ViewGroup)grandParent.getParent();
                    ggrandParent.removeView(grandParent);
                    //System.out.println("yes");
                    break;
                }
               else{
                    btn = (Button) view;
                    text = (String) btn.getText();
                    if(querySet.contains(text))
                        querySet.remove(text);
                    View parent =(View) view.getParent();
                    ViewGroup grandParent=(ViewGroup) parent.getParent();
                    grandParent.removeView(parent);
                    break;
                }
            case 5://add entity to the positive query
                if(querySet.contains(label)){
                    return;
                }else{
                    querySet.add(label);
                }
                createQueryComponent(mycontext,label);
                break;
            case 6://add entity to the negative query
                if(querySet.contains(label)){
                    return;
                }else{
                    querySet.add(label);
                }
                createFeedBackComponent(mycontext,label);
        }
    }
    public void dealScollup(){
        MainActivity mainActivity=(MainActivity) this.getActivity();
        mainActivity.setCurrentSpinner(this,QueryNum);
        FragmentManager fragmentManger=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
        HistoryFragment subFragment = (HistoryFragment) getFragmentManager().findFragmentById(R.id.queryfragment);
        this.changeData();
        fragmentTransaction.hide(subFragment);
        fragmentTransaction.commit();
    }
    public void dealRelationExplore(String head,String tail){
            final  MainFragement mainFragement=this;
            final VisualPathFragment vispath=VisualPathFragment.newInstance();
            createConnection.getPath(head,tail,this.getContext(),this);
                    final ProgressDialog dialog=ProgressDialog.show(this.getContext(),"","Loading Data...",true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (true){
                                    if(mainFragement.path!=null){
                                        vispath.show(getFragmentManager(),"dialog");
                                        dialog.dismiss();
                                        break;
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
    }
    public void dealLongPressAction(String entity,int type,String url,Context ui){
        if(type==1){
            final InfoFragment dfragment = InfoFragment.newInstance();
            //bundle.putString("url",url);
           // bundle.putString("description","123");
            //dfragment.setArguments(bundle);
            final GlobalData mydata=(GlobalData) getActivity().getApplication();
            mydata.initialPress();
            pressFeature.clear();
            if(url==null){
                createConnection.getEntityImageUrl(entity,ui,this);
            }else{
                this.imageUrl=url;
                mydata.setImageFlag(1);
                //dfragment.setArguments(bundle);
            }
            //System.out.println("create Entity info");
            createConnection.getEntityInfo(entity,ui,dfragment,this);
            //System.out.println("deal with long press");
            final ProgressDialog dialog=ProgressDialog.show(this.getContext(),"","Loading Data...",true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            if(mydata.getResultFlag()==1&&mydata.getImageFlag()==1) {
                                dfragment.show(getFragmentManager(), "dialog");
                                dialog.dismiss();
                                break;
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
    public void dealScrollQuery(String url,Context ui){
        final GlobalData mydata=(GlobalData) getActivity().getApplication();
        mydata.initail();
        createConnection.getSearchResult(url,ui);
        final Handler myHandler=new Handler();
        final FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        final MainFragement mainFragement=this;
        final int queryNum=QueryNum;
        final Runnable updateUI=new Runnable() {
            @Override
            public void run() {
                MainActivity mainActivity=(MainActivity) mainFragement.getActivity();
                mainActivity.updateSpinner(queryNum);
                mainFragement.changeData();
                fragmentTransaction.show(mainFragement);
                fragmentTransaction.commit();
            }
        };
        final ProgressDialog dialog=ProgressDialog.show(ui,"","Loading Data...",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        if(mydata.getCount()!=0&&mydata.getCount()==mydata.getSimEntityCount()) {
                            myHandler.post(updateUI);
                            dialog.dismiss();
                            break;
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void changeData(Context ui,int QueryNum){
            removeChildView();
            createQueryComponent(ui,"Tom Hanks",QueryNum);
            createFeedBackComponent(ui,QueryNum,"Actor","Tom Hanks");
            createSearchResult(QueryNum,ui);
    }
    public void changeData(int type){
        removeChildView();
        MainActivity mainActivity=(MainActivity) getActivity();
        Context ui=mainActivity.getContext();
        createQueryComponent(ui,QueryNum);
        //createFeedBackComponent(ui,QueryNum,"Actor","Tom Hanks");
        createFeedBackComponent(ui,QueryNum);
        createSearchResult(QueryNum,ui,100);
    }
    public void changeData(){
        removeChildView();
        MainActivity mainActivity=(MainActivity) getActivity();
        Context ui=mainActivity.getContext();
        createQueryComponent(ui,QueryNum);
        //createFeedBackComponent(ui,QueryNum,"Actor","Tom Hanks");
        createFeedBackComponent(ui,QueryNum);
        createSearchResult(QueryNum,ui);
    }
    public void changeSearchResult(){
       LinearLayout parentLayout= (LinearLayout)getActivity().findViewById(R.id.search_result);
        parentLayout.removeAllViews();
        MainActivity mainActivity=(MainActivity) getActivity();
        Context ui=mainActivity.getContext();
        createSearchResult(QueryNum,ui);
    }
    public void removeChildView(){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.Query) ;
        System.out.println(parentLayout.toString());
        parentLayout.removeAllViews();
        parentLayout= (LinearLayout)getActivity().findViewById(R.id.search_result);
        parentLayout.removeAllViews();
        parentLayout=(LinearLayout)getActivity().findViewById(feadback) ;
        parentLayout.removeAllViews();
    }
    public void createQueryComponent(Context ui,String text,int QURYE_NUM){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.Query) ;
        //LinearLayout layout= CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        //Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,ui);
        QueryButton querybtn=CreateUIComponent.CreateQueryButton(text,ui);
        querybtn.setLongPressListener(new longpressListener(text,this,ui));
        //querybtn.setOnLongClickListener(new longpressListener(text));
       // parentLayout.addView(btntitle);
        //layout.addView(querybtn);
        parentLayout.addView(querybtn);
    }
    public void createQueryComponent(Context ui,String entity,String label){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.Query) ;
        //LinearLayout layout= CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        //Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,ui);
        //QueryButton querybtn=CreateUIComponent.CreateQueryButton(entity,ui);
        FeedBack querybtn=CreateUIComponent.createFeedBack(ui,label,entity);
        querybtn.setOnTouchListener(new touchListener(this,label,1));
        //querybtn.setLongPressListener(new longpressListener(entity));
        // parentLayout.addView(btntitle);
        //layout.addView(querybtn);
        parentLayout.addView(querybtn);
    }
    public void createQueryComponent(Context ui,String entity){
        LinearLayout parentLayout = (LinearLayout) getActivity().findViewById(R.id.Query);
        QueryButton querybtn = CreateUIComponent.CreateQueryButton(entity, ui);
        querybtn.setOnTouchListener(new touchListener(this, null, 1));
        querybtn.setListener();
        querybtn.setLongPressListener(new longpressListener(entity, this, ui));
        parentLayout.addView(querybtn);
    }
    public void createQueryComponent(Context ui,int QURYE_NUM) {
        LinearLayout parentLayout = (LinearLayout) getActivity().findViewById(R.id.Query);
        // LinearLayout layout= CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        //Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,ui);
        //parentLayout.addView(btntitle);
        List<FeedBackModel>QueryList=this.QueryList.get(QURYE_NUM);
        if(QueryList!=null) {
            for (int i = 0; i < QueryList.size(); i++) {
                if (QueryList.get(i) != null) {
                    String Entity = QueryList.get(i).getFeature().getEntity();
                    String label = QueryList.get(i).getFeature().getLabel();
                    int weight =QueryList.get(i).getWeight();
                    String[] querylist = Entity.split(",\\s");
                    if (label == null) {
                        for (int j = 0; j < querylist.length; j++) {
                            //System.out.println("create UI:"+querylist[j]);
                            QueryButton querybtn = CreateUIComponent.CreateQueryButton(querylist[j], ui,weight);
                            querybtn.setOnTouchListener(new touchListener(this, null, 1));
                            querybtn.setListener();
                            querybtn.setLongPressListener(new longpressListener(querylist[j], this, ui));
                            parentLayout.addView(querybtn);
                        }
                    } else {
                        FeedBack queryBtn = CreateUIComponent.createFeedBack(ui, label, Entity,weight);
                        queryBtn.setOnTouchListener(new touchListener(this, label, 1));
                        queryBtn.setListener();
                        parentLayout.addView(queryBtn);
                    }
                }
            }
        }
    }
    public void createFeedBackComponent(Context ui,String entity){
        LinearLayout parentLayout = (LinearLayout) getActivity().findViewById(feadback);
        QueryButton queryButton =CreateUIComponent.CreateQueryButton(entity,ui);
        queryButton.setOnTouchListener(new touchListener(this, null, 1));
        queryButton.setListener();
        queryButton.setLongPressListener(new longpressListener(entity, this, ui));
        parentLayout.addView(queryButton);
    }
    public void createFeedBackComponent(Context ui,int QURYE_NUM){
        //System.out.println("feedbackQueryNum:"+String.valueOf(QURYE_NUM));
        //System.out.println("create feedback");
        List<FeedBackModel>FeedbackList=this.FeedbackList.get(QURYE_NUM);
        //System.out.println("size:"+String.valueOf(FeedbackList.size()));
        if(FeedbackList!=null) {
            LinearLayout parentLayout = (LinearLayout) getActivity().findViewById(feadback);
            for (int i = 0; i < FeedbackList.size(); i++) {
                FeatureButtonModel feature = FeedbackList.get(i).getFeature();
                int weight = FeedbackList.get(i).getWeight();
                if (feature != null) {
                    if(feature.getLabel()==null){
                        System.out.println("weight:"+weight);
                        QueryButton queryButton = CreateUIComponent.CreateQueryButton(feature.getEntity(),ui,weight);
                        queryButton.setOnTouchListener(new touchListener(this,1));
                        parentLayout.addView(queryButton);
                    }else {
                        FeedBack fbk = CreateUIComponent.createFeedBack(ui, FeedbackList.get(i).getFeature().getLabel(), FeedbackList.get(i).getFeature().getEntity());
                        fbk.setWeight(weight);
                        fbk.setOnTouchListener(new touchListener(this, FeedbackList.get(i).getFeature().getLabel(), 1));
                        parentLayout.addView(fbk);
                    }
                }
            }
        }
    }
    public void createFeedBackComponent(Context ui,int QURYE_NUM,String label,String entity){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(feadback) ;
        //Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,ui);
        FeedBack fbk=CreateUIComponent.createFeedBack(ui,label,entity);
        // System.out.println("fuck"+fbk.getBtn().getLabel().getText());
        //parentLayout.addView(btntitle);
        fbk.setOnTouchListener(new touchListener(this,label,1));
        fbk.setLongPressListener(new longpressListener(entity,this,ui));
        parentLayout.addView(fbk);
    }
    public void createSearchResult(int QURYE_NUM,Context ui){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.search_result);
        //Button resultbtn=CreateUIComponent.CreateResultBtn(QURYE_NUM,ui);
        myHorizontalScrollView imageList=CreateUIComponent.createHorizontalScrollView(ui,this);
        ///imageList.setMainFragement(this);
        LinearLayout imgLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        GlobalData data=(GlobalData) ui.getApplicationContext();
        HashMap<String,String>mp=data.getSimilarList();
        List<String>simList=data.getSimilarEntity();
        Result res=new Result();
        res.setSimilarList(mp);
        res.setSimilarEntity(simList);
        for(String key:simList){
            ImageNode myimage=new ImageNode(ui,null);
            ///System.out.println("name:"+key+" "+mp.get(key));
            myimage.setEntityName(key);
            myimage.loadEntityImage(mycontext,mp.get(key),this);
            //myimage.setlongpressListener(new longpressListener(key,this,ui));
            myimage.setOnTouchListener(new touchListener(this,key,4,ui));
           // myimage.setDragListener(new dragListener());
            imgLayout.addView(myimage);
        }
        imageList.addView(imgLayout);
        //parentLayout.addView(resultbtn);
        parentLayout.addView(imageList);
        MyScrollView myscrollview = CreateUIComponent.createScrollView(ui,this);
        LinearLayout verticalLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL);
        LinearLayout EntityLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        LinearLayout leftLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        LinearLayout rightLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        List<FeatureButtonModel>featureList=data.getFeatureList();
        res.setFeatureList(featureList);
        historyList.put(QURYE_NUM,res);
        int length=featureList.size();
        for(int i=0;i<length;i++){
            if(i%2==0) {
                FeatureButtonModel feature = featureList.get(i);
                featureButton leftBtn = CreateUIComponent.createFeatureButton(ui, feature.getLabel(), feature.getEntity(), feature.getType());
                leftBtn.setOnTouchListener(new touchListener(this, feature.getLabel(),ui));
                //leftBtn.setLongPressListener(new longpressListener(feature.getEntity(), this, ui));
                leftLayout.addView(leftBtn);
            }else {
                FeatureButtonModel feature = featureList.get(i);
                featureButton rightBtn = CreateUIComponent.createFeatureButton(ui, feature.getLabel(), feature.getEntity(), feature.getType());
                rightBtn.setOnTouchListener(new touchListener(this, feature.getLabel(),ui));
               //rightBtn.setLongPressListener(new longpressListener(feature.getEntity(), this, ui));
                rightLayout.addView(rightBtn);
            }
        }
        EntityLayout.addView(leftLayout);
        EntityLayout.addView(rightLayout);
        verticalLayout.addView(EntityLayout);
        myscrollview.addView(verticalLayout);
        parentLayout.addView(myscrollview);
    }
    public void createSearchResult(int QURYE_NUM,Context ui,int height){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.search_result);
        //Button resultbtn=CreateUIComponent.CreateResultBtn(QURYE_NUM,ui);
        ScrollView myscrollview = CreateUIComponent.createScrollView(ui);
        LinearLayout scrollLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL);
        HorizontalScrollView imageList=CreateUIComponent.CreateHorizontalScrollView(ui);
        ///imageList.setMainFragement(this);
        LinearLayout imgLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        GlobalData data=(GlobalData) ui.getApplicationContext();
        HashMap<String,String>mp=data.getSimilarList();
        List<String>simList=data.getSimilarEntity();
        Result res=new Result();
        res.setSimilarList(mp);
        res.setSimilarEntity(simList);
        for(String key:simList){
            ImageNode myimage=new ImageNode(ui,null);
            ///System.out.println("name:"+key+" "+mp.get(key));
            myimage.setEntityName(key);
            myimage.loadEntityImage(mycontext,mp.get(key),this);
            //myimage.setlongpressListener(new longpressListener(key,this,ui));
            //myimage.setOnTouchListener(new touchListener(this,key,4,ui));
            // myimage.setDragListener(new dragListener());
            imgLayout.addView(myimage);
        }
        imageList.addView(imgLayout);
        //parentLayout.addView(resultbtn);
        scrollLayout.addView(imageList);
        //parentLayout.addView(imageList);

        LinearLayout EntityLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        LinearLayout leftLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        LinearLayout rightLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        List<FeatureButtonModel>featureList=data.getFeatureList();
        res.setFeatureList(featureList);
        historyList.put(QURYE_NUM,res);
        int length=featureList.size();
        for(int i=0;i<length;i++){
            if(i%2==0) {
                FeatureButtonModel feature = featureList.get(i);
                featureButton leftBtn = CreateUIComponent.createFeatureButton(ui, feature.getLabel(), feature.getEntity(), feature.getType());
                //leftBtn.setOnTouchListener(new touchListener(this, feature.getLabel(),ui));
                //leftBtn.setLongPressListener(new longpressListener(feature.getEntity(), this, ui));
                leftLayout.addView(leftBtn);
            }else {
                FeatureButtonModel feature = featureList.get(i);
                featureButton rightBtn = CreateUIComponent.createFeatureButton(ui, feature.getLabel(), feature.getEntity(), feature.getType());
                //rightBtn.setOnTouchListener(new touchListener(this, feature.getLabel(),ui));
                //rightBtn.setLongPressListener(new longpressListener(feature.getEntity(), this, ui));
                rightLayout.addView(rightBtn);
            }
        }
        EntityLayout.addView(leftLayout);
        EntityLayout.addView(rightLayout);
        //verticalLayout.addView(EntityLayout);
        scrollLayout.addView(EntityLayout);
        myscrollview.addView(scrollLayout);
        parentLayout.addView(myscrollview);
    }
}
