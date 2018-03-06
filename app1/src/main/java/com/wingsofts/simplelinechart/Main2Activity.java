package com.wingsofts.simplelinechart;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {
//     ListView listView;
//     String[] arr=new String[]{"http://www.baidu.com/img/baidu_logo.gif","http://www.chinatelecom.com.cn/images/logo_new.gif",
//             "http://cache.soso.com/30d/img/web/logo.gif","http://csdnimg.cn/www/images/csdnindex_logo.gif","http://images.cnblogs.com/logo_small.gif"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        listView=(ListView) findViewById(R.id.listview);
//        Myadapter myadapter=new Myadapter(arr,this);
//        listView.setAdapter(myadapter);   loadImage(",
        loadImage2("http://www.baidu.com/img/baidu_logo.gif", R.id.imageview1);
        loadImage2("http://www.chinatelecom.com.cn/images/logo_new.gif",
                R.id.imageview2);
        loadImage2("http://cache.soso.com/30d/img/web/logo.gif", R.id.imageview3);
        loadImage2("http://csdnimg.cn/www/images/csdnindex_logo.gif",
                R.id.imageview4);
//        loadImage2("http://images.cnblogs.com/logo_small.gif",
//                R.id.imageView5);
    }

    @SuppressLint("HandlerLeak")
    final Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj!=null){
            ((ImageView) Main2Activity.this.findViewById(msg.arg1))
                    .setImageBitmap((Bitmap) msg.obj);
        }}
    };

    // 采用handler+Thread模式实现多线程异步加载
    private void loadImage2(final String url, final int id) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                HttpURLConnection con = null;
                try {
                    URL url1 = new URL(url);
                    con = (HttpURLConnection) url1.openConnection();
                    con.setConnectTimeout(5 * 1000);
                    con.setReadTimeout(10 * 1000);
                    bitmap = BitmapFactory.decodeStream(con.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = handler2.obtainMessage();
                message.arg1 = id;
                message.obj = bitmap;
                handler2.sendMessage(message);
            }
        };
        thread.start();
        };
    }

