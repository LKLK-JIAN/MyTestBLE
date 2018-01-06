package illuminometer.com.example.android.mytestble;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import illuminometer.com.example.android.mytestble.adapter.FileListAdapter;
import illuminometer.com.example.android.mytestble.domain.Document;
import illuminometer.com.example.android.mytestble.util.DBOpenHelperDatabase;

public class FileActivity extends AppCompatActivity {
    ListView list1;
    List<Document> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        list=new ArrayList<>();
        list1=(ListView) findViewById(R.id.document_lv);
        getData();
        Log.e("TAG", "onCreate: 888888888888"+list.size() );
        FileListAdapter listAdapter=new FileListAdapter(this,list);
        list1.setAdapter(listAdapter);
        Log.e("TAG", "onCreate:8888document数据填充完成 " );
    }
    public void getData(){
      list.clear();
        DBOpenHelperDatabase database=new DBOpenHelperDatabase(this);
        Cursor cursor=database.getAllDocument();
        while(cursor.moveToNext()){
            Document document=new Document();
            document.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            document.setFile_type(cursor.getString(cursor.getColumnIndex("file_type")));
            document.setFile_title(cursor.getString(cursor.getColumnIndex("file_title")));
            document.setFile_time(cursor.getString(cursor.getColumnIndex("file_time")));
            document.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
            document.setTem_unit(cursor.getString(cursor.getColumnIndex("tem_unit")));
            document.setLux_unit(cursor.getString(cursor.getColumnIndex("lux_unit")));
            list.add(document);
    }
        database.closeDatabase();
}}
