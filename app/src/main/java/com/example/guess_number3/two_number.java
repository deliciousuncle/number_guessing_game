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
import java.util.LinkedList;

public class two_number extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;//調變器
private LinkedList<Integer> answer=new LinkedList<>();
private TextView textViews[]=new TextView[2],Degree_of_difficulty_text;
private int[] intres={R.id.num1,R.id.num4};
private Button[] buttons=new Button[10];
private int[] intbtn={R.id.number_0,R.id.number_1,R.id.number_2,R.id.number_3,R.id.number_4,R.id.number_5,
        R.id.number_6,R.id.number_7,R.id.number_8,R.id.number_9};
     String from[]=new String[]{"lowest_num","meaningless","Highest_num"};//調變器用的KEY
     int[] to={R.id.twonum1,R.id.meaningless,R.id.twonum2};//調變器連接two_number_listview的textview
    private LinkedList<HashMap<String,String>> list;//負責記錄調變器更動的
    private LinkedList<Integer> record_numbers=new LinkedList<>();//紀錄數字之後用做判斷
    private int input_record=0;//負責記錄輸入了幾次
    private int maximum_number=99,minimum_number=0;//最大數及最小數 負責send數字的判斷
    private int entered_number;//將record_numbers轉成整數
   // private int answer_judge;//answer轉整數
    private boolean winner=false;//判斷是否勝利
    /////////
    private int Degree_of_difficulty/*難易度*/,win_num/*獲勝次數*/,lose_num/*失敗次數*/,Number_of_departures=0/*離開次數*/;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_number);
        initview();//初始化所有的VIEW
       initlistview();//設定listview的調變器
        initgame();//創造遊戲
    }
    private void initgame(){
        answer.clear();
        Degree_of_difficulty=getIntent().getIntExtra("Degree_of_difficulty",-1);
        answer= creatanswer();//設置謎底
        maximum_number=99;
        minimum_number=0;
        winner=false;
        btn_clear(null);
    }
    private void initlistview(){//設定listview的調變器
        listView=findViewById(R.id.listview);
        list=new LinkedList<>();
         adapter=new SimpleAdapter(this,list,R.layout.two_number_listview,from,to);
        listView.setAdapter(adapter);
    }
    private void initview(){ //初始化所有的VIEW
        for(int i=0;i<intres.length;i++){
            textViews[i]=findViewById(intres[i]);
        }
       for(int i=0;i<intbtn.length;i++){
           buttons[i]=findViewById(intbtn[i]);
        }
        Degree_of_difficulty_text=findViewById(R.id.Degree_of_difficulty_text);
    }
    public void btn_back(View view) {
        finish();
    }
    private LinkedList creatanswer(){
       answer.add((int) (Math.random()*99+0));
       return answer;
    }

    public void btn_clear(View view) {
   input_record=0;
   record_numbers.clear();
   for(int i=0;i<textViews.length;i++){
    textViews[i].setText("");
   }
   for(int i=0;i<2;i++)
   {
       record_numbers.add(-1);//讓linkedlist裡面的size不為0
   }
    }

   public void inputnum(View view) {
     if(input_record==2){return;}

     for(int i=0;i<intbtn.length;i++)
     {
         if(buttons[i]==view)
         {  record_numbers.size();
            record_numbers.set(input_record,i);//紀錄數字之後用做判斷
            textViews[input_record].setText(i+"");//將輸入的數字顯示在護面上
             input_record++;
             break;
             }
            }
        }

    public void btn_delete(View view) {
        if(input_record==0){return;}
        input_record--;
        record_numbers.remove(input_record);
        textViews[input_record].setText("");

    }

    public void btn_send(View view) {
    if(input_record==0)//沒輸入任何資料變送出
    {return;}
    else if(input_record==1)  //只輸入一次變送出
    {entered_number=record_numbers.get(input_record-1); }
    else if(input_record==2&&record_numbers.get(0)==0)//輸入兩次但十位數輸入的是0
    {entered_number=record_numbers.get(1);}
    else if(input_record==2&&record_numbers.get(0)!=0)//輸入兩次十位數非0
    { entered_number=(record_numbers.get(input_record-2)*10)+record_numbers.get(input_record-1); }
    if(entered_number<answer.get(0)&&entered_number>minimum_number){ //輸入小於答案
          minimum_number=entered_number;
      }
    if(entered_number==answer.get(0)){ //輸入正確
        winner=true;
    }
    if(entered_number>answer.get(0)&&entered_number<maximum_number){//輸入大於答案
          maximum_number=entered_number;
      }
        Degree_of_difficulty--;
      HashMap<String,String> hashMap=new HashMap<>();
      hashMap.put(from[0],minimum_number+"");
        hashMap.put(from[1],"到");
        hashMap.put(from[2],maximum_number+"之間" );
        Degree_of_difficulty_text.setText("剩餘機會:"+Degree_of_difficulty);
        list.add(hashMap);
        adapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(list.size()-1);//自動移動listview
        btn_clear(null);

        if(Degree_of_difficulty==0)
        {
          Game_Over(winner);
          lose_num++;
        }
       if(winner==true)
       {
        Game_Over(winner);
        win_num++;
       }

    }
    private void Game_Over(boolean winner){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("遊戲結束");
        builder.setMessage(winner?"你已經找到了答案:":"你輸了答案是"+answer.get(0));
        //alterdialog最多可以放三個按鈕，且位置是固定的，分別是
        //setPositiveButton()右邊按鈕
        //setNegativeButton()中間按鈕
        //setNeutralButton()左邊按鈕
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNeutralButton("開新遊戲", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_replay(null);
            }
        });
        builder.create().show();
    }

    public void btn_replay(View view) {
        list.clear();
        initgame();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra("win",win_num);
        intent.putExtra("lose",lose_num);
        setResult(2,intent);
        super.finish();
    }
}

