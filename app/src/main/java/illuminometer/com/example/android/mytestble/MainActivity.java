package illuminometer.com.example.android.mytestble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import illuminometer.com.example.android.mytestble.view.MyThermometer;


public class MainActivity extends AppCompatActivity {
    public static Button start;
    public static String bLEDevAddress;     //蓝牙地址
    public static Boolean isCon;            //是否点击连接
    public static Boolean con;            //是否连接上
    public static Boolean isStart;          //是否开始
    public static BluetoothGatt bluetoothGatt;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    public static List<ArrayList<BluetoothGattCharacteristic>> gattCharacteristicList = new ArrayList<>();
    public static BluetoothGattCharacteristic characteristic;
    public static SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Short num;                  //总共多少条存储数据
    private Timer timer;          //计算器
    private TimerTask task;
    TextView tv1;                       //实时测量值
    TextView tv2;                      //最大值
    TextView tv3;
    TextView tv4;
    public static String type;           //测量类型
    LineChart  chart;
   // public static float[] list = new float[]{};
    public static ArrayList<Float> list=new ArrayList();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private illuminometer.com.example.android.mytestble.view.tempcontrolview.TempControlView tempControl;
    MyThermometer myThermometer;
    Canvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempControl = (illuminometer.com.example.android.mytestble.view.tempcontrolview.TempControlView) findViewById(R.id.temp_control);
        // 设置三格代表温度1度
        tempControl.setAngleRate(3);
        tempControl.setTemp(16, 37, 16);

        tempControl.setOnTempChangeListener(new illuminometer.com.example.android.mytestble.view.tempcontrolview.TempControlView.OnTempChangeListener() {
            @Override
            public void change(int temp) {
                Toast.makeText(MainActivity.this, temp + "°", Toast.LENGTH_SHORT).show();
            }
        });

