package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoringActivity extends AppCompatActivity {
    TextView score;//获取分数控件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        score=findViewById(R.id.score);//这里的id就是配置文件里的id
    }
    public void btnAdd1(View btn){
        showScore(1);
    }//这里取的名字最好对应布局视图里的id名，便于在给控件加方法的时候查找到
    public void btnAdd2(View btn){
        showScore(2);
    }
    public void btnAdd3(View btn){
        showScore(3);
    }
    public void btnReset(View btn){
        score.setText("0");//Reset键执行此方法
    }

    private void showScore(int inc){
        Log.i("show","inc="+inc);//用于在Logcat中输出日志
        String oldScore = (String)score.getText();
        int newScore = Integer.parseInt(oldScore) + inc;
        score.setText(""+ newScore);//but123执行此方法（“”+newScore是将整数转换为字符串）


    }

}
