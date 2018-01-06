package illuminometer.com.example.android.mytestble;

import java.util.ArrayList;

/**
 *
 */
public class Ble_Cachedata_handle {
    private byte[] first_data = null;
    private byte[] second_data = null;
//    private int[] third_data = null;
//    private int[] forth_data = null;

    public byte[] getFirst_data() {
        return first_data;
    }
    /**
     *
     * @return   整个 处理好的数据 若不为null则可以一直 取出
     */
    public byte[] getComplete_Data() {
        return Complete_Data;
    }

    public void setComplete_Data(byte[] complete_Data) {
        Complete_Data = complete_Data;
    }

    private byte[] Complete_Data = null;

    /**
     *
     * @param  data  整个的数据解析
     * @return
     */

    public void rece_all_data_handle(byte[] data)
    {
        if((data[0]==0x55)) {
//            switch(data[1])
//            {
//                case 0x57:  this.first_data  = data; break;
//                case 0x58:  this.second_data = data; break;
//                case 0x59:  this.third_data  = data; break;
//                case 0x60:  this.forth_data  = data; break;
//            }
            //这是分的第一个包
            this.first_data =data;
        }else if (data[0]!=0x55){
            //这是第二个分的包
            this.second_data =data;
        }
    }

    public byte[] get_complete_receData() {
        if ((this.first_data != null) && (this.second_data != null)) {
            this.Complete_Data = this.combine_two_intdata(this.first_data, this.second_data);// 第一个包和 第二个包结合
//            this.Complete_Data = this.combine_two_intdata(this.Complete_Data,this.third_data);// 第三包结合
//            this.Complete_Data = this.combine_two_intdata(this.Complete_Data,this.forth_data);// 第四个包结合
            return this.Complete_Data;
        } else {
            return null;
        }
    }

    /**
     *
     * @param a  结合前面的int型数组
     * @param b  结合后面的int型数组
     * @return   a+b的结合的int型数组
     */
    public byte[] combine_two_intdata(byte[] a, byte[] b) {

        ArrayList<Byte> alist = new ArrayList<>(a.length + b.length);

        for (int j = 0; j < a.length; j++) {
            alist.add(a[j]);
        }

        for (int k = 0; k < b.length; k++) {
            alist.add(b[k]);
        }

        byte c[] = new byte[alist.size()];

        for (int i = 0; i < alist.size(); i++) {
            c[i] = alist.get(i);
        }
        return c;
    }
}
