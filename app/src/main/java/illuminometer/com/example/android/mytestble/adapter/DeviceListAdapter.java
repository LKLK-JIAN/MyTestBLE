package illuminometer.com.example.android.mytestble.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import illuminometer.com.example.android.mytestble.R;
import illuminometer.com.example.android.mytestble.holder.ViewHolder;

/**
 * 设备列表适配器
 *
 * @author Lyh
 */
public class DeviceListAdapter extends BaseAdapter {
	private List<BluetoothDevice> mBleArray;
	private ViewHolder viewHolder;
	private Context context;

	public DeviceListAdapter(Context context) {
		super();
		this.context = context;
		mBleArray = new ArrayList<BluetoothDevice>();
	}

	public void addDevice(BluetoothDevice device) {
		if (!mBleArray.contains(device)) {
			mBleArray.add(device);
		}
	}

	@Override
	public int getCount() {
		return mBleArray.size();
	}

	@Override
	public BluetoothDevice getItem(int position) {
		return mBleArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.bluetooth_item_list, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_devName = (TextView) convertView
					.findViewById(R.id.tv_devName);
			viewHolder.tv_devAddress = (TextView) convertView
					.findViewById(R.id.tv_devAddress);
			convertView.setTag(viewHolder);
		} else {
			convertView.getTag();
		}

		// add-Parameters
		Log.e("TAG", "getView: 00444444444444" );
		BluetoothDevice device = mBleArray.get(position);
		String devName = device.getName();
		if (devName != null && devName.length() > 0) {
			viewHolder.tv_devName.setText(devName);
		} else {
			viewHolder.tv_devName.setText("unknow-device");
		}
		viewHolder.tv_devAddress.setText(device.getAddress());

		return convertView;
	}

}
