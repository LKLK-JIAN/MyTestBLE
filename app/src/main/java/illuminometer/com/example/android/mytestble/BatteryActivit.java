package illuminometer.com.example.android.mytestble;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BatteryActivit extends AppCompatActivity {
    private ToggleButton tb;
    private TextView batterytv;
   // private BatteryReceiver receiver=null;
     AlarmManager alermmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        tb=(ToggleButton) findViewById(R.id.tb);
        batterytv=(TextView) findViewById(R.id.batterytv);
        alermmanager=(AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmReceiver.class);
        PendingIntent pendingintent=PendingIntent.getBroadcast(this,0,intent,0);
        alermmanager.set(AlarmManager.RTC_WAKEUP,1000,pendingintent);
        //receiver=new BatteryReceiver();
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                    registerReceiver(broadcastReceiver, filter);//注册BroadcastReceiver
                }
                else{
                    //停止获取电池电量
                    unregisterReceiver(broadcastReceiver);
                    batterytv.setText(null);
                }
            }
        });
   }

    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int current=intent.getExtras().getInt("level");//获得当前电量
            int total=intent.getExtras().getInt("scale");//获得总电量
            int percent=current*100/total;
            System.out.print("0");
            System.out.print("0");
            System.out.print("0");
            //batterytv.setText("现在的电量是"+percent+"%。");
            batterytv.setText("现在的电量是"+percent+"%。");
        }
    }

   BroadcastReceiver  broadcastReceiver=new BroadcastReceiver() {
       @Override
       public void onReceive(Context context, Intent intent) {
           int current=intent.getExtras().getInt("level");//获得当前电量
           int total=intent.getExtras().getInt("scale");//获得总电量
           int percent=current*100/total;

           batterytv.setText("现在的电量是"+percent+"%。");
       }
   };
//    private class BatteryReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    }
    private class AlarmReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "onReceive: 8888877788" );
            batterytv.setText("现在的电量是%。");
        }
    }
}
