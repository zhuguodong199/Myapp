package com.swufe.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;//变量名不用在整个工程内唯一，只用在当前类唯一就行
    private final String tag ="Rate";
    private float dollarRate=0.1f;
    private float euroRate=0.2f;
    private float wonRate=0.3f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.showOut);
    }
    public void onClick(View btn){
        //获取用户输入内容
        Log.i("click","onClick:");
        String str=rmb.getText().toString();
        Log.i("click","onClick: r="+ str);
        float r = 0;
        if(str.length()>0) {
            r = Float.parseFloat(str);//用户输入内容时，r值等于用户输入的内容；用户没有输入内容时，r值为0
        }else{
            //提示用户输入内容
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();//消息提示，选择create a new toast
        }
        Log.i("click","onClick: r="+ r);
        //计算；方法一，格式化为两个小数
        if(btn.getId()==R.id.btn_dollar){
            show.setText(String.format("%.2f",r*dollarRate));
        }else if(btn.getId()==R.id.btn_euro){
            show.setText(String.format("%.2f",r*euroRate));
        }else{
            show.setText(String.format("%.2f",r*wonRate));
        }
        //方法二：未格式化为两位小数
//        if(btn.getId()==R.id.btn_dollar){//当用户点美元时
//            float val = r*(1/6.7f);//r是人民币，r*6.7只是double类型，我们需要的是float类型，所以加一个f
//            show.setText(String.valueOf(val));//setText方法里的参数只能是字符串类型，所以要把float类型转换成为字符串类型
//        }else if(btn.getId()==R.id.btn_euro){
//            float val = r*(1/11.0f);//1/11=0，因为整数除以整数仍为整数，转为小数时要加小数点变为double型，再加f才能变为float型
//            show.setText(val+"");//数字类型+“”可自动转换成为字符串类型
//        }else{
//            float val = r*500;
//            show.setText(String.valueOf(val));
//        }
        /*
方法三：方法改进(方法更直观）未格式化为两位小数
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
    //抽取方法功能将配置对话框抽取出来，这样在下面菜单中也用到此方法的时候，就可以不用重复写代码
    public void openOne(View btn){
        openConfig();
    }
    //配置对话框
    private void openConfig() {
        //打开一个页面Activity
        Log.i("open", "openOne:");
        Intent hello = new Intent(this, ScoringActivity.class);//打开scoringactivity窗口
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:87092173"));
        Intent config = new Intent(this, ConfigActivity.class);
        //页面传递参数
        config.putExtra("dollar_rate_key", dollarRate);//往config里放变量dollarRate的时候给它一个标签"dollar_rate_key"
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);
        //用于调试
        Log.i("open", "openOne:dollarRate" + dollarRate);
        Log.i("open", "openOne:euroRate" + dollarRate);
        Log.i("open", "openOne:wonRate" + wonRate);
        //startActivity(config);
        //打开窗口带回数据，后面的值可以是任何数,1是请求编码
        startActivityForResult(config, 1);
    }

    @Override
    //在activity中启用菜单项
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
       return true;
    }

    @Override
    //处理菜单事件
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
        //配置对话框（事件处理代码）
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override//requestCode可以确定是哪个窗口返回的数据，resultCode可以确定返回什么类型的数据
    //接收返回数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);
            euroRate = bundle.getFloat("key_euro",0.1f);
            wonRate = bundle.getFloat("key_won",0.1f);
            Log.i(tag,"onActivityResult:dollarRate="+ dollarRate);
            Log.i(tag,"onActivityResult:dollarRate="+ euroRate);
            Log.i(tag,"onActivityResult:dollarRate="+ wonRate);



        }
    }
}
