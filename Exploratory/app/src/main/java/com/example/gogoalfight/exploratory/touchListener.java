package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.gogoalfight.exploratory.model.GlobalData;

/**
 * Created by GoGoalFight on 2017/10/23.
 */

public class touchListener implements View.OnTouchListener{
    private float startx=0;
    private float starty=0;
    private float endx=0;
    private  float endy=0;
    private MainFragement mainFragement;
    private String label;
    private long starttime;
    private int type;
    private Context context;
    public touchListener(MainFragement mainFragement,String label){
        this.mainFragement=mainFragement;
        this.label=label;
        this.type=0;
    }
    public touchListener(MainFragement mainFragement,String label,Context context){
        this.mainFragement=mainFragement;
        this.label=label;
        this.type=0;
        this.context=context;
    }
    public touchListener(MainFragement mainFragement,String label,int type){
        this.mainFragement=mainFragement;
        this.label=label;
        this.type=type;
    }
    public touchListener(MainFragement mainFragement, String label, int type, Context context){
        this.mainFragement=mainFragement;
        this.label=label;
        this.type=type;
        this.context=context;
    }
    public touchListener(MainFragement mainFragement,int type){
        //type:3 change to current search
        this.mainFragement=mainFragement;
        this.type=type;
    }
    public void setLabel(String label){
        this.label=label;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //System.out.println("begin touch 1111");
        int action = MotionEventCompat.getActionMasked(event);
        int pointers=0;
       /* if(type==4)
            pointers=mainFragement.getTouchHPointers();
        else
            pointers=mainFragement.getTouchVPointers();
        //System.out.println("pointers"+pointers);*/
        switch(action) {
            case (MotionEvent.ACTION_DOWN):
                startx=event.getX();
                starty=event.getY();
                //System.out.println("begin touch"+String.valueOf(startx));
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
                //System.out.println("action stop");
                startx=Math.abs(startx);
                starty=Math.abs(starty);
                endx=Math.abs(endx);
                endy=Math.abs(endy);
                //System.out.println("startx:"+startx+"starty:"+starty+"endx:"+endx+"endy:"+endy);
                //long endtime=System.currentTimeMillis();
                //System.out.println(endtime-starttime);
                //long interval=(endtime-starttime)/1000;
                //Toast.makeText(mainFragement.getContext(),"type:"+type,Toast.LENGTH_SHORT);
                /*Toast.makeText(mainFragement.getContext(),"interval:"+String.valueOf(interval)+"StartX:"+String.valueOf(startx)
                                +"StartY:"+String.valueOf(starty)+"endx:"+String.valueOf(endx)+"endy:"+String.valueOf(endy),
                        Toast.LENGTH_SHORT).show();*/
                if(Math.abs(startx-endx)<Math.abs(starty-endy)&&Math.abs(starty-endy)>5&&endy!=0&&type==3){// type==3:deal with the current search
                    mainFragement.dealScollup();
                }
                if(type==4&&Math.abs(startx-endx)>Math.abs(starty-endy)&&Math.abs(startx-endx)>50&&endx!=0){//type==4: deal with the similar entity left Or right
                    if(startx>endx){
                        mainFragement.DealTouchAction(v,label,5);
                    }else{
                        mainFragement.DealTouchAction(v,label,6);
                    }
                   /* mainFragement.setTouchVPointers(0);
                    mainFragement.setTouchHPointers(0);*/
                }
                if(Math.abs(startx-endx)>Math.abs(starty-endy)&&Math.abs(startx-endx)>100&&endx!=0&&endy!=0&&type!=4) {//deal with the similar feature left or right
                    //System.out.println("swipe left");
                    if (startx > endx) {
                        //System.out.println("swipe left"+String.valueOf(startx)+" "+String.valueOf(endx));
                        if (this.type == 0) {//type =0 , swipe left or right else remove
                            mainFragement.DealTouchAction(v, label, 1);
                        } else {
                            mainFragement.DealTouchAction(v, label, 3);
                        }
                    } else {
                        //System.out.println("swipe right"+String.valueOf(startx)+" "+ String.valueOf(endx));
                        if (this.type == 0) {
                            mainFragement.DealTouchAction(v, label, 2);
                        } else {
                            mainFragement.DealTouchAction(v, label, 4);
                        }
                    }
                   /* mainFragement.setTouchVPointers(0);
                    mainFragement.setTouchHPointers(0);*/
                }else if(Math.abs(startx-endx)<3&&Math.abs(starty-endy)<3&&type!=3&&type!=1){
                    //System.out.println(type);
                //}else {

                    //mainFragement.dealRelationExplore(testHead, testTail);

                    //endtime=System.currentTimeMillis();
                    //System.out.println(endtime-starttime);
                    //interval=(endtime-starttime)/1000;
                    String mytail=null;
                    if(type!=4) {
                        Button entity = (Button) v;
                        mytail = entity.getText().toString();
                    }else if(type==4){
                        mytail=label;
                    }
                   /* Toast.makeText(mainFragement.getContext(),"xdistance:"+String.valueOf(startx-endx)+"ydistance:"+String.valueOf(starty-endy),Toast.LENGTH_SHORT).show();*/
                    //System.out.println("begin the long touch event");
                    if (label!=null&&(label.equals("subject")||label.equals("Subject"))) {
                        mytail = "Category:" + mytail;
                    }
                    final String tail=mytail;
                    //System.out.println("paht explore now!");
                    //mainFragement.dealRelationExplore("forrest gump","tom hanks");
                    /*Toast.makeText(mainFragement.getContext(),"pointers"+pointers,
                            Toast.LENGTH_SHORT).show();*/
                        //System.out.println("begin explore the path");
                       /* Toast.makeText(mainFragement.getContext(),"tail:"+tail+"stacksize:"+String.valueOf(mainFragement.touchList.size()),
                                Toast.LENGTH_SHORT).show();*/
                        //String mytail = tail;
                    /*Toast.makeText(mainFragement.getContext(),"tail:"+tail+"stacksize:"+String.valueOf(mainFragement.touchList.size()),
                            Toast.LENGTH_SHORT).show();*/
                       /* if(mainFragement.touchFlag){
                            try{
                                Thread.sleep(50);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {*/
                           // mainFragement.touchFlag=true;
                    final Handler myHandler=new Handler();
                    final Runnable longPress=new Runnable() {
                        @Override
                        public void run() {
                            mainFragement.dealLongPressAction(tail, 1, null, mainFragement.getContext());
                        }
                    };
                    final Runnable relationExplore=new Runnable() {
                        @Override
                        public void run() {
                            GlobalData mydata=(GlobalData) context.getApplicationContext();
                            String head = mydata.getTouchList().peek();
                            /*Toast.makeText(mainFragement.getContext(),"head:"+head+"tail:"+tail+"stacksize:"+String.valueOf(mydata.getTouchList().size()),
                                    Toast.LENGTH_SHORT).show();*/
                            //mydata.popTouchEntity();
                           while (mydata.getTouchSize() > 0) {
                                mydata.popTouchEntity();
                            }
                           /* String testHead="forrest gump";
                            String testTail="tom hanks";*/
                            if (!head.equals(tail)) {
                                mainFragement.dealRelationExplore(head, tail);
                            }
                        }
                    };
                        new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GlobalData mydata=(GlobalData) context.getApplicationContext();
                            //System.out.println("begin touch add ......"+mydata.getTouchSize());
                            if (mydata.getTouchSize() == 0) {
                                /*Stack<String>list=mainFragement.getTouchList();
                                list.add(tail);*/
                                mydata.addTouchEntity(tail);
                                //System.out.println("touch finished ......"+mydata.getTouchSize());
                                // mainFragement.touchFlag=false;
                                try {
                                    Thread.sleep(100);
                                    //System.out.println("lalalalalalala");
                                    if (mydata.getTouchSize() == 1) {
                                        while (mydata.getTouchSize() > 0) {
                                            mydata.popTouchEntity();
                                        }
                                        myHandler.post(longPress);
                                    } else {
                                        while (mydata.getTouchSize()> 0) {
                                            mydata.popTouchEntity();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println(e.getMessage());
                                }

                            } else {
                                // mainFragement.touchFlag=false;
                                myHandler.post(relationExplore);
                                //myHandler.post(longPress);
                            }
                        }
                    }).start();

                }

                       /* Toast.makeText(mainFragement.getContext(),"tail:"+tail+"stacksize:"+String.valueOf(mainFragement.touchList.size()),
                                Toast.LENGTH_SHORT).show();*/
                        /*if(mainFragement.touchList.size()>1){
                            String head=mainFragement.touchList.peek();
                            mainFragement.touchList.pop();
                            tail=mainFragement.touchList.peek();
                            Toast.makeText(mainFragement.getContext(),"head:"+head+"tail:"+tail+"stacksize:"+String.valueOf(mainFragement.touchList.size()),
                                    Toast.LENGTH_SHORT).show();
                            if(!head.equals(tail)){
                                *//*Toast.makeText(mainFragement.getContext(),"ok",
                                        Toast.LENGTH_SHORT).show();*//*
                                mainFragement.dealRelationExplore(head,tail);
                            }
                            while (mainFragement.touchList.size()>0){
                                mainFragement.touchList.pop();
                            }
                            */
                          /*  if(type==4)
                                mainFragement.setTouchHPointers(0);
                            else
                                mainFragement.setTouchVPointers(0);
                        }*/
                        //if(mainFragement.touchList.size()==0) {

                            /*if(type==4)
                                mainFragement.setTouchHPointers(0);
                            else
                                mainFragement.setTouchVPointers(0);*/
                       // }
                        /*while (mainFragement.touchList.size()>0){
                            mainFragement.touchList.pop();
                        }*/


                //mainFragement.setTouchPointers(0);
                break;
        }
        return  true;
    }
}
