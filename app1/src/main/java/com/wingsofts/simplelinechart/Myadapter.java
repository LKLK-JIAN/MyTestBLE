package com.wingsofts.simplelinechart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by android on 2018/1/15.
 */

public class Myadapter extends BaseAdapter{
    String[] arr;
    Context context;
    Myadapter(String[] arr,Context context){
        this.arr=arr;
        this.context=context;
    }
    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int position) {
        return arr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.listview_layout,null);
        Holder holder=new Holder();
        holder.imageView=(ImageView)convertView.findViewById(R.id.iv);
        try {
            Glide.with(context).load(new URL(arr[position])).into(holder.imageView);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        return convertView;
    }
    class Holder{
        public ImageView imageView;
    }
}
