package illuminometer.com.example.android.mytestble.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;
import illuminometer.com.example.android.mytestble.R;
import illuminometer.com.example.android.mytestble.photoActivity;

/**
 * Created by android on 2018/1/6.
 */
public class ImageLoader {
    private LruCache<String,Bitmap> cache;//用于缓存图片
    private ListView listView;

    public ImageLoader(ListView listView) {//初始化一些数据
        this.listView = listView;
        int maxMemry = (int) Runtime.getRuntime().maxMemory();//获取当前应用可用的最大内存
        int cacheSize = maxMemry/4;//以最大的四分之一作为可用的缓存大小
        this.cache = new LruCache<String,Bitmap>(cacheSize){//初始化LruCache
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();//每次存入缓存的大小，即bitmap的大小
            }
        };
    }


    public Bitmap getBitmapFromUrl(String urlString){
        Bitmap bitmap;
        bitmap= BitmapFactory.decodeFile(urlString);

        return bitmap;
    }
    /**
     * 将图片url转化为bitmap
     * @param
     * @return Bitmap
     */

    public void showImageByAsyncTask(ImageView imageView, String url){//改进之后，获取图片的控制权由原来getview改成了滚动状态。
        Bitmap bitmap = getBitmapFromUrl(url);//从缓存中获取图片
        if(bitmap==null){//如果没有就设置默认的图片
            imageView.setImageResource(R.mipmap.ic_launcher);
        }else{//如果有就设置当前的图片
            imageView.setImageBitmap(bitmap);
        }

    }
    /**
     * 加载从start到end的所有图片
     * @param start
     * @param end
     */
    public void loadImage(int start,int end){
        for (int i= start;i<end;i++){//拿到数组中的图片对应的url
            String url = (String)photoActivity.fileNames.get(i);
            Bitmap bitmap = getBitmapFromUrl(url);
            ImageView imageView = (ImageView) listView.findViewWithTag(url);//通过findViewWithTag找到imageview，这个tag就是imageview的url
            imageView.setImageBitmap(bitmap);
//            bitmap.recycle();
        }
    }
}
