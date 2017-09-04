package com.highway.study.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.highway.study.util.Utility;

import java.io.IOException;

/**
 * @author JH
 * @date 2017/8/30
 */


public class ConnectThread extends Thread {
    private  BluetoothSocket mmSocket;
    private  BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    ConnectInterface connectInterface;
    public ConnectThread(BluetoothDevice device, BluetoothAdapter mBluetoothAdapter, ConnectInterface connectInterface) {
        this.mBluetoothAdapter = mBluetoothAdapter;
        this.connectInterface = connectInterface;
        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            tmp = device.createRfcommSocketToServiceRecord(Utility.uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmSocket = tmp;
    }

    public void run() {
        mBluetoothAdapter.cancelDiscovery();

        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        connectInterface.connectSocket(mmSocket);
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
