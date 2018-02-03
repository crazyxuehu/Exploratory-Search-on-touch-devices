package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by GoGoalFight on 2017/11/3.
 */

public class MyScrollView extends ScrollView {
    private MainFragement mainFragement;
    private float startx;
    private float starty;
    private float endx;
    private float endy;
    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setMainFragement(MainFragement mainFragement){
        this.mainFragement=mainFragement;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int pointers=ev.getPointerCount();
       /*Toast.makeText(mainFragement.getContext(),"pointers:"+pointers,
                Toast.LENGTH_SHORT).show();*/
        /*Toast.makeText(mainFragement.getContext(),"parent pointers"+pointers,
                Toast.LENGTH_SHORT).show();*/
       /* int lastPointers=mainFragement.getTouchVPointers();
        Toast.makeText(mainFragement.getContext(),"last pointers"+lastPointers,
                Toast.LENGTH_SHORT).show();
        if(lastPointers==0)
            mainFragement.setTouchVPointers(pointers);
        else{
            if(pointers==2){
                mainFragement.setTouchVPointers(pointers);
            }
        }*/
        if(pointers>=3){
            //mainFragement.setTouchVPointers(0);
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
/*    @Override
    public boolean onTouchEvent (MotionEvent motionEvent){
       // int pointers=motionEvent.getPointerCount();
        *//*Toast.makeText(mainFragement.getContext(),"my pointers"+pointers,
                Toast.LENGTH_SHORT).show();*//*
       // System.out.println("myscrollview:"+pointers);
       *//* int lastPointers=mainFragement.getTouchVPointers();
        Toast.makeText(mainFragement.getContext(),"last pointers"+lastPointers,
                Toast.LENGTH_SHORT).show();
        if(lastPointers==0)
            mainFragement.setTouchVPointers(pointers);
        else{
            if(pointers==2){
                mainFragement.setTouchVPointers(pointers);
            }
        }*//*
       // int action=motionEvent.getAction();
            *//*switch (action){
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(mainFragement.getContext(),"yes one1",
                            Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Toast.makeText(mainFragement.getContext(),"yes one2",
                            Toast.LENGTH_SHORT).show();
                    mainFragement.setTouchPointers(pointers);
                    break;
                case MotionEvent.ACTION_UP:
                    Toast.makeText(mainFragement.getContext(),"yes one3",
                            Toast.LENGTH_SHORT).show();
                    if(Math.abs(endy-starty)-Math.abs(endx-startx)>=10){
                        return super.onTouchEvent(motionEvent);
                    }
                    break;
            }*//*
            if(pointers>=3)
                return super.onTouchEvent(motionEvent);
            else
                return true;
    }*/
}
