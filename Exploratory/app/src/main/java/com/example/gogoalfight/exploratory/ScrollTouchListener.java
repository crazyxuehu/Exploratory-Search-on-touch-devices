package com.example.gogoalfight.exploratory;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.example.gogoalfight.exploratory.service.beginSearch;

/**
 * Created by GoGoalFight on 2017/11/3.
 */

public class ScrollTouchListener implements View.OnTouchListener {
    private int scrollY;
    private int height;
    private int scrollViewMeasuredHeight;
    private float y1;
    private float y2;
    private ScrollView myScrollView;
    private MainFragement mainFragement;
    private ScrollView negScrollView;
    private beginSearch mysearch;
    private int pointers;
    public ScrollTouchListener(ScrollView myScrollView, ScrollView negScrollView, MainFragement mainFragement) {
        this.myScrollView = myScrollView;
        this.negScrollView = negScrollView;
        mysearch = new beginSearch();
        this.mainFragement = mainFragement;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointers=motionEvent.getPointerCount();
                /*Toast.makeText(mainFragement.getContext(), "down pointers:" + pointers,
                        Toast.LENGTH_SHORT).show();*/
                // System.out.println("触发滑动");
                y1 = motionEvent.getY();
                //System.out.println(String.valueOf(y1));
            case MotionEvent.ACTION_MOVE:
                //System.out.println("开始滑动");
                pointers=motionEvent.getPointerCount();
               /* Toast.makeText(mainFragement.getContext(), "move pointers:" + pointers,
                        Toast.LENGTH_SHORT).show();*/
                y2 = motionEvent.getY();
                // System.out.println(String.valueOf(y2));
                break;
            case MotionEvent.ACTION_UP:
                scrollY = view.getScrollY();
                height = view.getHeight();
                scrollViewMeasuredHeight = myScrollView.getChildAt(0).getMeasuredHeight();
                //System.out.println("停止滑动："+String.valueOf(y2));
                if (pointers>=2&&Math.abs(y1) - Math.abs(y2) > 180 && scrollY == 0) {
                    mysearch.search(myScrollView, negScrollView, mainFragement);
                    //System.out.println("滑动到了底部");
                } else if (pointers>=2&&(scrollY + height) == scrollViewMeasuredHeight) {
                    mysearch.search(myScrollView, negScrollView, mainFragement);
                }
                break;
            default:
                break;
        }
        return true;
    }
}
