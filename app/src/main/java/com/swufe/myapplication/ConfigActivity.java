package com.swufe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {
    public final String tag = "ConfigActivity";
    //定义控件
    EditText dollarText;
    EditText euroText;
    EditText wonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent= getIntent();
        float dollar2=intent.getFloatExtra("dollar_rate_key",0.0f);//取数据时通过"dollar_rate_key"标签去取
        //当标签不一致时，后面那个变量会变成默认值自动赋予dollar2
        float euro2=intent.getFloatExtra("euro_rate_key",0.0f);
        float won2=intent.getFloatExtra("won_rate_key",0.0f);
        Log.i("cfg", "onCreate:dollar2="+dollar2);
        Log.i("cfg", "onCreate:euro2="+euro2);
        Log.i("cfg", "onCreate:won2="+won2);

        //获取控件
        dollarText = (EditText)findViewById(R.id.dollar_rate);
        euroText = (EditText)findViewById(com.swufe.myapplication.R.id.euro_rate);
        wonText = (EditText)findViewById(com.swufe.myapplication.R.id.won_rate);

        //显示数据到控件
        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));
    }

    public void save(View btn) {
        Log.i("cfg", "save: ");
        //获取新的值
        float newDollar = Float.parseFloat(dollarText.getText().toString());
        float newEuro= Float.parseFloat(euroText.getText().toString());
        float newWon = Float.parseFloat(wonText.getText().toString());

        Log.i(tag,"save:获取到新的值");
        Log.i("cfg", "save:newDollar="+newDollar);
        Log.i("cfg", "save:newEuro2="+newEuro);
        Log.i("cfg", "save:newWon2="+newWon);

        //保存到bundle或放入到Extra.把数据打包放到bundle再返回bundle。2是相应代码
        //使用bundle传递参数
        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);
        setResult(2,intent);//设置resultCode及带回的数据

        //返回到调用页面.finish就是结束当前页面，回到上一页面
        finish();
    }
}

