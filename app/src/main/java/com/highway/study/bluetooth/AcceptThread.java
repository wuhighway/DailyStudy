package com.highway.study.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.highway.study.util.Utility;

import java.io.IOException;
import java.util.UUID;

/**
 * @author JH
 * @date 2017/8/30
 */


public class AcceptThread extends Thread {
    private BluetoothServerSocket serverSocket;
    private AcceptInterface acceptInterface;
    private UUID uuid = UUID.fromString("4cdbc040-657a-4847-b266-7e31d9e2c3d9,4cdbc040657a4847b2667e31d9e2c3d9");

    public AcceptThread(Context context, BluetoothAdapter mBluetoothAdapter, AcceptInterface acceptInterface) {
        BluetoothServerSocket tmp = null;
        this.acceptInterface = acceptInterface;
        try {
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(context.getPackageName(), Utility.uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = tmp;
    }

    @Override
    public void run() {
        super.run();
        BluetoothSocket socket = null;
        if (serverSocket != null) {
            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    acceptInterface.acceptSocket(socket);
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) { }
    }
}
