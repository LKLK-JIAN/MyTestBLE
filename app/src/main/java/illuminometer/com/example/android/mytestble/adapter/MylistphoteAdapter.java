package illuminometer.com.example.android.mytestble.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import illuminometer.com.example.android.mytestble.R;
import illuminometer.com.example.android.mytestble.holder.PhotoViewHolder;
import illuminometer.com.example.android.mytestble.photoActivity;

/**
 * Created by android on 2017/11/18.
 */
public class MylistphoteAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    int start;
    int end;
    boolean isFirst;
    ArrayList names = null;
    ArrayList descs= null;
    ArrayList fileNames = null;
    ImageLoader imageLoader;
    Context context;
    PhotoViewHolder holder;

    public MylistphoteAdapter(Context context, ArrayList names, ArrayList fileNames, ListView listview) {
        this.context=context;
        this.names = names;
        this.fileNames = fileNames;
        listview.setOnScrollListener(this);
        imageLoader=new ImageLoader(listview);
        isFirst=true;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder=new PhotoViewHolder();
        convertView= LayoutInflater.from(context).inflate(R.layout.line, null);
        if(convertView!=null){
            holder.name=(TextView) convertView.findViewById(R.id.name);
            holder.desc=(ImageView) convertView.findViewById(R.id.desc);
            convertView.setTag(holder);
        }
        else{
            convertView.getTag();
        }
       if(names.get(position)==null){
           holder.name.setText(" ");
       }
        else{
           holder.name.setText(names.get(position).toString()+" ");
       }
       holder.desc.setImageResource(R.mipmap.ic_launcher);
       holder.desc.setTag(photoActivity.fileNames.get(position));
       //imageLoader.showImageByAsyncTask(holder.desc,(String)photoActivity.fileNames.get(position));
//        final View finalConvertView = convertView;
//        ImageView image = (ImageView) finalConvertView.findViewById(R.id.desc);
//
//        image.setImageBitmap(BitmapFactory.decodeFile((String) fileNames.get(position)));
        return convertView;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_FLING){
           imageLoader.loadImage(start,end);
        }
        else{

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
      start=firstVisibleItem;
      end=visibleItemCount+firstVisibleItem;
        if(isFirst && visibleItemCount > 0){//第一次加载的时候调用，显示图片
            imageLoader.loadImage(start,end);
            isFirst=false;
        }
    }
}
