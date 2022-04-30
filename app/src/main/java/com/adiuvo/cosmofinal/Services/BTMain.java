package com.adiuvo.cosmofinal.Services;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BTMain {
    private static BTMain instance;
    final String TAG="BTMain";
    Context context;
    BluetoothDevice thedevice;
    BluetoothSocket thesocket;

    public BTMain(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
        if (BA.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "init: PERMISSION GRANTED");
                BA.enable();
                if (thedevice == null) {
                    Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            if (device.getName().equals("raspberrypi")) {
                                thedevice = device;
                            }
                        }
                    }
                }
                //If device is still null return 1
                if (thedevice == null) {
                    Log.d(TAG, "init: DEVICE NOT FOUND RECONNECTING");
                    init();
                }else{
                    Log.d(TAG, "init: Device Found Establishing Socket");
                    if (thesocket == null) {
                        try {
                            thesocket = thedevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("1e0ca4ea-299d-4335-93eb-27fcfe7fa848"));
                            if (!thesocket.isConnected())
                                thesocket.connect();
                                if(thesocket.isConnected()){
                                    Log.d(TAG, "init: Socket ESTABLISHED");
                                }
                                else{
                                    Log.d(TAG, "init: Socket is not found");
                                }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        else{

            Log.d(TAG, "init: No BlueTooth Permission");

        }
    }

    public static synchronized BTMain getInstance(Context context) {
        if (instance == null) {
            instance = new BTMain(context);
        }
        return instance;
    }
    public void deInit(){
        try {
            if (thesocket != null)
                thesocket.close();
            thesocket = null;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public int btSendCmd(Character send) {
        if(thesocket.isConnected()){
            OutputStream outpacket = null;
            try {
                outpacket = thesocket.getOutputStream();
                outpacket.write(send);
                InputStream inpacket = thesocket.getInputStream();
                int recv = inpacket.read();
                if (recv == send) {
                    return 0;
                } else {
                    return 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

    public int getdepth() {
        //Get the distance reading.
        try {
            OutputStream outpacket = thesocket.getOutputStream();
            InputStream inpacket = thesocket.getInputStream();
            outpacket.write('m');
            int recv = inpacket.read();
            return recv;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
