package com.highway.study.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.highway.study.R;
import com.highway.study.util.Logger;

import java.util.Set;

public class BluetoothActivity extends AppCompatActivity implements AcceptInterface, ConnectInterface {

    private static final String TAG = "BluetoothActivity";
    private static final int REQUEST_ENABLE_BT = 1001;
    private BluetoothAdapter bluetoothAdapter;
    private ListView listView;
    private ArrayAdapter<String> mArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        listView = (ListView) findViewById(R.id.listview);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(mArrayAdapter);
        //1.获取BluetoothAdapter;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            //2.启用蓝牙
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                //查询配对信息
                findDevice();
                discoverable();
            }
        } else {
            // 不支持蓝牙
            Toast.makeText(this, "不支持蓝牙设备", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 查询配对信息
     */
    private void findDevice() {
        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
        if (bluetoothDevices.size() > 0) {
            for (BluetoothDevice device : bluetoothDevices){
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                Logger.e(TAG, "name = " + device.getName() + ":address = " + device.getAddress());
            }
        }
        //检测设备
        bluetoothAdapter.startDiscovery();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e(TAG, "授权成功" + (resultCode == RESULT_OK));
        if (requestCode == RESULT_OK) {

        } else {

        }
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    /**
     * 设置当前设备可被其他设备检测
     *  应用可以设置的最大持续时间为 3600 秒，值为 0 则表示设备始终可检测到。 任何小于 0 或大于 3600 的值都会自动设为 120 秒
     */
    private void discoverable() {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(discoverableIntent);
    }

    @Override
    public void acceptSocket(BluetoothSocket socket) {

    }


    @Override
    public void connectSocket(BluetoothSocket socket) {

    }
}