        tempControl.setOnClickListener(new illuminometer.com.example.android.mytestble.view.tempcontrolview.TempControlView.OnClickListener() {
            @Override
            public void onClick(int temp) {
                Toast.makeText(MainActivity.this, temp + "°", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout ll=(LinearLayout) findViewById(R.id.ll);
        chart=(LineChart) findViewById(R.id.chart);
        start = (Button) findViewById(R.id.start);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3=(TextView) findViewById(R.id.tv3);
        tv4=(TextView) findViewById(R.id.tv4);
       // chart=(LineChart) findViewById(R.id.chart1);
        isCon = false;
        con = false;
        isStart = false;
        // 显示dialog进行连接
        CreateData();
        //createChart();
        if (sp.getString("bLEDevAddress", null) != null) {
            pair(sp.getString("bLEDevAddress", null));
        }
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick:1113113 ");
                tv1.setText("11223");
            }
        });
       start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,BluetoothActivity.class));
           }
       });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //自动配对
    private void pair(String strAddr) {
        //蓝牙设备适配器
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        //取消发现当前设备的过程
        bluetoothAdapter.cancelDiscovery();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
        if (!BluetoothAdapter.checkBluetoothAddress(strAddr)) { // 检查蓝牙地址是否有效
            Log.e("TAG", "pair: 8888777");
            //由蓝牙设备地址获得另一蓝牙设备对象
        }
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr);//得到连接地址
        bluetoothGatt = device.connectGatt(this, false, bluetoothGattCallback);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isCon) {
            Log.e("TAG", "onRestart: 88888123");
            initConnect();
        }
        isCon = false;
    }

    public void start_main(View view) {
        startActivity(new Intent(this, BluetoothActivity.class));
    }

    private void CreateData() {
        sp = getSharedPreferences("LuxMeter", MODE_PRIVATE);
        editor = sp.edit();
        if (sp.getString("interval_num", null) == null) {
            editor.putString("interval_num", "10");
        }
        if (sp.getString("Illum_unit", null) == null) {
            editor.putString("Illum_unit", "Lux");
        }
        if (sp.getString("Tem_unit", null) == null) {
            editor.putString("Tem_unit", "℃");
        }
        if (sp.getString("Alarm_Switch", null) == null) {
            editor.putString("Alarm_Switch", "false");
        }
        if (sp.getString("height_alarm", null) == null) {
            editor.putString("height_alarm", "3000");
        }
        if (sp.getString("low_alarm", null) == null) {
            editor.putString("low_alarm", "0");
        }
        editor.putString("auto_record", "AutoRecord");
        editor.putString("manual_record", "ManualRecord");
        editor.apply();

    }

    /**
     * 初始化连接
     */
    private void initConnect() {
        Log.e("TAG", "initConnect:8888 " + bLEDevAddress);
        bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(bLEDevAddress);
        bluetoothGatt = device.connectGatt(this, false, bluetoothGattCallback);
        editor.putString("bLEDevAddress", bLEDevAddress);
        editor.commit();
    }

    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        //返回连接状态
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.d("BLEDeviceTestActivity", "status:" + status);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                //连接成功
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1200);
                            con = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("TAG", "run: 88888成功");
                            }
                        });
                    }
                }.start();
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                //断开连接
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (con)
                            Log.e("TAG", "run: 8888失败");
                        bluetoothGatt.close();

                    }

                });
            }
        }

        //获取连接时GATT服务是的回调
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                gatt.getServices();
                gattCharacteristicList.clear();
                String uuid = null;
                ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
                ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
                for (BluetoothGattService gattService : gatt.getServices()) {// 遍历出gattServices里面的所有服务
                    HashMap<String, String> currentServiceData = new HashMap<String, String>();
                    uuid = gattService.getUuid().toString();
                    Log.e("TAG", "onServicesDiscovered: **************" + gattService.getUuid().toString());
                    currentServiceData.put("name",
                            SampleGattAttributes.lookup(uuid, "未知服务"));
                    currentServiceData.put("uuid", uuid);
                    gattServiceData.add(currentServiceData);
                    ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();
                    for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {//// 遍历每条服务里的所有Characteristic
                        charas.add(gattCharacteristic);
                        HashMap<String, String> currentCharaData = new HashMap<String, String>();
                        uuid = gattCharacteristic.getUuid().toString();
                        Log.e("TAG", "onServicesDiscovered:$$$$$" + gattCharacteristic.getUuid());
                        currentCharaData.put("name",
                                SampleGattAttributes.lookup(uuid, "未知特征"));
                        currentCharaData.put("uuid", uuid);
                        if (uuid.equals("0000ff02-0000-1000-8000-00805f9b34fb")) {//需要通信的UUID
                            setCharacteristicNotification(gattCharacteristic, true);//设置特征 true启用该通知对象
                        }
                        gattCharacteristicGroupData.add(currentCharaData);
                    }
                    gattCharacteristicList.add(charas);
                    gattCharacteristicData.add(gattCharacteristicGroupData);
                }
            }
        }

        //读取数据
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        //向特征写入数据
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        //设备发出通知时会调用该接口，连接成功时，回调
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            final byte[] data = characteristic.getValue();


            if (data != null && data.length > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(type=="1"){
                        float lux;
                        int luxe,gears,gear;
                        luxe=data[4]<<8|data[3]&0xff;
                        lux=luxe>>2;
                        gear=luxe&0x03;
                        if(gear==0){
                            gears=1;
                        }
                        else if(gear==1){
                            gears=10;
                        }
                        else if(gear==2){
                            gears=100;
                        }
                        else{gears=1000;}
                        tv2.setText(String.format("%.1f",lux*0.1f*gears));
                            tempControl.setTemp(0, 50, (int)(lux*0.1f*gears*0.1));}
                        if(type=="2"){
                            Log.e("TAG", "run: 手动存储成功" );
                        }
                    }
              });
           }

        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };

    //发送实时测量指令
    public static void SendInfo() {
        Log.e("TAG", "SendInfo:888888 " );
        type = "1";
        byte[] value = new byte[6];
        value[0] = (byte)0xAA;
        value[1] = 0x55;
        value[2] = 0x03;
        value[3] = 0x3C;
        value[4] = 0x01;
        value[5] = (byte)0xBB;
        characteristic = gattCharacteristicList.get(3).get(0);
        characteristic.setValue(value);
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        bluetoothGatt.writeCharacteristic(characteristic);
        characteristic = gattCharacteristicList.get(3).get(1);
    }

    // 发送最大值指令
    public static void sendHightInfo() {
        type = "max";
        byte[] value = new byte[6];
        value[0] = (byte) 0xAA;
        value[1] = 0x55;
        value[2] = 0x03;
        value[3] = 0x3C;
        value[4] = 0x02;
        value[5] = (byte) 0xBB;
        characteristic = gattCharacteristicList.get(3).get(1);
        characteristic.setValue(value);
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        bluetoothGatt.writeCharacteristic(characteristic);
       // characteristic = gattCharacteristicList.get(3).get(1);
    }

    //设置特征值
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID
                .fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));//特征配置
        if (descriptor != null) {
            System.out.println("write descriptor");
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);//设置其可以接收通知
            bluetoothGatt.writeDescriptor(descriptor);
        }
    }

    public void sendData(View view) {
        type="1";
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                SendInfo();
            }
        };
        timer.schedule(task, 100,2000);
    }
    public void sendVertral(View view){
        Log.e("TAG", "sendVertral: 8888发送" );
        byte[] value = new byte[7];
        value[0] = (byte)0xAA;
        value[1] = 0x55;
        value[2]=0x04;
        value[3] = 0x3C;
        value[4] = 0x09;
        value[5] = 0x05;
        value[6] = (byte)0xBB;
        characteristic = gattCharacteristicList.get(3).get(0);
        characteristic.setValue(value);
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        bluetoothGatt.writeCharacteristic(characteristic);
    }

