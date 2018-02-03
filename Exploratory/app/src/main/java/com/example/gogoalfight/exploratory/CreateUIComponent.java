package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.net.Uri;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by GoGoalFight on 2017/10/8.
 */

public class CreateUIComponent {
    public static QueryButton CreateQueryButton(String text, Context ui){
        QueryButton btn=new QueryButton(ui,null);
        btn.setText(text);
        return btn;
    }
    public static QueryButton CreateQueryButton(String text, Context ui,int weight){
        QueryButton btn=new QueryButton(ui,null);
        btn.setText(text);
        btn.setWeight(weight);
        return btn;
    }
    public static Button CreateQuery(int id,Context ui){
        Button btn=new Button(ui);
        int width=200;
        int widvalue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                width, ui.getResources().getDisplayMetrics());
        btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        String text="Query"+String.valueOf(id);
        btn.setText(text);
        return btn;
    }
    public static Button CreateResultBtn(int id,Context ui){
        Button btn=new Button(ui);
        LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(param);
        btn.setText("ENTITY LIST(Q"+String.valueOf(id)+")");
        return btn;
    }
    public static  LinearLayout createLinearLayout(Context ui,int orientation){
        LinearLayout layout=new LinearLayout(ui);
        layout.setOrientation(orientation);
        LinearLayout.LayoutParams layoutparam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutparam);
        return layout;
    }
    public static  LinearLayout createLinearLayout(Context ui,int orientation,int margin_left){
        LinearLayout layout=new LinearLayout(ui);
        layout.setOrientation(orientation);
        margin_left=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                margin_left, ui.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutparam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutparam.setMargins(margin_left,0,0,0);
        layout.setLayoutParams(layoutparam);
        return layout;
    }
    public static myHorizontalScrollView createHorizontalScrollView(Context ui,MainFragement mainFragement){
        myHorizontalScrollView myview =new myHorizontalScrollView(ui,mainFragement);
        int scrollWidth=520;
        int paddingLeft=10;
        int paddingRight=20;
        int widvalue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
               scrollWidth , ui.getResources().getDisplayMetrics());
        int padvalue= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                paddingLeft , ui.getResources().getDisplayMetrics());
        int rigthvalue= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                paddingRight , ui.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(widvalue,LinearLayout.LayoutParams.WRAP_CONTENT);
        myview.setLayoutParams(param);
        myview.setPadding(padvalue,0,rigthvalue,0);
        return myview;
    }
    public static myHorizontalScrollView createHorizontalScrollView(Context ui){
        myHorizontalScrollView myview =new myHorizontalScrollView(ui);
        int scrollWidth=520;
        int widvalue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                scrollWidth , ui.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(widvalue,LinearLayout.LayoutParams.WRAP_CONTENT);
        myview.setLayoutParams(param);
        return myview;
    }
    public static HorizontalScrollView CreateHorizontalScrollView(Context ui){
        HorizontalScrollView myview =new HorizontalScrollView(ui);
        int scrollWidth=520;
        int widvalue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                scrollWidth , ui.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(widvalue,LinearLayout.LayoutParams.WRAP_CONTENT);
        myview.setLayoutParams(param);
        return myview;
    }
    public static ScrollView createScrollView(Context ui){
        ScrollView myview = new ScrollView(ui);
        int scrollWidth=500;
       // int heightvalue = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,scrollHeight,ui.getResources().getDisplayMetrics());
        int widvalue = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,scrollWidth,ui.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(widvalue, ViewGroup.LayoutParams.MATCH_PARENT);
        myview.setLayoutParams(param);
        return myview;
    }
    public static MyScrollView createScrollView(Context ui,MainFragement mainFragement){
        MyScrollView myview = new MyScrollView(ui);
        myview.setMainFragement(mainFragement);
        int scrollHeight=267;
        int scrollWidth=500;
        int heightvalue = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,scrollHeight,ui.getResources().getDisplayMetrics());
        int widvalue = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,scrollWidth,ui.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(widvalue,heightvalue);
        myview.setLayoutParams(param);
        return myview;
    }
    public static MyScrollView createScrollView(Context ui,MainFragement mainFragement,int height){
        MyScrollView myview = new MyScrollView(ui);
        myview.setMainFragement(mainFragement);
        int scrollHeight=height;
        int scrollWidth=500;
        int heightvalue = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,scrollHeight,ui.getResources().getDisplayMetrics());
        int widvalue = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,scrollWidth,ui.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(widvalue,heightvalue);
        myview.setLayoutParams(param);
        return myview;
    }
    public static featureButton createFeatureButton(Context ui,String labelText,String entityText){
        featureButton btn=new featureButton(ui,null);
        btn.setLabelText(labelText);
        btn.setEntityText(entityText);
        return btn;
    }
    //distinguish the relation and category relation:1 and category:2
    public static featureButton createFeatureButton(Context ui,String labelText,String entityText,int type){
        featureButton btn=new featureButton(ui,null);
        btn.setLabelText(labelText);
        btn.setEntityText(entityText);
        btn.setDirection(type);
        return btn;
    }
    public static featureButton createFeatureButton(Context ui,String labelText,String entityText,int type,int labelWidth,int entityWidth){
        featureButton btn=new featureButton(ui,null);
        btn.setLabelText(labelText);
        btn.setEntityText(entityText);
        btn.setFeatureWidth(labelWidth,entityWidth);
        return btn;
    }
    public static featureButton createFeatureButton(Context ui,String labelText,String entityText,int type,int labelWidth,int entityWidth,int bottum){
        featureButton btn=new featureButton(ui,null);
        btn.setLabelText(labelText);
        btn.setEntityText(entityText);
        btn.setFeatureWidth(labelWidth,entityWidth);
        btn.setFeatureBottum(bottum);
        return btn;
    }
    public  static FeedBack createFeedBack(Context ui,String labelText,String entityText){
        FeedBack fbk=new FeedBack(ui,null);
        //featureButton btn=createFeatureButton(ui,labelText,entityText);
        fbk.setBtn(labelText,entityText);
        fbk.setWeight(50);
        //fbk.setOnTouchListener(new touchListener());
        return fbk;
    }
    public  static FeedBack createFeedBack(Context ui,String labelText,String entityText,int weight){
        FeedBack fbk=new FeedBack(ui,null);
        //featureButton btn=createFeatureButton(ui,labelText,entityText);
        fbk.setBtn(labelText,entityText);
        fbk.setWeight(weight);
        //fbk.setOnTouchListener(new touchListener());
        return fbk;
    }
    public static void loadImage(Context ui, ImageView view,String url){

        if(url.contains("not-found")){
            url="http://10.48.20.198:8080/Knowledge_Explore/img/not-found.png";
        }
        //System.out.println("load url:"+url);
        Glide.with(ui).load(url).into(view);
    }
    public static void loadImage(Context ui, ImageView view,String url,MainFragement mainFragement) {
        System.out.println(url);
        if(url.contains("not-found")){
            url="http://10.48.20.198:8080/Knowledge_Explore/img/not-found.png";
           // Glide.with(ui).load(url).into(view);
        }else if(url.contains("SVG")||url.contains("svg")){
            Uri uri = Uri.parse(url);
            System.out.println("uri"+uri);
            mainFragement.requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .into(view);
        }else {
            Glide.with(ui).load(url).into(view);
        }
    }
}
