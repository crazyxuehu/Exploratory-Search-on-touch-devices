package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by GoGoalFight on 2017/10/15.
 */

public class featureButton extends LinearLayout {
    private Button label;
    private Button entity;
    private int direction=0;
    private int defaultLabelWidth=80;
    private int defaultEntityWidth=150;
    public featureButton(Context context, AttributeSet attrs){
        super(context,attrs);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.featurebutton, this, true);
        label=(Button)findViewById(R.id.featureRelation);
        entity=(Button)findViewById(R.id.featureEntity);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FeatureButton);
        if(attributes!=null) {
            String labelText = attributes.getString(R.styleable.FeatureButton_relation_text);
            String entityText=attributes.getString(R.styleable.FeatureButton_entity_text);
            int labelWidth=attributes.getInt(R.styleable.FeatureButton_label_width,defaultLabelWidth);
            int entityWidth=attributes.getInt(R.styleable.FeatureButton_entity_width,defaultEntityWidth);
            setFeatureWidth(labelWidth,entityWidth);
            if (!TextUtils.isEmpty(labelText)) {
                label.setText(labelText);
            }
            if(!TextUtils.isEmpty(entityText)){
                entity.setText(entityText);
            }
            //float weight=attributes.getFloat();
           /* if (width!=defaultWidth) {
                LayoutParams param = new LinearLayout.LayoutParams(width, heightValue);
                label.setLayoutParams(param);
                entity.setLayoutParams(param);
            }*/
            attributes.recycle();
        }
    }
    public void setFeatureWidth(int labelWidth,int entityWidth){
        labelWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                labelWidth, getResources().getDisplayMetrics());
        entityWidth=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                entityWidth, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layout=getLabelLayout();
        layout.width=labelWidth;
        setLabelLayout(layout);
        layout=getEntitylayout();
        layout.width=entityWidth;
        setEntityLayout(layout);
    }
    public void setFeatureBottum(int bottum){
        bottum=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                bottum, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layout=getLabelLayout();
        layout.setMargins(15,bottum,0,bottum);
        setLabelLayout(layout);
        layout=getEntitylayout();
        layout.setMargins(0,bottum,0,bottum);
        setEntityLayout(layout);
    }
    public void setLabelText(String text){
        label.setText(text);
    }
    public  void setEntityText(String text){
        entity.setText(text);
    }
    public LinearLayout.LayoutParams getLabelLayout(){
        return (LayoutParams) label.getLayoutParams();
    }
    public LinearLayout.LayoutParams getEntitylayout(){
        return (LayoutParams) entity.getLayoutParams();
    }
    public void setLabelLayout(LinearLayout.LayoutParams layout){
        label.setLayoutParams(layout);
    }
    public void setEntityLayout(LinearLayout.LayoutParams layout){
        entity.setLayoutParams(layout);
    }
    public Button getLabel(){
        return label;
    }
    public Button getEntity(){
        return entity;
    }
    public void setLabel(Button newLabel){
        label=newLabel;
    }
    public  void setEntity(Button newEntity){
        entity=newEntity;
    }
    public  void setOnTouchListener(touchListener listener){
           // label.setOnTouchListener(listener);
            entity.setOnTouchListener(listener);
    }
    public void setLongPressListener(longpressListener listener){
        //entity.setLongClickable(true);
        entity.setOnLongClickListener(listener);
        //label.setLongClickable(true);
        //label.setOnLongClickListener(listener);

    }
    public int getDirection(){
        return this.direction;
    }
    public void setDirection(int direction){
        this.direction=direction;
    }
   /* public void setBtnWidth(int width){
        LinearLayout.LayoutParams param=(LinearLayout.LayoutParams) label.getLayoutParams();
        param.height=defaultHeight;
        param.width=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                width, getResources().getDisplayMetrics());
        label.setLayoutParams(param);
        entity.setLayoutParams(param);
    }
    public int getBtnWidth(){
        return btnWidth;
    }*/
}
