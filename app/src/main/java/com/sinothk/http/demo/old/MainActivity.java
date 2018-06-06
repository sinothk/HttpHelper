package com.sinothk.http.demo.old;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sinothk.http.demo.R;
import com.sinothk.http.v2.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OHttpManager.post("https://www.baidu.com/", null, new OHttpManager.OHttpCallback() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object o) {
                BaseData baseData = (BaseData) o;
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }


}
