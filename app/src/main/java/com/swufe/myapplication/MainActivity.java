package com.swufe.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {

    TextView out; //TextView控件 类变量 显示区
    EditText inp;//Edittext控件 输入区
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.swufe.myapplication.R.layout.activity_main);

       out = findViewById(com.swufe.myapplication.R.id.showText);//控件获取的初始化
       inp = findViewById(com.swufe.myapplication.R.id.inpText);

       //Button btn = findViewById(R.id.btn1);
       //btn.setOnClickListener(this);//当前类监听
//        btn.setOnClickListener(new View.OnClickListener() {
 //           @Override
        //           public void onClick(View v) {//实现的接口匿名类监听
//              Log.i("mail","onClick called....");
//            String str = inp.getText().toString();
//                out.setText("Hello" +str);
//            }
//        });
    }

   // @Override
    //public void onClick(View v) {
  //      Log.i("click","onClick.....");

        //TextView tv = findViewById(R.id.showText);//没有定义类变量的时候用于重新获取对象

        //EditText inp = findViewById(R.id.inpText);
    //    String str = inp.getText().toString();
    //    out.setText("Hello"  + str);

  //  }
    public void btnClick(View btn){//对按钮使用配置文件（最简便的方法，不用使用监听器）
        Log.i("click", "btnClick called.....");
        String str = inp.getText().toString();//调取用户输入的内容并转换为字符串
        out.setText("Hello"+" "+str);//输出hello+用户输入的内容
    }
}
