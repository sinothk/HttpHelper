package com.sinothk.http.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sinothk.http.v1.HttpCallback;
import com.sinothk.http.v1.HttpManager;
import com.sinothk.http.v1.HttpResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpManager.post("https://www.baidu.com/", "", null, null, false, new HttpCallback() {
            @Override
            public void upComplete(HttpResult result) {
                if (result == null) {

                }
            }
        });
    }
}