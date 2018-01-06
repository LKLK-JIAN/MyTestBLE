package illuminometer.com.example.android.mytestble.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import illuminometer.com.example.android.mytestble.R;
import illuminometer.com.example.android.mytestble.domain.Document;
import illuminometer.com.example.android.mytestble.holder.FileViewHolder;

/**
 * Created by android on 2017/10/13.
 */
public class FileListAdapter extends BaseAdapter {
    Context context;
    List<Document> list=new ArrayList<>();
    FileViewHolder holder;

    public FileListAdapter(Context context, List<Document> list) {
        super();
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        holder = new FileViewHolder();
        view = LayoutInflater.from(context).inflate(R.layout.file_item_list, null);
        if(view!=null) {
            holder.file_time = (TextView) view.findViewById(R.id.file_time);
            holder.file_title = (TextView) view.findViewById(R.id.file_title);
            view.setTag(holder);

        }
//add
        Log.e("TAG", "getView: 888888888884421324"+holder.file_time );

        holder.file_time.setText(list.get(i).getFile_time().toString());
        holder.file_title.setText(list.get(i).getFile_title().toString());
        return view;
    }
}
