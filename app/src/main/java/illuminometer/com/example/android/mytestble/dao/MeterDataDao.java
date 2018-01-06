package illuminometer.com.example.android.mytestble.dao;

import android.database.Cursor;

import illuminometer.com.example.android.mytestble.domain.MeterData;

/**
 * Created by android on 2017/10/13.
 */
public interface MeterDataDao {
    void insertMeterData(MeterData meterData);
    void deleteMeterData(Integer id );
    void modifiyMeterData(Integer id);
    Cursor getALLMeterData(Integer id);
    String getMaxfs(Integer id);
    String getMinfs(Integer id);
    String getAVfs(Integer id);

}
