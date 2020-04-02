package com.example.websocketchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private WebSocketClient webSocketClient;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWebSocketClient();
        textView = findViewById(R.id.userResult);
        ImageButton imageButton1 = findViewById(R.id.user1Button);
        ImageButton imageButton2 = findViewById(R.id.user2Button);
        ImageButton imageButton3 = findViewById(R.id.user3Button);
        ImageButton imageButton4 = findViewById(R.id.user4Button);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
    }

    private void createWebSocketClient() {
        URI uri;
        try {
            uri = new URI("ws://springchat2020.herokuapp.com/websocket");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("RRR", "Старт сессии...");
                webSocketClient.send("Hello!");
            }
            @Override
            public void onTextReceived(String s) {
                Log.i("RRR", "Сообщение получено...");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            textView.setText(message);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onBinaryReceived(byte[] data) {
            }
            @Override
            public void onPingReceived(byte[] data) {
            }
            @Override
            public void onPongReceived(byte[] data) {
            }
            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }
            @Override
            public void onCloseReceived() {
                Log.i("RRR", "Закрыто!");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    @Override
    public void onClick(View v) {
        Log.i("RRR", "Нажали на кнопку, пожалуйста подождите...");
        textView.setText("Загружается...");
        switch(v.getId()){
            case(R.id.user1Button):
                webSocketClient.send("1");
                break;

            case(R.id.user2Button):
                webSocketClient.send("2");
                break;

            case(R.id.user3Button):
                webSocketClient.send("3");
                break;

            case(R.id.user4Button):
                webSocketClient.send("4");
                break;
        }

    }
}
