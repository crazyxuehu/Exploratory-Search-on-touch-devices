package com.example.gogoalfight.exploratory;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by GoGoalFight on 2018/1/18.
 */

public class mytouchListener implements View.OnTouchListener {
    private float startx=0;
    private float starty=0;
    private float endx=0;
    private  float endy=0;
    private MainFragement mainFragement;
    private String label;
    private long starttime;
    private int type;
    public mytouchListener(MainFragement mainFragement,String label){
        this.mainFragement=mainFragement;
        this.label=label;
        this.type=0;
    }
    public mytouchListener(MainFragement mainFragement,String label,int type){
        this.mainFragement=mainFragement;
        this.label=label;
        this.type=type;
    }
    public mytouchListener(MainFragement mainFragement,int type){
        //type:3 change to current search
        this.mainFragement=mainFragement;
        this.type=type;
    }
    public void setLabel(String label){
        this.label=label;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        int pointers=MotionEventCompat.getPointerCount(event);
        if(pointers==1)
            v.getParent().requestDisallowInterceptTouchEvent(true);
        switch(action) {
            case (MotionEvent.ACTION_DOWN):
                startx=event.getX();
                starty=event.getY();
                System.out.println("begin touch"+String.valueOf(startx));
                starttime=System.currentTimeMillis();
                break;
            case (MotionEvent.ACTION_MOVE):
                endx=event.getX();
                endy=event.getY();
                //System.out.println("touch move"+String.valueOf(endx));
                // return true;
                break;
            case (MotionEvent.ACTION_UP):
                //Log.d(DEBUG_TAG, "Action was UP");
                System.out.println("action stop");
                break;
        }
            return true;
    }
}
