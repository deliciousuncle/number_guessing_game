package com.example.guess_number3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

public class guess_binary extends AppCompatActivity {
    private int answer;
    private String answerString;
    private TextView textView;
    private EditText inputanswer;
    ////////////////
    private ListView listView;
    private LinkedList<HashMap<String,String>> list;
    private String[] from={"answer_list","true_or_false","remaining"};
    private int[] to={R.id.answer_list,R.id.true_or_false,R.id.remaining};
    private SimpleAdapter adapter;
    /////////////////////////////
    private boolean winner=false;
    //////////
    private int Degree_of_difficulty/*難度*/,win_num/*獲勝次數*/,lose_num/*失敗次數*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_binary);
        initview();
        initlistview();
        initGame();
    }
public void initlistview(){
    listView=findViewById(R.id.listview);
    list=new LinkedList<>();
    adapter=new SimpleAdapter(this,list,R.layout.guess_binary_listview,from,to);
    listView.setAdapter(adapter);
}
    public void initGame(){
        Degree_of_difficulty=getIntent().getIntExtra("Degree_of_difficulty",-1);
        creatanswer();
        btn_clear(null);
    }
    public void initview(){
        textView=findViewById(R.id.decimal_answer);
        inputanswer=findViewById(R.id.inputanswer);
    }

    public void btn_back(View view) {
        finish();
    }
    private void creatanswer(){
          answer= (int) (Math.random()*127);
        answerString=new String(Integer.toString(answer,2));
        textView.setText("題目:"+answer);
    }

    public void btn_replay(View view) {
        creatanswer();
        list.clear();
        winner=false;
        adapter.notifyDataSetChanged();
    }

    public void btn_clear(View view) {
        inputanswer.setText("");
        inputanswer.setHint("輸入答案");
    }

    public void btn_send(View view) {
        Degree_of_difficulty--;
        String userinput=new String(inputanswer.getText().toString());
        if (userinput.equals(answerString)) {
             winner=true;
            win_num++;
            GAMEOVER(winner);
        }
      HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(from[0],userinput);
        hashMap.put(from[1],winner+"");
        hashMap.put(from[2],"剩餘次數"+Degree_of_difficulty);
        list.add(hashMap);
        adapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(list.size()-1);//自動移動listview
        btn_clear(null);
        if(Degree_of_difficulty==0){
            lose_num++;
            GAMEOVER(winner);
        }
    }
    public void GAMEOVER(boolean winner){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(winner?"你好棒!!!":"你好爛!!!");
        builder.setMessage(winner?"你找到了答案":"你輸了答案是"+answerString);
        builder.setNeutralButton("重玩", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_replay(null);
            }
        });
        builder.setPositiveButton("離開", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_back(null);
            }
        });
        builder.create().show();
    }

    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra("win",win_num);
        intent.putExtra("lose",lose_num);
        setResult(01,intent);
        super.finish();
    }
}