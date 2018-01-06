package illuminometer.com.example.android.mytestble.domain;

import java.io.Serializable;

/**
 * Created by android on 2017/10/13.
 */
public class Document implements Serializable {
    private int id;
    private String file_type;
    private String file_title;
    private String file_time;
    private String remarks;
    private String tem_unit;
    private String lux_unit;

    public Document() {

    }

    public Document(String file_type, String file_title, String file_time, String remarks, String tem_unit, String lux_unit) {
        this.file_type = file_type;
        this.file_title = file_title;
        this.file_time = file_time;
        this.remarks = remarks;
        this.tem_unit = tem_unit;
        this.lux_unit = lux_unit;
    }

    public Document(int id, String file_type, String file_title, String file_time, String remarks, String tem_unit, String lux_unit) {
        this.id = id;
        this.file_type = file_type;
        this.file_title = file_title;
        this.file_time = file_time;
        this.remarks = remarks;
        this.tem_unit = tem_unit;
        this.lux_unit = lux_unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_title() {
        return file_title;
    }

    public void setFile_title(String file_title) {
        this.file_title = file_title;
    }

    public String getFile_time() {
        return file_time;
    }

    public void setFile_time(String file_time) {
        this.file_time = file_time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTem_unit() {
        return tem_unit;
    }

    public void setTem_unit(String tem_unit) {
        this.tem_unit = tem_unit;
    }

    public String getLux_unit() {
        return lux_unit;
    }

    public void setLux_unit(String lux_unit) {
        this.lux_unit = lux_unit;
    }
}
