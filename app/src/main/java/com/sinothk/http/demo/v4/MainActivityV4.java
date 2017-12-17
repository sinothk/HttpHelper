package com.sinothk.http.demo.v4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lzy.okgo.model.HttpParams;
import com.sinothk.http.demo.R;
import com.sinothk.http.v4.HttpCallback;
import com.sinothk.http.v4.HttpManager;
import com.sinothk.http.v4.HttpResult;

public class MainActivityV4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HttpManager.post("https://www.baidu.com/", null, null, false, new HttpCallback() {
//
//            @Override
//            public void onComplete(HttpResult result) {
//
//            }
//        });

        HttpManager.post("https://www.baidu.com/", new HttpParams(), new HttpCallback() {
            @Override
            public void onComplete(HttpResult result) {
                if (result == null) {

                }
            }
        });

//        HttpManager.get("url", "tag", new HttpCallback() {
//
//            @Override
//            public void upComplete(HttpResult result) {
//
//            }
//        });
    }
}
