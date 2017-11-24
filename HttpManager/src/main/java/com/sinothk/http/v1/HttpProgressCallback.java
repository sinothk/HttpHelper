package com.sinothk.http.v1;

/**
 * Created by Administrator on 2017/6/4 0004.
 */

public interface HttpProgressCallback extends HttpCallback {
    void upProgress(long currentSize, long totalSize, float progress, long networkSpeed);
}
