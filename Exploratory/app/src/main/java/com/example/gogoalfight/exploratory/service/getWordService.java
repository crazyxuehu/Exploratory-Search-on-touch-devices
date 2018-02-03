package com.example.gogoalfight.exploratory.service;

import android.content.Context;
import android.widget.AutoCompleteTextView;

/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class getWordService {
    private Context mycontext;
    private AutoCompleteTextView myview;
    public getWordService(Context mycontext,AutoCompleteTextView myview){
        this.mycontext=mycontext;
        this.myview=myview;
    }
    public void getWord(String text){
            String query="test?query="+text;
            createConnection.getAutoCompleteWord(query,mycontext,myview);
    }
}
