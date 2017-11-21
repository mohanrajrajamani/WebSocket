package com.mindnotix.websocket;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button tv;
    File myExternalFile;
    private String filename = "SocketLog.txt";
    private String filepath = "MyFileStorage";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv = (Button) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWebSocketClient(new Random().nextInt());
            }
        });


    }


    private WebSocketClient webSocketClient;

    private void createWebSocketClient(final int i) {


        URI uri;
        try {
            uri = new URI("ws://navacabs.com:5000?device=A&app=D");
            Log.d(TAG, "createWebSocketClient: ");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }


        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                System.out.println("onOpen");
                Log.d(TAG, "onOpen: ");
                webSocketClient.send("Hello, World! " + i);

            }

            @Override
            public void onTextReceived(String message) {
//                System.out.println("onTextReceived" + message);
//
//                String separator = "/SocketLog/a.txt";
//                String filePath = Environment.getExternalStorageDirectory().getPath() + separator;
//                Log.d(TAG, "onTextReceived: " + filePath);
//                String msgToSave = "\n" + TimeUtils.getCurrentTimeInString() + " " + message;
//                FileUtils.writeFile(filePath, msgToSave, true);
//





             /*   String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera";
                Log.d("Files", "Path: " + path);
                File directory = new File(path);
                File[] files = directory.listFiles();
                Log.d("Files", "Size: "+ files.length);
                for (int i = 0; i < files.length; i++)
                {
                    Log.d("Files", "FileName:" + files[i].getName());
                }*/



            }

            @Override
            public void onBinaryReceived(byte[] data) {
                System.out.println("onBinaryReceived");
                Log.d(TAG, "onBinaryReceived: ");
            }

            @Override
            public void onPingReceived(byte[] data) {
                System.out.println("onPingReceived");
                Log.d(TAG, "onPingReceived: ");
            }

            @Override
            public void onPongReceived(byte[] data) {
                System.out.println("onPongReceived");

                Log.d(TAG, "onPongReceived: ");
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
                Log.d(TAG, "onException: ");
            }

            @Override
            public void onCloseReceived() {
                System.out.println("onCloseReceived");
                Log.d(TAG, "onCloseReceived: ");
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        // webSocketClient.addHeader("Origin", "http://developer.example.com");
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}
