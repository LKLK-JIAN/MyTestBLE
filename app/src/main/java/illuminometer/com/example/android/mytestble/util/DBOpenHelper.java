package illuminometer.com.example.android.mytestble.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by android on 2017/10/13.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_PATH=android.os.Environment.getExternalStorageDirectory().
            getAbsolutePath()+"/mytestBLE";  //数据库路径
    private static final String DATABASE_NAME="mutestBLE.db";
    private static final int VERSION=1;


    public DBOpenHelper(Context context) {
        super(context, DATABASE_PATH+"/"+DATABASE_NAME,null ,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
          sqLiteDatabase.execSQL("create table document(_id integer primary key autoincrement,file_type varchar(10),file_title varchar(20)," +
                  "file_time varchar(20),remarks varchar(10),tem_unit varchar(10),lux_unit varchar(10))");
        sqLiteDatabase.execSQL("create table meterdata(_id integer primary key autoincrement,tem varchar(10),lux float,did integer)");
        Log.e("TAG", "onCreate:88888888888888数据库创建成功" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
