package com.sinothk.http.demo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sinothk.http.okHttp.HttpConstants;
import com.sinothk.http.okHttp.HttpHelper;
import com.sinothk.http.okHttp.callback.HttpCallback;
import com.sinothk.http.okHttp.callback.HttpProgressCallback;

import java.io.File;

public class V5MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = this.findViewById(R.id.imageView);
    }

    public void test(View view) {
        HttpHelper.get("https://www.so.com/?src=so.com", new HttpCallback() {
            @Override
            public void onComplete(final int code, final Object result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code == HttpConstants.SUCCESS) {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        HttpHelper.get("https://www.so.com/?src=so.com", null, new HttpCallback() {
            @Override
            public void onComplete(final int code, final Object result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code == HttpConstants.SUCCESS) {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        HttpHelper.get4List("https://www.so.com/?src=so.com", null, new HttpCallback() {
            @Override
            public void onComplete(final int code, final Object result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code == HttpConstants.SUCCESS) {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        HttpHelper.post("https://www.so.com/?src=so.com", null, new HttpCallback() {
            @Override
            public void onComplete(final int code, final Object result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code == HttpConstants.SUCCESS) {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        HttpHelper.downloadBitmap("http://p1.so.qhimgs1.com/t011cd2f45483b7b3a1.jpg", new HttpCallback() {
            @Override
            public void onComplete(int code, Object result) {
                imageView.setImageBitmap((Bitmap) result);
            }
        });

        HttpHelper.downloadFile("http://banzou.cdn.aliyun.com/apk/autoupdate_1527820151_872.apk", new HttpProgressCallback() {
            @Override
            public void onComplete(final int code, final Object result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code == HttpConstants.SUCCESS) {
                            File f = (File) result;
                            Log.e("onComplete", f.getAbsolutePath());
                        } else {
                            Toast.makeText(V5MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                Log.e("downloadProgress", currentSize + "/" + totalSize + "; progress:" + progress + "; networkSpeed = " + networkSpeed);
            }
        });
    }
}
