package com.example.gogoalfight.exploratory;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.gogoalfight.exploratory.model.FeatureButtonModel;
import com.example.gogoalfight.exploratory.model.FeedBackModel;
import com.example.gogoalfight.exploratory.model.GlobalData;
import com.example.gogoalfight.exploratory.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    final  private int Request_Code=1011;
    final private int Result_Ok=2011;
    private int QURYE_NUM=1;
    private int FLING_MIN_DISTANCE=10;
    private int FLING_MIN_VELOCITY=0;
    private  GestureDetector mGestureDetector;
    private List<String> HistoryQuery;
    private List<String> HistoryResult;
    private List<String> HistoryFeedback;
    private Spinner querySpiner;
    private Spinner resultSpiner;
    private Spinner feedbkSpiner;
    private int SpinnerSize=100;
    private  static int mainPage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManger=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
        HistoryFragment queryFragment=(HistoryFragment) fragmentManger.findFragmentById(R.id.queryfragment);
        MainFragement mainFragment=(MainFragement) fragmentManger.findFragmentById(R.id.mainfragment);
        fragmentTransaction.hide(queryFragment);
        //mainFragment.initial(this,QURYE_NUM);
        fragmentTransaction.commit();
        // LinearLayout layout = (LinearLayout)findViewById(R.id.testtouch);
        initialSpinner();
        Button btn=(Button) findViewById(R.id.currentSearch);
        btn.setOnTouchListener(new touchListener(mainFragment,3));
        /*MyScrollView myScrollView=(MyScrollView) findViewById(R.id.scrollview2);
        myScrollView.setMainFragement(mainFragment);*/
        if(mainPage==0)
        goBack();
       // mGestureDetector=new GestureDetector(this,myGestureListener);

       /* layout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("touch now");
                return mGestureDetector.onTouchEvent(event);
            }
        });*/
        //layout.setOnTouchListener(this);
       // layout.setLongClickable(true);
    }
   /* @Override
    public boolean onTouchEvent( MotionEvent event) {
       // Log.i(null, "OnTouchListener--onTouch-- action="+event.getAction()+" --"+v);
        System.out.println("catch the touch event");
        return mGestureDetector.onTouchEvent(event);
    }*/

   public void intialSearchRound(){
       //QURYE_NUM=1;
       FragmentManager fragmentManger=getFragmentManager();
       MainFragement mainFragment=(MainFragement) fragmentManger.findFragmentById(R.id.mainfragment);
       QURYE_NUM=1;
       mainFragment.initial(this,QURYE_NUM);
       initialSpinner();
   }
   public void initialSpinner(){
       //HistoryQuery=new ArrayList<String>();
       if(HistoryResult==null)
       HistoryResult=new ArrayList<String>();
       HistoryResult.clear();
       //HistoryFeedback=new ArrayList<String>();
       //HistoryQuery.add("History Query");
       HistoryResult.add("History Search");
       //HistoryFeedback.add("History Feedback");
       updateSpinner(0);
   }
   public void updateSpinner(int num){
       if(num!=0) {
           //HistoryQuery.add("Query " + String.valueOf(num));
           HistoryResult.add("Search Result " + String.valueOf(num) + " ");
           //HistoryFeedback.add("Feedback " + String.valueOf(num));
       }
       //querySpiner=(Spinner)findViewById(R.id.historyQuery);
       resultSpiner=(Spinner)findViewById(R.id.historyResult);
       //feedbkSpiner=(Spinner)findViewById(R.id.historyFeedback);
       //setSpinner(this,querySpiner,HistoryQuery.toArray(new String[HistoryQuery.size()]));
       setSpinner(this,resultSpiner,HistoryResult.toArray(new String[HistoryResult.size()]));
       //setSpinner(this,feedbkSpiner,HistoryFeedback.toArray(new String[HistoryFeedback.size()]));
   }
   public void setCurrentSpinner(MainFragement mainFragement,int num){
       Spinner spinner=(Spinner) mainFragement.getActivity().findViewById(R.id.historyResult);
       spinner.setSelection(num);
   }
   public  void setSpinner(Context ui, Spinner sp, final String[]list){
       ArrayAdapter<String> adapter=new ArrayAdapter<String>(ui,android.R.layout.simple_spinner_item,list);
       sp.setAdapter(adapter);
       sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               //System.out.println(list[position]);
               //querySpiner.setSelection(position);
               resultSpiner.setSelection(position);
               //feedbkSpiner.setSelection(position);
                if(list[position].equals("History Query")||list[position].equals("History Search")||list[position].equals("History Feedback")){
                    hideFragment();
                }else{
                    createFragment(null,list[position],2);
                }
           }
           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
   }
    public void createFragment(String data,String query,int type){
        String[]result=query.split(" ");
        final MainFragement mainFragment = (MainFragement) getFragmentManager().findFragmentById(R.id.mainfragment);
        int queryNum=mainFragment.getQueryNum();
        ScrollView myscrollview=(ScrollView) mainFragment.getActivity().findViewById(R.id.positiveInput);
        ScrollView negscrollview=(ScrollView)mainFragment.getActivity().findViewById(R.id.negativeInput);
        myscrollview.setOnTouchListener(new ScrollTouchListener(myscrollview,negscrollview,mainFragment));
        negscrollview.setOnTouchListener(new ScrollTouchListener(myscrollview,negscrollview,mainFragment));
        QURYE_NUM=mainFragment.getQueryNum();
        //System.out.println("querynum:"+String.valueOf(QURYE_NUM));

        if(data!=null){
            //System.out.println("123");
            String[]querylist=data.split(",\\s");
            List<FeedBackModel>myQueryList=new ArrayList<FeedBackModel>();
            for(int i=0;i<querylist.length;i++){
                //System.out.println("queryList:"+querylist[i]);
                mainFragment.addQuerySet(querylist[i]);
                myQueryList.add(new FeedBackModel(new FeatureButtonModel(1,null,querylist[i])));
            }
            mainFragment.setQueryList(QURYE_NUM,myQueryList);
        }
        /*System.out.println("QueryList:"+mainFragment.getQueryList(queryNum).get(0).getFeature().getEntity());*/
        if (result.length>1){

            queryNum=Integer.parseInt(result[result.length-1]);
            System.out.println("Query_num:"+String.valueOf(QURYE_NUM)+" query_Num:"+String.valueOf(queryNum));
            if(queryNum==QURYE_NUM) type=3;
        }
        final FragmentManager fragmentManger=getFragmentManager();
        final FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
        if(type==2) {
            HistoryFragment subFragment = (HistoryFragment) getFragmentManager().findFragmentById(R.id.queryfragment);
            mainFragment.changeData(1);
            subFragment.changeData(this, queryNum);
            fragmentTransaction.show(subFragment);
            fragmentTransaction.show(mainFragment);
            fragmentTransaction.commit();
        }else if(type==1){
            //MainFragement mainFragment = (MainFragement) getFragmentManager().findFragmentById(R.id.mainfragment);
            //System.out.println("Entity:"+data);
            updateSpinner(QURYE_NUM);
            if(mainFragment.getHistoryList()==null){
                mainFragment.setHistoryList(new HashMap<Integer, Result>());
            }else{
                mainFragment.getHistoryList().clear();
            }
            HistoryFragment subFragment = (HistoryFragment) getFragmentManager().findFragmentById(R.id.queryfragment);
            //mainFragment.addQuery(data,null);
            //mainFragment.addFeedBack(null,1);
            mainFragment.changeData();
            fragmentTransaction.hide(subFragment);
            fragmentTransaction.show(mainFragment);
            fragmentTransaction.commit();
        }else{
            HistoryFragment subFragment = (HistoryFragment) getFragmentManager().findFragmentById(R.id.queryfragment);
            mainFragment.changeData();
            fragmentTransaction.hide(subFragment);
            fragmentTransaction.commit();
        }
    }
    public void hideFragment(){
        FragmentManager fragmentManger=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
        Fragment subFragment=getFragmentManager().findFragmentById(R.id.queryfragment);
        fragmentTransaction.hide(subFragment);
        fragmentTransaction.commit();
    }
    public  void goBack(View view){
        //System.out.println("hello");
        startActivityForResult(new Intent(this,SearchActivity.class),Request_Code);
        GlobalData data=(GlobalData) getApplicationContext();
    }
    public void goBack(){
        mainPage=1;
        startActivityForResult(new Intent(this,SearchActivity.class),Request_Code);
        GlobalData data=(GlobalData) getApplicationContext();
    }
    /*public GestureDetector.SimpleOnGestureListener myGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDown(MotionEvent event) {
            //Log.d(DEBUG_TAG,"onDown: " + event.toString());
            System.out.println("start touch");
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            System.out.println("swipe test");
            Log.e("<--滑动测试-->", "开始滑动");
            float x = e1.getX()-e2.getX();
            float x2 = e2.getX()-e1.getX();
            if(x>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
                //Toast.makeText(MainActivity.this, "向左手势", Toast.LENGTH_SHORT).show();
                System.out.println("swipe left");
                DealTouchAction(1);
                //startActivity(new Intent(MainActivity.this,MainActivity.class));
               // System.out.println();
                return true;

            }else if(x2>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
                //Toast.makeText(MainActivity.this, "向右手势", Toast.LENGTH_SHORT).show();
                System.out.println("swipe right");
                DealTouchAction(0);
                return true;
            }

            return false;
        };
    };*/

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==Request_Code){
            if(resultCode==Result_Ok){
                intialSearchRound();
                //QURYE_NUM++;
                //updateSpinner(QURYE_NUM);
                createFragment(data.getData().toString(),"query 0",1);
            }
        }
    }
    public int getQueryNum(){
        return QURYE_NUM;
    }
    public Context getContext(){
        return this;
    }
   /*public void createQueryComponent(String text){
       QURYE_NUM++;
       LinearLayout parentLayout=(LinearLayout)findViewById(R.id.Query) ;
       LinearLayout layout= CreateUIComponent.createLinearLayout(this,LinearLayout.HORIZONTAL);
       Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,this);
       QueryButton querybtn=CreateUIComponent.CreateQueryButton(text,this);
       parentLayout.addView(btntitle);
       layout.addView(querybtn);
       parentLayout.addView(layout);
   }
   public void createFeatureComponent(String label,String entity){
        LinearLayout parentLayout=(LinearLayout)findViewById(R.id.featureLayout1);
       featureButton btn=CreateUIComponent.createFeatureButton(this,label,entity);
       parentLayout.addView(btn);
   }
    public void createFeedBackComponent(String label,String entity){
        LinearLayout parentLayout=(LinearLayout)findViewById(R.id.feadback) ;
        Button btntitle=CreateUIComponent.CreateQuery(QURYE_NUM,this);
        FeedBack fbk=CreateUIComponent.createFeedBack(this,label,entity);
       // System.out.println("fuck"+fbk.getBtn().getLabel().getText());
        parentLayout.addView(btntitle);
        parentLayout.addView(fbk);
    }
    public void createSearchResult(){
        LinearLayout parentLayout=(LinearLayout)findViewById(R.id.search_result);
        Button resultbtn=CreateUIComponent.CreateResultBtn(QURYE_NUM,this);
        HorizontalScrollView imageList=CreateUIComponent.createHorizontalScrollView(this);
        LinearLayout imgLayout=CreateUIComponent.createLinearLayout(this,LinearLayout.HORIZONTAL);
        for(int i=0;i<5;i++){
            ImageNode myimage=new ImageNode(this,null);
            myimage.setEntityImage(R.drawable.img1);
            myimage.setEntityName("noodles");
            imgLayout.addView(myimage);
        }
        imageList.addView(imgLayout);
        parentLayout.addView(resultbtn);
        parentLayout.addView(imageList);
        LinearLayout EntityLayout=CreateUIComponent.createLinearLayout(this,LinearLayout.HORIZONTAL);
        LinearLayout leftLayout=CreateUIComponent.createLinearLayout(this,LinearLayout.VERTICAL,5);
        LinearLayout rightLayout=CreateUIComponent.createLinearLayout(this,LinearLayout.VERTICAL,5);
        for(int i=0;i<4;i++){
            featureButton leftBtn=CreateUIComponent.createFeatureButton(this,"Relation","Entity");
            leftLayout.addView(leftBtn);
            featureButton rightBtn=CreateUIComponent.createFeatureButton(this,"Relation","Entity");
            rightLayout.addView(rightBtn);
        }
        EntityLayout.addView(leftLayout);
        EntityLayout.addView(rightLayout);
        parentLayout.addView(EntityLayout);
    }*/
    /**
    public void CreateQuery(String[]list,){

    }
    public Button createQueryButton(String text,int id){
        /**
        int width=90;
        int height=30;
        int left=5;
        int top=10;
        int widvalue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                width, getResources().getDisplayMetrics());
        int heightvalue=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                height, getResources().getDisplayMetrics());
        int margin_left=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                left, getResources().getDisplayMetrics());
        int margin_top=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                top, getResources().getDisplayMetrics());
        Button btn=new Button(this);
         **//**
        Button btn=new Button(this);
        Button sampleBtn=(Button)findViewById(R.id.queryButton);
        ViewGroup.LayoutParams params=sampleBtn.getLayoutParams();
        btn.setLayoutParams(params);
        btn.setBackground(getDrawable(R.drawable.corner));
        btn.setText(text);
        return btn;
    }*/

}
