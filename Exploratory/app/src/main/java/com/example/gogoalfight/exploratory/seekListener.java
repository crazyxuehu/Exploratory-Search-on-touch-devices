package com.example.gogoalfight.exploratory;

import android.widget.SeekBar;

/**
 * Created by GoGoalFight on 2017/10/25.
 */

public class seekListener implements SeekBar.OnSeekBarChangeListener {
    private String entityName;
    public seekListener(String entityName){
        this.entityName=entityName;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser){

    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar){

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar){
            int value=seekBar.getProgress();
            System.out.println(value);
    }
}
