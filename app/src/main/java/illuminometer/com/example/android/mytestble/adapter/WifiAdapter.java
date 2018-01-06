package illuminometer.com.example.android.mytestble.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import illuminometer.com.example.android.mytestble.R;

/**
 * Created by android on 2017/11/22.
 */
public class WifiAdapter extends BaseAdapter {

    List<ScanResult> wifiInfos =null;
    Context con;

    public WifiAdapter(List<ScanResult> wifiInfos, Context con){
        this.wifiInfos =wifiInfos;
        this.con = con;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return wifiInfos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return wifiInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LayoutInflater.from(con).inflate(R.layout.wifilayout, null);
        TextView tv = (TextView)convertView.findViewById(R.id.tv);
        TextView tv2 = (TextView)convertView.findViewById(R.id.tv1);
        tv.setText(wifiInfos.get(position).level+"");
        tv2.setText(wifiInfos.get(position).SSID+"");

        return convertView;
    }

}