public void stopTimer(){
    if (task != null) {
        task.cancel();
        task = null;
    }
    if (timer != null) {
       timer.cancel();
       timer = null;
}}
    public void startTimer(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                SendInfo();
            }
        };
        timer.schedule(task, 100,2000);


    }
    //求数组最大的数
    public float max(ArrayList<Float> lists) {
        float Max = lists.get(0);
        for(int i=0;i<lists.size();i++){
            if(lists.get(i)>Max){
                Max=lists.get(i);
            }
        }
      return Max;
        }

    public float min(ArrayList<Float> lists) {
        float Min = lists.get(0);
        for(int i=0;i<lists.size();i++){
            if(lists.get(i)<Min){
                Min=lists.get(i);
            }
        }
        return Min;
    }
    public void onSave(View view){
        startActivity(new Intent(this,SaveActivity.class));
    }
    public void onFile(View view){
        startActivity(new Intent(this,FileActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://illuminometer.com.example.android.mytestble/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://illuminometer.com.example.android.mytestble/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

   public void createChart(){
       chart.setNoDataTextDescription(" no data");
       chart.setDrawGridBackground(true);         // 是否显示表格颜色
       chart.setGridBackgroundColor(Color.BLUE);  // 表格的的颜色，在这里是是给颜色设置一个透明度
       chart.setTouchEnabled(true);                // 设置是否可以触摸
       chart.setDragEnabled(true);                 // 是否可以拖拽
       chart.setScaleEnabled(true);                // 是否可以缩放
       chart.setPinchZoom(false);
       chart.setBackgroundColor(Color.argb(10, 0xFF, 0xFF, 0xFF));// 设置背景
       LineData lineData = new LineData();
       chart.setData(lineData);                    // 设置数据
       Legend mLegend = chart.getLegend();

       Log.e("TAG", "onCreate:88888 " + mLegend);
       // 设置比例图标示，就是那个一组y的value的
       mLegend.setForm(Legend.LegendForm.LINE);  // 样式
       mLegend.setFormSize(6f);                     // 字体
       mLegend.setTextColor(Color.BLACK);          // 颜色
//        chart.animateX(2500);                       // 立即执行的动画,x轴
   }
    public void addEntry(float wind){
        //数据集制作
        LineData data = chart.getData();
        LineDataSet set = data.getDataSetByIndex(0);
        // 如果该统计折线图还没有数据集，则创建一条出来，如果有则跳过此处代码。
        if (set == null) {
            Log.e("TAG", "addEntry: 88888555555454");
            set = createLineDataSet();
            data.addDataSet(set);
        }
        Log.e("TAG", "addEntry: 88888888" + data.getXValCount());
        // 因为是从0开始，data.getXValCount()每次返回的总是全部x坐标轴上总数量，所以不必多此一举的加1

        data.addXValue((data.getXValCount() + 1) + "");
        // 如从0开始一样的数组下标，那么不必多次一举的加1
        Log.e("TAG", "addEntry: 8888" + set.getEntryCount());
        Entry entry = new Entry(wind, set.getEntryCount());
        data.addEntry(entry, 0);
        // 像ListView那样的通知数据更新
        chart.notifyDataSetChanged();
        // 当前统计图表中最多在x轴坐标线上显示的总量
        chart.setVisibleXRangeMaximum(25);
        // 此代码将刷新图表的绘图
        chart.moveViewToX(set.getEntryCount() - 9);
        chart.invalidate();
    }

    public LineDataSet createLineDataSet() {
        LineDataSet set = new LineDataSet(null, "lux");
        set.setLineWidth(1.75f); // 线宽
        set.setValueTextColor(Color.BLACK);
        set.setCircleSize(2f);// 显示的圆形大小
        set.setColor(Color.parseColor("#2B8AC2"));// 显示颜色
        set.setCircleColor(Color.parseColor("#2B8AC2"));// 圆形的颜色
        set.setHighLightColor(Color.parseColor("#2B8AC2")); // 高亮的线的颜色
        set.setDrawFilled(true);    //设置允许填充
        set.setFillColor(Color.parseColor("#2B8AC2"));
        return set;
    }

}

