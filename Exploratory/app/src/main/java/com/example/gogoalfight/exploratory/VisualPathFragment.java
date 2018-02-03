package com.example.gogoalfight.exploratory;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.gogoalfight.exploratory.service.ExchangeDataForJs;

/**
 * Created by GoGoalFight on 2017/11/12.
 */

public class VisualPathFragment extends DialogFragment {
    static VisualPathFragment newInstance() {
        return new VisualPathFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.visualpath, container, false);
        MainFragement mainFragement=(MainFragement) getFragmentManager().findFragmentById(R.id.mainfragment);
        WebView myview = (WebView) v.findViewById(R.id.mywebview);
        myview.setWebViewClient(new WebViewClient());
        // 开启javascript 渲染
        myview.getSettings().setJavaScriptEnabled(true);

        /*JSONObject data=null;
        JSONObject links=null;
       try{
            data=(JSONObject) mainFragement.getPath().get("vertexList");
            links=(JSONObject)mainFragement.getPath().get("edgeList");
        }catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(links);*/
        myview.addJavascriptInterface(new ExchangeDataForJs(this.getContext(),mainFragement.getPath()),"JavaScriptInterface");
        myview.loadUrl("file:///android_asset/path.html");
        mainFragement.clearPath();
        return  v;
    }
}