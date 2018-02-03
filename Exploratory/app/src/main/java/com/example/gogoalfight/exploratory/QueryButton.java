package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * Created by GoGoalFight on 2017/10/12.
 */

public class QueryButton extends LinearLayout {
    private Button querybtn;
    private SeekBar seekbar;
    private int defaultValue=50;
    public QueryButton(Context context,AttributeSet attrs){
        super(context,attrs);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.querybutton, this, true);
        querybtn=(Button)findViewById(R.id.queryButton);
        seekbar=(SeekBar)findViewById(R.id.querWeight);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.QueryButton);
        if(attributes!=null) {
            String btntext = attributes.getString(R.styleable.QueryButton_button_text);
            int weight=attributes.getInt(R.styleable.QueryButton_seek_weight,defaultValue);
            setWeight(weight);
            if (!TextUtils.isEmpty(btntext)) {
                querybtn.setText(btntext);
            }
            //float weight=attributes.getFloat();
            attributes.recycle();
        }
     }
     public void setWeight(int value){
         seekbar.setProgress(value);
     }
     public void setText(String text){
         querybtn.setText(text);
     }
     public int getWeight(){
         return seekbar.getProgress();
     }
     public String getText(){
         return querybtn.getText().toString();
     }
     public void setListener(){
         seekbar.setOnSeekBarChangeListener(new seekListener((String)querybtn.getText()));
     }
     public void setOnTouchListener(touchListener listener){
         this.querybtn.setOnTouchListener(listener);
     }
     public Button getQuerybtn(){
         return querybtn;
     }
     public SeekBar getSeekbar(){
         return seekbar;
     }
     public void setLongPressListener(longpressListener listener){
        // System.out.println("set long click");
         //querybtn.setLongClickable(true);
         querybtn.setOnLongClickListener(listener);
     }
}
