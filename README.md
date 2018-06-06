# android-http
android http

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
