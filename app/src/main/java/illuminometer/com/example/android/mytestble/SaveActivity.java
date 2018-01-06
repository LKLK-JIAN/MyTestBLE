package illuminometer.com.example.android.mytestble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import illuminometer.com.example.android.mytestble.domain.Document;
import illuminometer.com.example.android.mytestble.domain.MeterData;
import illuminometer.com.example.android.mytestble.util.DBOpenHelperDatabase;

public class SaveActivity extends AppCompatActivity {
    public static List<MeterData>  data=new ArrayList<>();
    EditText  et_title,et_ramarks;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        et_title=(EditText) findViewById(R.id.et_title);
        et_ramarks=(EditText) findViewById(R.id.et_ramark);
        btn1=(Button)findViewById(R.id.btsave);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              save();
             finish();
            }
        });
    }
    public void save(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        DBOpenHelperDatabase database=new DBOpenHelperDatabase(SaveActivity.this);
        Document document=new Document("real",et_title.getText().toString(),str,et_ramarks.getText().toString(),"tem","lux");
        database.insertDocument(document);
        for(MeterData meterData:data){
            meterData.setDid(document.getId());
            database.insertMeterData(meterData);
        }
        database.closeDatabase();
        Log.e("TAG", "save: 88888888888" );
    }

}
