package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by GoGoalFight on 2018/1/17.
 */

public class myHorizontalScrollView extends HorizontalScrollView {
    private float startX, startY, lastX, lastY;
    MainFragement mainFragement;
    public myHorizontalScrollView(Context context,MainFragement mainFragement) {
        super(context);
        this.mainFragement=mainFragement;
    }
    public void setMainFragement(MainFragement mainFragement){
        this.mainFragement=mainFragement;
    }
    public myHorizontalScrollView(Context context){
        super(context);
    }
    public myHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

   @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
       int pointers=ev.getPointerCount();
       /*int lastPointers=mainFragement.getTouchHPointers();
       Toast.makeText(mainFragement.getContext(),"last pointers"+lastPointers,
               Toast.LENGTH_SHORT).show();
       System.out.println("last pointers:"+lastPointers);
       if(lastPointers==0){
           mainFragement.setTouchHPointers(pointers);
           System.out.println("horizontal:"+pointers);
           Toast.makeText(mainFragement.getContext(),"horizontal"+lastPointers,
                   Toast.LENGTH_SHORT).show();
       }
       else{
           if(pointers==2){
               mainFragement.setTouchHPointers(pointers);
           }
       }*/
       if(pointers>=3){
           mainFragement.setTouchVPointers(0);
           return super.onInterceptTouchEvent(ev);
       }else{
            return false;
       }
    }
   @Override
    public boolean onTouchEvent (MotionEvent ev){
       int pointers=ev.getPointerCount();
       if(pointers>=3){
           /*switch (action){
               case MotionEvent.ACTION_DOWN:
                   startX=getX();
                   startY=getY();
                   Toast.makeText(mainFragement.getContext(),"yes three",
                           Toast.LENGTH_SHORT).show();
                   break;
               case MotionEvent.ACTION_MOVE:
                   lastX=getX();
                   lastY=getY();
                   Toast.makeText(mainFragement.getContext(),"yes three",
                           Toast.LENGTH_SHORT).show();
                   break;
               case MotionEvent.ACTION_UP:
                   lastX=getX();
                   lastY=getY();
                   Toast.makeText(mainFragement.getContext(),"yes two",
                           Toast.LENGTH_SHORT).show();
                   if(Math.abs(lastX-startX)-Math.abs(lastY-startY)>=10){
                       return super.onTouchEvent(ev);
                   }
                   break;
           }*/
           return super.onTouchEvent(ev);
        }
        return true;
    }
}