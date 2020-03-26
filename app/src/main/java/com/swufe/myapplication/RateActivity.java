package com.swufe.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;//变量名不用在整个工程内唯一，只用在当前类唯一就行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getApplicationContext().getResources().getIdentifier("main", "layout", getApplicationContext().getPackageName()));
        setContentView(R.layout.activity_rate);
        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.showOut);
    }
    public void onClick(View btn){
        //获取用户输入内容
        String str=rmb.getText().toString();
        float r = 0;
        if(str.length()>0) {
            r = Float.parseFloat(str);//用户输入内容时，r值等于用户输入的内容；用户没有输入内容时，r值为0
        }else{
            //提示用户输入内容
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();//消息提示，选择create a new toast
        }

        if(btn.getId()==R.id.btn_dollar){//当用户点美元时
            float val = r*(1/6.7f);//r是人民币，r*6.7只是double类型，我们需要的是float类型，所以加一个f
            show.setText(String.valueOf(val));//setText方法里的参数只能是字符串类型，所以要把float类型转换成为字符串类型
        }else if(btn.getId()==R.id.btn_euro){
            float val = r*(1/11.0f);//1/11=0，因为整数除以整数仍为整数，转为小数时要加小数点变为double型，再加f才能变为float型
            show.setText(val+"");//数字类型+“”可自动转换成为字符串类型
        }else{
            float val = r*500;
            show.setText(String.valueOf(val));
        }
        /*
方法改进(方法更直观）
        float val = 0;
        if(btn.getId()==R.id.btn_dollar){
         val = r*(1/6.7f);
        }else if(btn.getId()==R.id.btn_euro){
         val = r*(1/11.0f);
        }else{
         val = r*500;
        }
        show.setText(String.valueOf(val));
*/
    }
    public void openOne(View btn){
        //打开一个页面Activity
        Log.i("open","openOne:");
        Intent hello = new Intent(this,ScoringActivity.class);
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:87092173"));
        startActivity(hello);
    }
}
