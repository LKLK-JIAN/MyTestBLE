package illuminometer.com.example.android.mytestble.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import illuminometer.com.example.android.mytestble.dao.DocumentDao;
import illuminometer.com.example.android.mytestble.dao.MeterDataDao;
import illuminometer.com.example.android.mytestble.domain.Document;
import illuminometer.com.example.android.mytestble.domain.MeterData;

/**
 * Created by android on 2017/10/13.
 */
public class DBOpenHelperDatabase extends DBOpenHelper implements DocumentDao,MeterDataDao{
    private SQLiteDatabase database=null;
    public DBOpenHelperDatabase(Context context) {
        super(context);
        database=getReadableDatabase();
        Log.e("TAG", "DBOpenHelperDatabase: 88888888"+database );
    }
    public void closeDatabase(){
        if(database!=null)
            database.close();
    }

    @Override
    public void insertDocument(Document document) {
          database.execSQL("insert into document(file_type,file_title,file_time,remarks,tem_unit,lux_unit) values (?,?,?,?,?,?)",
                  new Object[]{document.getFile_type(),document.getFile_title(),document.getFile_time(),document.getRemarks(),document.getTem_unit(),document.getLux_unit()});
    }

    @Override
    public void deleteDocument(Integer documentid) {

    }

    @Override
    public void modifiyDocument(Integer documentid) {

    }

    @Override
    public Cursor getAllDocument() {
        return database.rawQuery("select * from document order by _id desc",null);
    }

    @Override
    public Document getDocument(Integer documentid) {
        return null;
    }

    @Override
    public Document getTopDocument() {
        return null;
    }

    @Override
    public void insertMeterData(MeterData meterData) {
        database.execSQL("insert into meterData(tem,lux,did) values (?,?,?)",new Object[]{meterData.getTem(),meterData.getLux(), meterData.getDid()});
    }

    @Override
    public void deleteMeterData(Integer id) {

    }

    @Override
    public void modifiyMeterData(Integer id) {

    }

    @Override
    public Cursor getALLMeterData(Integer id) {
        return null;//
    }

    @Override
    public String getMaxfs(Integer id) {
        return null;
    }

    @Override
    public String getMinfs(Integer id) {
        return null;
    }

    @Override
    public String getAVfs(Integer id) {
        return null;
    }
}
