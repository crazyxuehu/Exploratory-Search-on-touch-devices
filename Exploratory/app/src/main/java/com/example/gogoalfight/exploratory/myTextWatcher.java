package com.example.gogoalfight.exploratory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

import com.example.gogoalfight.exploratory.service.getWordService;
/**
 * Created by GoGoalFight on 2017/10/27.
 */

public class myTextWatcher implements TextWatcher{
    private Context mycontext;
    private AutoCompleteTextView myView;
    public myTextWatcher(Context mycontext,AutoCompleteTextView myView){
        this.mycontext=mycontext;
        this.myView=myView;
    }
    public void afterTextChanged(Editable editable) {
        // TODO Auto-generated method stub

    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        String newText = s.toString();
        String[] parts = newText.split(",");
        if(parts[parts.length -1].length() > 3){
            new getWordService(mycontext,myView).getWord( parts[parts.length -1] );
        }
    }
}
