package com.example.guess_number3;



import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
ActivityResultContracts.StartActivityForResult contrast=new ActivityResultContracts.StartActivityForResult();
////////////////////////////////////four_number
    ActivityResultCallback<ActivityResult> four_numberCallBack=new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        textView_four_number.setText("猜4個數字\n"+"此次獲勝次數:"+result.getData().getIntExtra("win",-1)+"\n此次失敗次數"+result.getData().getIntExtra("lose",-1));
    }
};
    ActivityResultLauncher<Intent> four_numberLauncher=registerForActivityResult(contrast,four_numberCallBack);
/////////////////////guess_binary
   ActivityResultCallback<ActivityResult> guess_binaryCallBack=new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        guess_binary_textView.setText("2進位轉換\n"+"此次獲勝次數:"+result.getData().getIntExtra("win",-1)+"\n此次失敗次數"+result.getData().getIntExtra("lose",-1));
    }
};
   ActivityResultLauncher<Intent> guess_binary_Launcher=registerForActivityResult(contrast,guess_binaryCallBack);
////////////////////two_number
ActivityResultCallback<ActivityResult> two_numberCallback=new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        two_number_textView.setText("猜0~99\n"+"此次獲勝次數:"+result.getData().getIntExtra("win",-1)+"\n此次失敗次數"+result.getData().getIntExtra("lose",-1));
    }
};
ActivityResultLauncher<Intent> two_number_Launcher=registerForActivityResult(contrast,two_numberCallback);
/////////////////////
    TextView textView_four_number,guess_binary_textView,two_number_textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_four_number=findViewById(R.id.textView_four_number);
        guess_binary_textView=findViewById(R.id.guess_binary_textView);
        two_number_textView=findViewById(R.id.two_number_textView);
    }
    public void four_number(View view) {
       // Intent intent=new Intent(this,four_number.class);
        //startActivity(intent);
        Mainbulid(1);
    }
    public void guess_binary(View view) {
       // Intent intent=new Intent(this,guess_binary.class);
       // startActivity(intent);
        Mainbulid(2);
    }
    public void two_number(View view) {
       // Intent intent=new Intent(this,two_number.class);
       // startActivity(intent);
        Mainbulid(3);
    }
    public void Mainbulid(int whichbtn){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        switch (whichbtn){
            case 1:
                builder.setTitle("你選擇了猜4個數字");
                builder.setMessage("選擇難度");
                builder.setNeutralButton("難度低", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                   Intent intent=new Intent(MainActivity.this,four_number.class);
                   intent.putExtra("Degree_of_difficulty",10);
                   four_numberLauncher.launch(intent);
                    }
                });
                builder.setPositiveButton("難度中", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainActivity.this,four_number.class);
                        intent.putExtra("Degree_of_difficulty",6);
                        four_numberLauncher.launch(intent);
                    }
                });
                builder.setNegativeButton("難度高", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainActivity.this,four_number.class);
                        intent.putExtra("Degree_of_difficulty",3);
                        four_numberLauncher.launch(intent);
                    }
                });
                builder.create().show();
                break;
            case 2:
                builder.setTitle("你已選擇了二進位轉換");
                builder.setMessage("選擇難度");
                builder.setNegativeButton("難度低", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainActivity.this,guess_binary.class);
                        intent.putExtra("Degree_of_difficulty",4);
                        guess_binary_Launcher.launch(intent);
                    }
                });
                builder.setNeutralButton("難度中", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     Intent intent=new Intent(MainActivity.this,guess_binary.class);
                     intent.putExtra("Degree_of_difficulty",3);
                     guess_binary_Launcher.launch(intent);
                    }
                });
                builder.setPositiveButton("難度高", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainActivity.this,guess_binary.class);
                        intent.putExtra("Degree_of_difficulty",2);
                        guess_binary_Launcher.launch(intent);
                    }
                });
                builder.create().show();
                break;
            case 3:
                builder.setTitle("你已選擇了猜0~99");
                builder.setMessage("選擇難度");
                builder.setPositiveButton("難度低", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                          Intent intent=new Intent(MainActivity.this,two_number.class);
                        intent.putExtra("Degree_of_difficulty",10);
                        two_number_Launcher.launch(intent);
                    }
                });
                builder.setNeutralButton("難度中", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainActivity.this,two_number.class);
                        intent.putExtra("Degree_of_difficulty",5);
                        two_number_Launcher.launch(intent);
                    }
                });
                builder.setNegativeButton("難度高", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainActivity.this,two_number.class);
                        intent.putExtra("Degree_of_difficulty",3);
                        two_number_Launcher.launch(intent);
                    }
                });
                builder.create().show();
        }
    }
    public void quit(View view) {
        finish();
    }

}