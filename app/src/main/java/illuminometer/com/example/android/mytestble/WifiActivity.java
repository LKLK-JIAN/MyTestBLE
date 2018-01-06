package illuminometer.com.example.android.mytestble;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import illuminometer.com.example.android.mytestble.adapter.WifiAdapter;

public class WifiActivity extends AppCompatActivity {
    WifiAdmin admin;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        admin=new WifiAdmin(this);
        admin.openWifi();
        handler=new Handler() ;
        startScan(true);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("TAG", "run:888888888888 " );
//                        admin.startScan();
//                        WifiAdapter adapter=new WifiAdapter(admin.getWifiList(),WifiActivity.this);
//                        ((ListView)WifiActivity.this.findViewById(R.id.wifiinfoview)).setAdapter(adapter);
//                    }
//                });
//
//            }
//        },1000,5000);

        ((Button)WifiActivity.this.findViewById(R.id.search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.startScan();
                WifiAdapter adapter=new WifiAdapter(admin.getWifiList(),WifiActivity.this);
                ((ListView)WifiActivity.this.findViewById(R.id.wifiinfoview)).setAdapter(adapter);
            }
        });
    }
    public void startScan(final boolean enable){
        if(enable){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run:1111111111 " );
                    admin.startScan();
                        WifiAdapter adapter=new WifiAdapter(admin.getWifiList(),WifiActivity.this);
                        ((ListView)WifiActivity.this.findViewById(R.id.wifiinfoview)).setAdapter(adapter);
                }
            },5000);
            Log.e("TAG", "run:11111111112" );
            admin.startScan();
            WifiAdapter adapter=new WifiAdapter(admin.getWifiList(),WifiActivity.this);
            ((ListView)WifiActivity.this.findViewById(R.id.wifiinfoview)).setAdapter(adapter);
        }
        else{
            Log.e("TAG", "run:11111111113" );
            admin.startScan();
            WifiAdapter adapter=new WifiAdapter(admin.getWifiList(),WifiActivity.this);
            ((ListView)WifiActivity.this.findViewById(R.id.wifiinfoview)).setAdapter(adapter);
        }
    }

}
