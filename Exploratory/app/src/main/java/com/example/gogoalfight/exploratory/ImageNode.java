package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by GoGoalFight on 2017/10/15.
 */

public class ImageNode extends LinearLayout {
    private ImageView entityImage;
    private TextView entityName;
    private final int defValue = R.drawable.notfound;

    public ImageNode(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.imagenode, this, true);
        entityImage = (ImageView) findViewById(R.id.image_show);
        entityName = (TextView) findViewById(R.id.image_text);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageNode);
        if (attributes != null) {
            String imgtext = attributes.getString(R.styleable.ImageNode_imagenode_text);
            int imgsrc = attributes.getResourceId(R.styleable.ImageNode_imagenode_src, defValue);
            if (!TextUtils.isEmpty(imgtext)) {
                entityName.setText(imgtext);
            }
            entityImage.setImageResource(imgsrc);
            attributes.recycle();
        }
    }
    public void setEntityImage(int  myImage){
        entityImage.setImageResource(myImage);
    }
    public void loadEntityImage(Context ui,String url){
        //System.out.println("url: "+url);
        if(url.contains("not-found")){
            int resource=R.drawable.notfound;
            Glide.with(ui).load(resource).into(entityImage);
        }else{
            Glide.with(ui).load(url).into(entityImage);
        }
    }
    public void loadEntityImage(Context ui,String url,HistoryFragment historyFragment){
        //System.out.println("url: "+url);
        if(url.contains("not-found")){
            int resource=R.drawable.notfound;
            Glide.with(ui).load(resource).into(entityImage);
        }else if(url.contains("SVG")||url.contains("svg")){
            Uri uri = Uri.parse(url);
            System.out.println("uri"+uri);
            historyFragment.requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .into(entityImage);
        }else{
            Glide.with(ui).load(url).into(entityImage);
        }
    }
    public void loadEntityImage(Context ui,String url,MainFragement mainFragement){
        //System.out.println("url: "+url);
        if(url.contains("not-found")){
           int resource=R.drawable.notfound;
            Glide.with(ui).load(resource).into(entityImage);
        }else if(url.contains("SVG")||url.contains("svg")){
            Uri uri = Uri.parse(url);
            System.out.println("uri"+uri);
            mainFragement.requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .into(entityImage);
        }else {
            Glide.with(ui).load(url).into(entityImage);
        }
    }
    public void setEntityName(String myName){
        /*if(myName.length()>23){
            myName=myName.substring(0,23);
            myName+="...";
        }*/
        entityName.setText(myName);
    }
    public ImageView getEntityImage(){
        return entityImage;
    }
    public TextView getEntityName(){
        return entityName;
    }
    public void setlongpressListener(longpressListener listener){
        entityImage.setLongClickable(true);
        entityImage.setOnLongClickListener(listener);
        entityName.setLongClickable(true);
        entityName.setOnLongClickListener(listener);
    }
    public void setDragListener(dragListener listener){
        entityImage.setOnDragListener(listener);
    }
}