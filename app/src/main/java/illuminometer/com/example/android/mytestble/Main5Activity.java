package illuminometer.com.example.android.mytestble;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {
    public static TextView alarmtext;
    private AlarmManager alarmmanager;
    AlarmReciver reciver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        alarmtext=(TextView) findViewById(R.id.alarmtext);
        alarmmanager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmReciver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmmanager.set(AlarmManager.RTC_WAKEUP,1000,pendingIntent);
        IntentFilter filter=new IntentFilter("com.");
        reciver=new AlarmReciver();
        registerReceiver(reciver,filter);
    }
    public  static class AlarmReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            alarmtext.setText("13213");
        }
    }
}
