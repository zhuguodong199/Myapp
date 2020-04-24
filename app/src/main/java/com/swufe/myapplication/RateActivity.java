package com.swufe.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RateActivity extends AppCompatActivity implements Runnable {
    EditText rmb;
    TextView show;//变量名不用在整个工程内唯一，只用在当前类唯一就行
    Handler handler;
    private final String tag = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.showout);

        //获取sp里保存的数据。打开app时取值都为0，因为此时还没有文件myrate，就会取默认值。
        //括号里第一个是一个字符串文件，第二个是访问权限。私有权限只允许当前app应用读写文件。
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        //法二
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dolla_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);

        Log.i(tag, "onCreate:sp dollarRate=" + dollarRate);
        Log.i(tag, "onCreate:sp euroRate=" + euroRate);
        Log.i(tag, "onCreate:sp wonRate=" + wonRate);

        //开启子线程
        Thread t =new Thread(this);
        t.start();

         handler = new Handler(){
            //匿名类方法改写
            @Override
           public void handleMessage(Message msg){
                if(msg.what==5){
                    //将obj类型强制转换为字符串类型
                    String str=(String) msg.obj;
                    Log.i(tag,"handleMessage:getMessage msg =" +str);
                    show.setText(str);
                }
               super.handleMessage(msg);
           }
        };
    }

    public void onClick(View btn) {
        //获取用户输入内容
        Log.i("click", "onClick:");
        String str = rmb.getText().toString();
        Log.i("click", "onClick: r=" + str);
        float r = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);//用户输入内容时，r值等于用户输入的内容；用户没有输入内容时，r值为0
        } else {
            //提示用户输入内容
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();//消息提示，选择create a new toast
            return;
        }
        Log.i("click", "onClick: r=" + r);
        //计算；方法一，格式化为两个小数
        if (btn.getId() == R.id.btn_dollar) {
            show.setText(String.format("%.2f", r * dollarRate));
        } else if (btn.getId() == R.id.btn_euro) {
            show.setText(String.format("%.2f", r * euroRate));
        } else {
            show.setText(String.format("%.2f", r * wonRate));
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
    //此方法是响应按钮的方法，因为括号里有View btn
    public void openOne(View btn) {
        openConfig();
    }

    //配置对话框，但此方法并不是响应按钮的方法
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
        getMenuInflater().inflate(R.menu.rate, menu);
        return true;
    }

    @Override
    //处理菜单事件
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_set) {
            //配置对话框（事件处理代码）
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override//requestCode可以确定是哪个窗口返回的数据，resultCode可以确定返回什么类型的数据
    //接收返回数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar", 0.1f);
            euroRate = bundle.getFloat("key_euro", 0.1f);
            wonRate = bundle.getFloat("key_won", 0.1f);
            Log.i(tag, "onActivityResult:dollarRate=" + dollarRate);
            Log.i(tag, "onActivityResult:dollarRate=" + euroRate);
            Log.i(tag, "onActivityResult:dollarRate=" + wonRate);

            //将新设置的汇率写到sp里
            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate", dollarRate);
            editor.putFloat("euro_rate", euroRate);
            editor.putFloat("won_rate", wonRate);
            //用于保存数据。两种方法都可以用，commit需要等待操作完成再进行保存，apply不用等待。
            editor.commit();
//            editor.apply();
            Log.i(tag, "onActivityResult:数据已保存到sharedPreferences");


        }
    }

    @Override
    public void run() {
        Log.i(tag, "run:run()……");
        for(int i=1;i<6;i++) {
        Log.i(tag,"run:i="+i);
           //停止2秒钟
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //获取Msg对象，用于返回主线程
        Message msg= handler.obtainMessage(5);
//        msg.what=5;  5是msg标号
        //obj是字符串msg
        msg.obj ="Hello from run()";
        handler.sendMessage(msg);
        //获取网络数据
        URL url = null;
        try {
            url = new URL("http://www.usd-cny.com/icbc.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(tag,"run:html="+html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   //把输入流转换成字符串输出
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
                out.append(buffer, 0, rsz);
        }
         return out.toString();
    }
    }

