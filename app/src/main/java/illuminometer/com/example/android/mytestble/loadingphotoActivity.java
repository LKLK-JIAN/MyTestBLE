package illuminometer.com.example.android.mytestble;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;


public class loadingphotoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingphoto);
        loadImage("http://www.baidu.com/img/baidu_logo.gif", R.id.iv1);
        loadImage("http://www.chinatelecom.com.cn/images/logo_new.gif", R.id.iv2);
        loadImage("http://cache.soso.com/30d/img/web/logo.gif", R.id.iv3);
        loadImage("http://csdnimg.cn/www/images/csdnindex_logo.gif", R.id.iv4);
        loadImage("http://images.cnblogs.com/logo_small.gif", R.id.iv5);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ((ImageView) loadingphotoActivity.this.findViewById(msg.arg1)).setImageDrawable((Drawable) msg.obj);
        }
    };

    public void loadImage(final String Path,final int id){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable=null;
                try {
                    drawable=Drawable.createFromStream(new URL(Path).openStream(),"image.gif");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("TAG", "run: 88888888888" );
                Message message=handler.obtainMessage();
                message.obj=drawable;
                message.arg1=id;
                handler.sendMessage(message);
            }
        });
        thread.start();
    }
}
