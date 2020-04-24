package com.swufe.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TemperatureActivity extends AppCompatActivity {

    TextView out; // 类变量
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        out=findViewById(R.id.tempOut);//控件获取的初始化
        inp=findViewById(R.id.inpTemp);
    }
    public void temp_btnClick(View btn){//对按钮使用配置文件
        Log.i("click", "btnClick called.....");//在Logcat中输出日志
        String centigrade = inp.getText().toString();//调取用户输入的摄氏温度并转换为字符串
        double fahrenheit = Double.parseDouble(centigrade)*1.8 + 32;//计算华氏温度
        out.setText("结果为："+""+fahrenheit);//输出华氏温度
    }
}
