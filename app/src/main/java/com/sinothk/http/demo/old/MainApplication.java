package com.sinothk.http.demo.old;

import android.app.Application;

import com.sinothk.http.v1.HttpManager;
import com.sinothk.http.v2.OHttpManager;

/**
 * Created by 梁玉涛 on 2017/11/25.
 * 功能：
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpManager.init(this);
        OHttpManager.init(this, BaseData.class);
    }
}
