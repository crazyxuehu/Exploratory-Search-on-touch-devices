package com.example.gogoalfight.exploratory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;

import com.example.gogoalfight.exploratory.model.GlobalData;
import com.example.gogoalfight.exploratory.service.beginSearch;

/**
 * Created by GoGoalFight on 2017/10/8.
 */

public class SearchActivity extends AppCompatActivity {
    private int Result_OK=2011;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Autocomplete
       /* ArrayAdapter<String> Entity = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, EntityList);*/
        MultiAutoCompleteTextView autocomp= (MultiAutoCompleteTextView)findViewById(R.id.searchtext);
        // 设置Adapter
        //autocomp.setAdapter(Entity);
        // 为MultiAutoCompleteTextView设置分隔符
        autocomp.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autocomp.addTextChangedListener(new myTextWatcher(this,autocomp));
        GlobalData mydata=(GlobalData) getApplicationContext();
        mydata.initail();
    }
    public void setCount(int count){
        this.count=count;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                System.out.println("center is clicked");
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                System.out.println("left was clicked");
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                System.out.println("right was clicked");
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                System.out.println("up was clicked");
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                System.out.println("down was clicked");
                break;
            case KeyEvent.KEYCODE_BACK:
                System.out.println("back was clicked");
                startActivity(new Intent(this,MainActivity.class));
        }
        return false;
    }
    public void dealsearch(){
        final Intent data=new Intent();
        MultiAutoCompleteTextView text=(MultiAutoCompleteTextView)findViewById(R.id.searchtext);
        data.setData(Uri.parse(text.getText().toString()));
        setResult(Result_OK, data);
        finish();
    }
    public void goSearch(View view){
        /*WebView webView=new WebView(this);
        webView.loadUrl("http://www.baidu.com");
        webView.onResume();*/
        final Intent data=new Intent();
        MultiAutoCompleteTextView text=(MultiAutoCompleteTextView)findViewById(R.id.searchtext);
        final GlobalData mydata=(GlobalData)getApplicationContext();
        this.count=0;
        //System.out.println("String:"+text.getText().toString());
        new beginSearch(text.getText().toString(),mydata,this).search(this);
        data.setData(Uri.parse(text.getText().toString()));
        //final Context mycontext=this;
        /*final Runnable mytest=new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mycontext,"datacount:"+mydata.getCount(),Toast.LENGTH_SHORT).show();
            }
        };*/
      //final Handler myHandler=new Handler();
     // final ProgressDialog dialog=ProgressDialog.show(this,"","Loading data...",true);
      /*final  SearchActivity searchActivity=this;
      new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Toast.makeText(this,"simentity:"+searchActivity.count,Toast.LENGTH_SHORT);
                    if (searchActivity.count == 10) {
                      // dialog.dismiss();
                        setResult(Result_OK, data);
                        finish();
                        break;
                    }
                }
                return;
            }
        }).start();*/
    }
}