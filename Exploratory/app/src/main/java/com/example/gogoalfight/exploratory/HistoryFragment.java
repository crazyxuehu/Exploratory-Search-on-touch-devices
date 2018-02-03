package com.example.gogoalfight.exploratory;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

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
import com.example.gogoalfight.exploratory.model.Result;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GoGoalFight on 2017/10/21.
 */

public class HistoryFragment extends Fragment  {
    private int QueryNum;
    private Context myContext;
    private List<FeedBackModel>QueryList;
    private List<FeedBackModel>FeedBackList;
    private List<FeatureButtonModel>FeatureButtonList;
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
        return inflater.inflate(R.layout.historyfragment,container,false);
    }
    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createQueryComponent(myContext,"Tom Hanks",QueryNum);
    }
    public void CreateData(Context ui,int queryNum){
        QueryNum=queryNum;
        myContext=ui;
    }*/
    public void changeData(Context ui,int QueryNum){
        removeChildView();
        //System.out.println("hello world"+String.valueOf(QueryNum));
        MainFragement mainFragement=(MainFragement) getActivity().getFragmentManager().findFragmentById(R.id.mainfragment);
        QueryList=mainFragement.getQueryList(QueryNum);
        FeedBackList=mainFragement.getFeedbackList(QueryNum);
        createQueryComponent(ui,QueryNum);
        createFeedBackComponent(ui,QueryNum);
        Result res=mainFragement.getHistoryList().get(QueryNum);
        createSearchResult(res,QueryNum,ui);
    }
    public void removeChildView(){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hisQuery) ;
        System.out.println(parentLayout.toString());
        parentLayout.removeAllViews();
        parentLayout= (LinearLayout)getActivity().findViewById(R.id.hissearch_result);
        parentLayout.removeAllViews();
        parentLayout=(LinearLayout)getActivity().findViewById(R.id.hisfeedback) ;
        parentLayout.removeAllViews();
    }
    public void createQueryComponent(Context ui,String text,int QURYE_NUM){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hisQuery) ;
        LinearLayout layout= CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        //Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,ui);
        QueryButton querybtn=CreateUIComponent.CreateQueryButton(text,ui);
        //parentLayout.addView(btntitle);
        layout.addView(querybtn);
        parentLayout.addView(layout);
    }
    public void createQueryComponent(Context ui,int QURYE_NUM){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hisQuery) ;
        if(QueryList!=null){
            for (int i = 0; i < QueryList.size(); i++) {
                if (QueryList.get(i) != null) {
                    String Entity=QueryList.get(i).getFeature().getEntity();
                    String label=QueryList.get(i).getFeature().getLabel();
                    int weight=QueryList.get(i).getWeight();
                    String[] querylist = Entity.split(",\\s");
                    if(label==null) {
                        for (int j = 0; j < querylist.length; j++) {
                            QueryButton querybtn = CreateUIComponent.CreateQueryButton(querylist[j], ui);
                            querybtn.setWeight(weight);
                            querybtn.setListener();
                            parentLayout.addView(querybtn);
                        }
                    }else{
                        FeedBack queryBtn=CreateUIComponent.createFeedBack(ui,label,Entity);
                        queryBtn.setWeight(weight);
                        queryBtn.setListener();
                        parentLayout.addView(queryBtn);
                    }
                }
            }
        }
    }
    public void createFeatureComponent(Context ui,String label,String entity){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hisfeatureLayout1);
        featureButton btn=CreateUIComponent.createFeatureButton(ui,label,entity);
        parentLayout.addView(btn);
    }
    public void createFeedBackComponent(Context ui,int QURYE_NUM,String label,String entity){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hisfeedback) ;
        //Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,ui);
        FeedBack fbk=CreateUIComponent.createFeedBack(ui,label,entity);
        // System.out.println("fuck"+fbk.getBtn().getLabel().getText());
        //parentLayout.addView(btntitle);
        parentLayout.addView(fbk);
    }
    public void createFeedBackComponent(Context ui,int QURYE_NUM){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hisfeedback) ;
        if(FeedBackList!=null){
            for (int i=0;i<FeedBackList.size();i++) {
                FeatureButtonModel feature=FeedBackList.get(i).getFeature();
                int weight=FeedBackList.get(i).getWeight();
                if(feature!=null) {
                    if(feature.getLabel()==null){
                        QueryButton queryButton = CreateUIComponent.CreateQueryButton(feature.getEntity(),ui,weight);
                        //queryButton.setOnTouchListener(new touchListener(this,1));
                        parentLayout.addView(queryButton);
                    }else{
                        FeedBack fbk = CreateUIComponent.createFeedBack(ui, feature.getLabel(), feature.getEntity());
                        //System.out.println(feature.getLabel()+"  "+feature.getEntity());
                        fbk.setWeight(weight);
                        parentLayout.addView(fbk);
                    }
                }
            }
        }
    }
    public void createSearchResult(int QURYE_NUM,Context ui){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hissearch_result);
        //Button resultbtn=CreateUIComponent.CreateResultBtn(QURYE_NUM,ui);
        HorizontalScrollView imageList=CreateUIComponent.CreateHorizontalScrollView(ui);
        LinearLayout imgLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        for(int i=0;i<5;i++){
            ImageNode myimage=new ImageNode(ui,null);
            myimage.setEntityImage(R.drawable.img1);
            myimage.setEntityName("noodles");
            imgLayout.addView(myimage);
        }
        imageList.addView(imgLayout);
        //parentLayout.addView(resultbtn);
        parentLayout.addView(imageList);
        LinearLayout EntityLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        LinearLayout leftLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        LinearLayout rightLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        for(int i=0;i<4;i++){
            featureButton leftBtn=CreateUIComponent.createFeatureButton(ui,"Relation","Entity");
            leftLayout.addView(leftBtn);
            featureButton rightBtn=CreateUIComponent.createFeatureButton(ui,"Relation","Entity");
            rightLayout.addView(rightBtn);
        }
        EntityLayout.addView(leftLayout);
        EntityLayout.addView(rightLayout);
        parentLayout.addView(EntityLayout);
    }
    public void createSearchResult(Result res,int QURYE_NUM,Context ui){
        LinearLayout parentLayout=(LinearLayout)getActivity().findViewById(R.id.hissearch_result);
        //Button resultbtn=CreateUIComponent.CreateResultBtn(QURYE_NUM,ui);
        HorizontalScrollView imageList=CreateUIComponent.CreateHorizontalScrollView(ui);
        LinearLayout imgLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        HashMap<String,String> mp=res.getSimilarList();
        List<String>simList=res.getSimilarEntity();
        for(String key:simList){
            ImageNode myimage=new ImageNode(ui,null);
            //myimage.setEntityImage(R.drawable.img1);
            myimage.setEntityName(key);
            myimage.loadEntityImage(ui,mp.get(key),this);
            //myimage.setlongpressListener(new longpressListener(key,this,ui));
            imgLayout.addView(myimage);

        }
        imageList.addView(imgLayout);
        //parentLayout.addView(resultbtn);
        parentLayout.addView(imageList);
        LinearLayout EntityLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.HORIZONTAL);
        LinearLayout leftLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        LinearLayout rightLayout=CreateUIComponent.createLinearLayout(ui,LinearLayout.VERTICAL,5);
        List<FeatureButtonModel>featureList=res.getFeatureList();
        int length=featureList.size();
        for(int i=0;i<length/2;i++){
            FeatureButtonModel feature=featureList.get(i);
            featureButton leftBtn=CreateUIComponent.createFeatureButton(ui,feature.getLabel(),feature.getEntity(),feature.getType());
            //leftBtn.setOnTouchListener(new touchListener(this,feature.getLabel()));
            //leftBtn.setLongPressListener(new longpressListener(feature.getEntity(),this,ui));
            leftLayout.addView(leftBtn);
            feature=featureList.get(length-1-i);
            featureButton rightBtn=CreateUIComponent.createFeatureButton(ui,feature.getLabel(),feature.getEntity(),feature.getType());
           // rightBtn.setOnTouchListener(new touchListener(this,feature.getLabel()));
            //rightBtn.setLongPressListener(new longpressListener(feature.getEntity(),this,ui));
            rightLayout.addView(rightBtn);
        }
        EntityLayout.addView(leftLayout);
        EntityLayout.addView(rightLayout);
        parentLayout.addView(EntityLayout);
    }
}
