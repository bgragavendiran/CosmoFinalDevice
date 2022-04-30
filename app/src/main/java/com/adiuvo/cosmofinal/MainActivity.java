package com.adiuvo.cosmofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.adiuvo.cosmofinal.Services.BTMain;

public class MainActivity extends AppCompatActivity {
    Button led0,led1,led2,led3,led4,led5,led6,depth;
    public String TAG="MainActivity.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BTMain btMain= BTMain.getInstance(getApplicationContext());
        led0=findViewById(R.id.led0);
        led1=findViewById(R.id.led1);
        led2=findViewById(R.id.led2);
        led3=findViewById(R.id.led3);
        led4=findViewById(R.id.led4);
        led5=findViewById(R.id.led5);
        led6=findViewById(R.id.led6);
        led0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+btMain.btSendCmd('0'));
            }
        });
        led1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+btMain.btSendCmd('1'));
            }
        });
        led2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+btMain.btSendCmd('2'));
            }
        });
        led3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+btMain.btSendCmd('3'));
            }
        });
        led4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+btMain.btSendCmd('4'));
            }
        });
        led5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+btMain.btSendCmd('5'));
            }
        });
        led6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+btMain.btSendCmd('6'));
            }
        });
    }
}