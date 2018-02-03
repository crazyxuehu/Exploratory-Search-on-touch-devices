package com.example.gogoalfight.exploratory;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gogoalfight.exploratory.model.FeatureButtonModel;

import java.util.List;

/**
 * Created by GoGoalFight on 2017/10/26.
 */

public class InfoFragment extends  DialogFragment {
    static InfoFragment newInstance() {
        return new InfoFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment, container, false);
      /*  WebView myview=(WebView) v.findViewById(R.id.mywebview);
        // 开启javascript 渲染
        myview.getSettings().setJavaScriptEnabled(true);
        myview.setWebViewClient(new WebViewClient());
        myview.addJavascriptInterface(new ExchangeDataForJs(this.getContext(),new JSONObject()), "JavaScriptInterface");
        myview.loadUrl("file:///android_asset/path.html");*/
        //System.out.println("begin create View");
        int EntityButtom=20;
        ImageView myImage=(ImageView) v.findViewById(R.id.imageView);
        MainFragement mainFragement=(MainFragement) getFragmentManager().findFragmentById(R.id.mainfragment);
        String imgUrl=mainFragement.getImageUrl();
        CreateUIComponent.loadImage(this.getContext(),myImage,imgUrl,mainFragement);
        String desc=mainFragement.getDescription();
        List<FeatureButtonModel> featurelist=mainFragement.getPressFeature();
        Context myContext=this.getContext();
        TextView myText=(TextView)v.findViewById(R.id.description);
        myText.setText(desc);
        LinearLayout layout=(LinearLayout)v.findViewById(R.id.dialog_feature);
        int labelWidth=120;
        int entityWidth=180;
        for(int i=0;i<featurelist.size();i++){
            int type=1;
            String label=featurelist.get(i).getLabel();
            String entity=featurelist.get(i).getEntity();
            if(label.equals("subject")){
                type=2;
                String[] myEntity = entity.split(":");
                if(myEntity.length==1){
                    entity=myEntity[0];
                }else{
                    entity = myEntity[1];
                }
            }
            featureButton fbk=CreateUIComponent.createFeatureButton(myContext,label,entity,type,labelWidth,entityWidth,EntityButtom);
            fbk.setOnTouchListener(new touchListener(mainFragement,label,type));
            layout.addView(fbk);
        }
        //System.out.println("finish work");
        return v;
    }
}
