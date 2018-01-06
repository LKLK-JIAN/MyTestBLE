package illuminometer.com.example.android.mytestble.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import illuminometer.com.example.android.mytestble.R;
import illuminometer.com.example.android.mytestble.holder.PhotoViewHolder;

/**
 * Created by android on 2017/11/18.
 */
public class MylistphoteAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    ArrayList names = null;
    ArrayList descs= null;
    ArrayList fileNames = null;
    Context context;
    PhotoViewHolder holder;

    public MylistphoteAdapter(Context context, ArrayList names, ArrayList fileNames) {
        this.context=context;
        this.names = names;
        this.fileNames = fileNames;
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

        final View finalConvertView = convertView;
        final Handler handler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ImageView image = (ImageView) finalConvertView.findViewById(R.id.desc);
                image.setImageBitmap(BitmapFactory.decodeFile((String) fileNames.get(position)));
            }
        };

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                 Message message=new Message();
                 message.arg1=position;
                 handler.sendEmptyMessage(00);
            }
        });
        thread.start();

        return convertView;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_FLING){

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
