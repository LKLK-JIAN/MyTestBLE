package illuminometer.com.example.android.mytestble;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class ImageActivity extends AppCompatActivity {
//     ListView listView;
//     String[] arr=new String[]{"http://www.baidu.com/img/baidu_logo.gif","http://www.chinatelecom.com.cn/images/logo_new.gif",
//             "http://cache.soso.com/30d/img/web/logo.gif","http://csdnimg.cn/www/images/csdnindex_logo.gif","http://images.cnblogs.com/logo_small.gif"};
         RequestQueue mQueue ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_image);
//        listView=(ListView) findViewById(R.id.listview);
//        Myadapter myadapter=new Myadapter(arr,this);
//        listView.setAdapter(myadapter);   loadImage(",
        mQueue = Volley.newRequestQueue(this);

        loadImage2("http://www.baidu.com/img/baidu_logo.gif", R.id.imageview1);
        loadImage2("http://www.chinatelecom.com.cn/images/logo_new.gif",
                R.id.imageview2);
        loadImage2("http://cache.soso.com/30d/img/web/logo.gif", R.id.imageview3);
        loadImage2("http://csdnimg.cn/www/images/csdnindex_logo.gif",
                R.id.imageview4);
//        loadImage2("http://images.cnblogs.com/logo_small.gif",
//                R.id.imageView5);
    }

    final Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ((ImageView) ImageActivity.this.findViewById(msg.arg1))
                    .setImageBitmap((Bitmap) msg.obj);
        }
    };

    // 采用handler+Thread模式实现多线程异步加载
    private void loadImage2(final String url, final int id) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                final Bitmap[] drawable = {null};
                ImageRequest imageRequest = new ImageRequest(
                        url,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                Log.e("TAG", "onResponse: 14141414141414");
                               drawable[0] =response;
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
           mQueue.add(imageRequest);
                // 模拟网络延时
                SystemClock.sleep(2000);

                Message message = handler2.obtainMessage();
                message.arg1 = id;
                message.obj = drawable[0];
                handler2.sendMessage(message);
            }
        };
        thread.start();
        thread = null;
    }

}
