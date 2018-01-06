package illuminometer.com.example.android.mytestble.domain;

/**
 * Created by android on 2017/10/13.
 */
public class MeterData {
    private int id;
    private String tem;
    private double lux;
    private int did;

    public MeterData(double lux) {     //保存光照强度
        this.lux = lux;
    }

    public MeterData(int id, String tem, double lux, int did) {
        this.id = id;
        this.tem = tem;
        this.lux = lux;
        this.did = did;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public double getLux() {
        return lux;
    }

    public void setLux(double lux) {
        this.lux = lux;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }
}
