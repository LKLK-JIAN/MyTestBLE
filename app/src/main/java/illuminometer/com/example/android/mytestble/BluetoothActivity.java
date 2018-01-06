package illuminometer.com.example.android.mytestble;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import illuminometer.com.example.android.mytestble.adapter.DeviceListAdapter;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0;
    private ListView blueList;
    private Button btn1;
    private String bleAdress;
    public static List<ArrayList<BluetoothGattCharacteristic>> gattCharacteristicList = new ArrayList<>();
    private static BluetoothGatt  bluetoothGatt;
    public static BluetoothGattCharacteristic characteristic;
    private DeviceListAdapter mDevListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private static final long SCAN_PERIOD = 5000;// 扫描周期
    private Handler mHandler;
    BluetoothManager bluetoothManager;
    private boolean mScanning;// 扫描
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        blueList = (ListView) findViewById(R.id.list);
        mHandler = new Handler();
        btn1 = (Button) findViewById(R.id.btn1);
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        s();
        mDevListAdapter = new DeviceListAdapter(this);
        blueList.setAdapter(mDevListAdapter);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanLeDevice(true);
            }
        });
        blueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mDevListAdapter.getCount() > 0) {
                    BluetoothDevice device = mDevListAdapter.getItem(position);
                    bleAdress = device.getAddress();
                    Log.e("TAG", "onItemClick:888888882 ");
                    MainActivity.isCon = true;
                    MainActivity.bLEDevAddress=bleAdress;
                    boolean i=connect(bleAdress);
                    if(i){
                      finish();
                    }

                }
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean connect(String str){
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(str);
        if (device == null) {
            Log.w("TAG", "Device not found.  Unable to connect.");
            return false;
        }
        bluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d("TAG", "Trying to create a new connection.");
        return true;
    }

     private final BluetoothGattCallback  mGattCallback=new BluetoothGattCallback() {
         @Override
         public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
             super.onConnectionStateChange(gatt, status, newState);
             if(newState== BluetoothProfile.STATE_CONNECTED){
                 Log.e("TAG", "onConnectionStateChange: 88825");
             }
             else if(newState==BluetoothProfile.STATE_DISCONNECTED){
                 Log.e("TAG", "onConnectionStateChange: 888256" );
             }
         }

         @Override
         public void onServicesDiscovered(BluetoothGatt gatt, int status) {
             super.onServicesDiscovered(gatt, status);
             Log.d("MainActivity", "status:" + status);
             if (BluetoothGatt.GATT_SUCCESS == status) {
                 gatt.getServices();
                 gattCharacteristicList.clear();
                 String uuid = null;
                 ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
                 ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
                 for (BluetoothGattService gattService : gatt.getServices()) {// 遍历出gattServices里面的所有服务
                     HashMap<String, String> currentServiceData = new HashMap<String, String>();
                     uuid = gattService.getUuid().toString();
                     Log.e("TAG", "onServicesDiscovered: **************"+gattService.getUuid().toString());
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
                         Log.e("TAG", "onServicesDiscovered:$$$$$"+gattCharacteristic.getUuid());
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

         @Override
         public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
             super.onCharacteristicRead(gatt, characteristic, status);
         }

         @Override
         public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
             super.onCharacteristicWrite(gatt, characteristic, status);
         }

         @Override
         public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
             super.onCharacteristicChanged(gatt, characteristic);
         }

     };

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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run:00003333333");
                    mDevListAdapter.addDevice(device);
                    mDevListAdapter.notifyDataSetChanged();
                }
            });
        }
    };


    /**
     * @Description: 搜索设备
     */
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run: out00002222222");
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);

                }
            }, 10000);
            Log.e("TAG", "scanLeDevice:000000start111111 ");
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);

        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    public void Blue_back(View view) {

        finish();
    }


    //动态请求权限
    public void s() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//判断是否需要 向用户解释，为什么要申请该权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
            }
        }
//权限申请结果

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Bluetooth Page", // TODO: Define a title for the content shown.
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
                "Bluetooth Page", // TODO: Define a title for the content shown.
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
}
