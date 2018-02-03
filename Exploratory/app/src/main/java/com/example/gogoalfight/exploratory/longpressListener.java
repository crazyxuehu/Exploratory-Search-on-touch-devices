package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.view.View;

import com.example.gogoalfight.exploratory.model.GlobalData;

/**
 * Created by GoGoalFight on 2017/10/25.
 */

public class longpressListener implements View.OnLongClickListener {
    private String entityName;
    private MainFragement mainFragement;
    private final  int MOVIE_TYPE=1;
    private Context myContext;
    public longpressListener(String entityName,MainFragement mainFragement,Context myContext){
        this.entityName=entityName;
        this.mainFragement=mainFragement;
        this.myContext=myContext;
    }
    @Override
    public boolean onLongClick(View v){
        GlobalData data=(GlobalData) mainFragement.getActivity().getApplicationContext();
        mainFragement.dealLongPressAction(entityName,MOVIE_TYPE,data.getEntityImage(entityName),myContext);
        return true;
    }
}
