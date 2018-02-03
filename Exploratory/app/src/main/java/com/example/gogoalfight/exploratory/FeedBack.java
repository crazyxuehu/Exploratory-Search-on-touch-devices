package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * Created by GoGoalFight on 2017/10/15.
 */

public class FeedBack extends LinearLayout {
    private featureButton btn;
    private SeekBar seekbar;
    private int defaultLabelWidth=75;
    private int defaultEntityWidth=130;
    private int defaultWeight=50;
    private int defaultMarginleft=5;
    public FeedBack(Context context, AttributeSet attrs){
        super(context,attrs);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.feedback, this, true);
        btn=(featureButton) findViewById(R.id.fbackbutton);
        seekbar=(SeekBar) findViewById(R.id.fbackweight);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FeedBack);
        if(attributes!=null) {
            String labelText = attributes.getString(R.styleable.FeedBack_feadback_label);
            String entityText=attributes.getString(R.styleable.FeedBack_feedback_entity);
            int LabelWidth=attributes.getInt(R.styleable.FeedBack_feedback_Labelwidth,defaultLabelWidth);
            int EntityWidth=attributes.getInt(R.styleable.FeedBack_feedback_Relationwidth,defaultEntityWidth);
            int weight=attributes.getInt(R.styleable.FeedBack_feedback_weight,defaultWeight);
            int marginLeft=attributes.getInt(R.styleable.FeedBack_feedback_marginleft,defaultMarginleft);
            setLabelLayout(LabelWidth);
            setEntityLayout(EntityWidth,marginLeft);
            seekbar.setProgress(weight);
            if (!TextUtils.isEmpty(labelText)) {
                btn.setLabelText(labelText);
            }
            if(!TextUtils.isEmpty(entityText)){
                btn.setEntityText(entityText);
            }
            //float weight=attributes.getFloat();
            attributes.recycle();
        }
    }
    public void setBtn(String label,String name){
        btn.setLabelText(label);
        btn.setEntityText(name);
        /*int width=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                defaultWidth, getResources().getDisplayMetrics());
        Button labelBtn=btn.getLabel();
       // System.out.println(labelBtn.getText());
        LinearLayout.LayoutParams paramLabel=(LayoutParams)labelBtn.getLayoutParams();
        paramLabel.width=width;
        labelBtn.setLayoutParams(paramLabel);
        btn.setLabel(labelBtn);
        Button entityBtn=btn.getEntity();
        System.out.println(entityBtn.getText());
        LinearLayout.LayoutParams paramEntity=(LayoutParams)entityBtn.getLayoutParams();
        paramEntity.width=width;
        entityBtn.setLayoutParams(paramEntity);
        btn.setEntity(entityBtn);*/
    }
    public void setWeight(int weight){
        seekbar.setProgress(weight);
    }
    public void setLabelLayout(int width){
        width=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                width, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layout=btn.getLabelLayout();
        layout.width=width;
        btn.setLabelLayout(layout);
    }
    public void setEntityLayout(int width,int left){
        width=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                width, getResources().getDisplayMetrics());
        left=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                left, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layout=btn.getEntitylayout();
        layout.width=width;
        //layout.setMarginStart(left);
        btn.setEntityLayout(layout);
    }
    public String getText(){
        return btn.getEntity().getText().toString();
    }
    public String getLabel(){
        return btn.getLabel().getText().toString();
    }
    public int getWeight(){
        return seekbar.getProgress();
    }
    public void setListener(){
        seekbar.setOnSeekBarChangeListener(new seekListener((String)btn.getEntity().getText()));
    }
    public  void setOnTouchListener(touchListener listener){
        // label.setOnTouchListener(listener);
        btn.setOnTouchListener(listener);
    }
    public void setLongPressListener(longpressListener listener){
        btn.setLongPressListener(listener);
    }
    public  void setSeekbar(SeekBar bar){
        seekbar=bar;
    }
    public featureButton getBtn(){
        return btn;
    }
    public SeekBar getSeekbar(){
        return seekbar;
    }
    public int getDirection(){
        return btn.getDirection();
    }
}
