package com.example.gogoalfight.exploratory;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Stack;

/**
 * Created by GoGoalFight on 2017/12/14.
 */

public class QueryTouchListener implements View.OnTouchListener{
    private float startx=0;
    private float starty=0;
    private float endx=0;
    private  float endy=0;
    private MainFragement mainFragement;
    private String label;
    private long starttime;
    Stack<String> mystack;
    private int type;
    public QueryTouchListener(MainFragement mainFragement,String label){
        this.mainFragement=mainFragement;
        this.label=label;
        mystack=mainFragement.getTouchList();
        this.type=0;
    }
    public QueryTouchListener(MainFragement mainFragement,String label,int type){
        this.mainFragement=mainFragement;
        this.label=label;
        mystack=mainFragement.getTouchList();
        this.type=type;
    }
    public void setLabel(String label){
        this.label=label;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //System.out.println("begin touch");
        int action = MotionEventCompat.getActionMasked(event);
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
                startx=Math.abs(startx);
                starty=Math.abs(starty);
                endx=Math.abs(endx);
                endy=Math.abs(endy);
                //System.out.println(String.valueOf(startx)+" "+String.valueOf(starty)+" "+String.valueOf(endx)+" "+String.valueOf(endy));
                if(Math.abs(startx-endx)>Math.abs(starty-endy)&&Math.abs(startx-endx)>100&&endx!=0&&endy!=0){
                    //System.out.println("swipe left");
                    if(startx>endx){
                        //System.out.println("swipe left"+String.valueOf(startx)+" "+String.valueOf(endx));
                        mainFragement.DealTouchAction(v,label,1);

                        //MainFragement.DealTouchAction(v,1);
                    }else{
                        //System.out.println("swipe right"+String.valueOf(startx)+" "+ String.valueOf(endx));
                        mainFragement.DealTouchAction(v,label,2);
                    }
                }else if(startx!=0&&starty!=0&&(int)endy==0&&(int)endx==0){
                    long endtime=System.currentTimeMillis();
                    System.out.println(endtime-starttime);
                    long interval=(endtime-starttime)/1000;
                    Button entity = (Button) v;
                    String tail = entity.getText().toString();
                    //System.out.println("begin the long touch event");
                    if (label.equals("subject")) {
                        tail = "category:" + tail;
                    }
                    //System.out.println("paht explore now!");
                    // mainFragement.dealRelationExplore("forrest gump","tom hanks");
                    if(interval>=1&&interval<2) {
                        //System.out.println("begin explore the path");
                        String mytail = tail;
                        mystack.add(tail);
                        if(mystack.size()>1){
                            String head=mystack.peek();
                            if(!head.equals(tail)){
                                mainFragement.dealRelationExplore(head,tail);
                            }
                            while (mystack.size()>0){
                                mystack.pop();
                            }
                        }
                    }
                    else if(interval>=2) {
                        mainFragement.dealLongPressAction(tail,1,null,mainFragement.getContext());
                        while (mystack.size()>0){
                            mystack.pop();
                        }
                    }
                }
                break;
        }
        return  true;
    }
}
