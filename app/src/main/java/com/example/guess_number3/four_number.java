package com.example.guess_number3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class four_number extends AppCompatActivity {
    boolean winner=true;
    private TextView textView[]=new TextView[4];
    private int intres[]={R.id.num1,R.id.num2,R.id.num3,R.id.num4};
    private Button btn[]=new Button[10];
    private int intbtn[]={R.id.number_0,R.id.number_1,R.id.number_2,R.id.number_3
            ,R.id.number_4,R.id.number_5,R.id.number_6,R.id.number_7,R.id.number_8,R.id.number_9};
    private int Degree_of_difficulty/*難易度*/,win_num,lose_num;
    /////////////////////////////////////////
    private ListView listview;
    private SimpleAdapter adapter;
    private LinkedList<HashMap<String,String>> list;
    private String[] from={"title","Entered_answer","How_many_A_and_B"};
    private int[] to={R.id.titletext,R.id.Entered_answer,R.id.How_many_A_and_B};
    ///////////////////////////////////////////////////////////////////////
    private LinkedList<Integer> answer;//謎底
    private LinkedList<Integer> intputanswer=new LinkedList<>();//使用者輸入的答案
    private int record_brn_times=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_number);
        InitView();//初始化view
        InitListView();//初始化listview 即設定adapter
        InitGame();//初始化遊戲
        win_num=lose_num=0;
    }
    public void InitView(){
        for(int i=0;i<intres.length;i++){
            textView[i]=findViewById(intres[i]);
        }
        for(int i=0;i<intbtn.length;i++){
            btn[i]=findViewById(intbtn[i]);
        }
    }
    public void InitGame(){
        winner=true;
        create_a_puzzle();//創造謎底
        BTN_clear(null);
        Degree_of_difficulty=getIntent().getIntExtra("Degree_of_difficulty",-1);
        list.clear();
        adapter.notifyDataSetChanged();
    }

    public void InitListView(){
        listview=findViewById(R.id.listview);
        list=new LinkedList<>();
        adapter=new SimpleAdapter(this,list,R.layout.four_number_listview,from,to);
        listview.setAdapter(adapter);
    }
    public void BTN_clear(View view) {
    for(int i=0;i<btn.length;i++){
        btn[i].setEnabled(true);
    }
        intputanswer.clear();
    for(int i=0;i<4;i++){
        intputanswer.add(-1);////讓linkedlist裡面的size不為0
    }
    for(int i=0;i<textView.length;i++){
        textView[i].setText("");
    }
    record_brn_times=0;
    }
    public void btn_delete(View view) {
        if(record_brn_times==0){return;}//判斷textview.text是否為0如果是 退掉
        record_brn_times--;
        btn[intputanswer.get(record_brn_times)].setEnabled(true);
        intputanswer.set(record_brn_times,-1);
        textView[record_brn_times].setText("");
    }

    public void btn_four_num(View view) {
        if(record_brn_times == 4){return;}//當輸入的數字為4的時候 就不能在輸入
        for(int i=0;i<btn.length;i++) {
            if(btn[i]==view) {
             intputanswer.set(record_brn_times,i);
              textView[record_brn_times].setText(i+"");
              record_brn_times++;
              btn[i].setEnabled(false);
              break;
           }
         }
    }

    public void btn_back(View view) {
        if(winner==false){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("輸了就想跑????????");
            builder.setPositiveButton("我就爛,我是菜B8", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNeutralButton("在...在一次", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    btn_replay(null);
                }
            });
            builder.create().show();
        }
        else{
        finish();}
    }

    public void create_a_puzzle()
    {  answer=new LinkedList<>();
        HashSet<Integer> hashSet=new HashSet<>();
        while(hashSet.size()<4)
        {
            hashSet.add((int) (Math.random()*9));
        }
        for (Integer i:hashSet
             ) {
            answer.add(i);
        }
    }


    public void btn_replay(View view) {
        InitGame();
    }

    public void BTN_send(View view) {
        if(record_brn_times!=4){return;}
        int a = 0,b=0;String intputanswerstring="";
        for(int i=0;i<record_brn_times;i++)
        {
            intputanswerstring=intputanswerstring+intputanswer.get(i);
           if(answer.get(i).equals(intputanswer.get(i)))
           {
                   a++;
           }
           if(answer.contains(intputanswer.get(i))){
                    b++;
           }
        }
        Degree_of_difficulty--;
        BTN_clear(null);
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(from[0],(list.size()+1)+"");
        hashMap.put(from[1],intputanswerstring);
        hashMap.put(from[2],a+"A"+b+"b");
        list.add(hashMap);
        adapter.notifyDataSetChanged();
        listview.smoothScrollToPosition(list.size()-1);
      if(a==4)
      {
          win_num++;
          GAMEOVER(winner);
      }
      if(Degree_of_difficulty==0)
      {
          GAMEOVER(winner=false);
          lose_num++;
      }
    }
    public void GAMEOVER(boolean winner){
        String stringanswer="";
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("遊戲結束");
        for(int i=0;i<4;i++)
        {
            stringanswer=stringanswer+answer.get(i).toString();
        }
        builder.setMessage(winner?"獲勝":"失敗\n"+"答案"+stringanswer);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_back(null);
            }
        });
        builder.setNeutralButton("重玩", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_replay(null);
            }
        });
        builder.create().show();
    }

    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra("win",win_num);
        intent.putExtra("lose",lose_num);
        setResult(4,intent);
        super.finish();
    }
}