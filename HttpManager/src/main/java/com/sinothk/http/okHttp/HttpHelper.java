package com.sinothk.http.okHttp;

import android.graphics.Bitmap;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.sinothk.http.base.HttpHelperBase;
import com.sinothk.http.okHttp.callback.HttpCallback;
import com.sinothk.http.okHttp.callback.HttpProgressCallback;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.sinothk.http.BuildConfig.DEBUG;

/**
 * Created by 梁玉涛 on 2017/6/4 0004.
 * 功能：
 */
public class HttpHelper extends HttpHelperBase {

    /**
     * get获取服务器数据
     *
     * @param url
     * @param httpCallback
     */
    public static void get(String url, final HttpCallback httpCallback) {
        OkGo.get(url)     // 请求方式和请求url
                .tag(url)                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.SUCCESS, resultStr);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                        e.printStackTrace();
                    }
                });
        printLog(url);
    }

    /**
     * get获取实体数据
     *
     * @param url
     * @param cls
     * @param httpCallback
     */
    public static void get(String url, final Class<?> cls, final HttpCallback httpCallback) {
        OkGo.get(url)     // 请求方式和请求url
                .tag(url)                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        try {
                            if (httpCallback != null) {

                                if (cls == null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, resultStr);
                                    return;
                                }

                                Object obj = JSON.parseObject(resultStr, cls);
                                if (obj != null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, obj);
                                } else {
                                    httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                    }
                });
        printLog(url);
    }

    /**
     * get获取列表数据
     *
     * @param url
     * @param cls
     * @param httpCallback
     */
    public static void get4List(String url, final Class<?> cls, final HttpCallback httpCallback) {
        OkGo.get(url)     // 请求方式和请求url
                .tag(url)                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        try {
                            if (httpCallback != null) {
                                if (cls == null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, resultStr);
                                    return;
                                }

                                Object obj = JSON.parseArray(resultStr, cls);
                                if (obj != null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, obj);
                                } else {
                                    httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                                }
                            }
                        } catch (Exception e) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                        e.printStackTrace();
                    }
                });

        printLog(url);
    }

    /**
     * 普通Post
     *
     * @param url
     * @param httpParams
     * @param httpCallback
     */
    public static void post(String url, HttpParams httpParams, final HttpCallback httpCallback) {
        if (httpParams == null) {
            httpParams = new HttpParams();
        }

        OkGo.post(url)//
                .tag(url)//
                //	.params("param1", "paramValue1")//  这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
//                .upString("这是要上传的长文本数据！")//
                //	.upString("这是要上传的长文本数据！", MediaType.parse("application/xml")) // 比如上传xml数据，这里就可以自己指定请求头
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.SUCCESS, resultStr);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, final Exception e) {
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                    }
                });

        printLog(url);
    }

    /**
     * post获取列表数据
     *
     * @param url
     * @param cls
     * @param httpCallback
     */
    public static void post(String url, HttpParams httpParams, final Class<?> cls, final HttpCallback httpCallback) {
        if (httpParams == null) {
            httpParams = new HttpParams();
        }

        OkGo.post(url)//
                .tag(url)//
                //	.params("param1", "paramValue1")//  这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
//                .upString("这是要上传的长文本数据！")//
                //	.upString("这是要上传的长文本数据！", MediaType.parse("application/xml")) // 比如上传xml数据，这里就可以自己指定请求头
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        try {
                            if (httpCallback != null) {
                                if (cls == null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, resultStr);
                                    return;
                                }

                                Object obj = JSON.parseObject(resultStr, cls);
                                if (obj != null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, obj);
                                } else {
                                    httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                                }
                            }
                        } catch (Exception e) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                        e.printStackTrace();
                    }
                });

        printLog(url);
    }

    /**
     * post获取列表数据
     *
     * @param url
     * @param cls
     * @param httpCallback
     */
    public static void post4List(String url, HttpParams httpParams, final Class<?> cls, final HttpCallback httpCallback) {
        if (httpParams == null) {
            httpParams = new HttpParams();
        }

        OkGo.post(url)//
                .tag(url)//
                //	.params("param1", "paramValue1")//  这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
//                .upString("这是要上传的长文本数据！")//
                //	.upString("这是要上传的长文本数据！", MediaType.parse("application/xml")) // 比如上传xml数据，这里就可以自己指定请求头
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        try {
                            if (httpCallback != null) {
                                if (cls == null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, resultStr);
                                    return;
                                }

                                Object obj = JSON.parseArray(resultStr, cls);
                                if (obj != null) {
                                    httpCallback.onComplete(HttpConstants.SUCCESS, obj);
                                } else {
                                    httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                                }
                            }
                        } catch (Exception e) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, resultStr);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (httpCallback != null) {
                            httpCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                        e.printStackTrace();
                    }
                });

        printLog(url);
    }

    private static void printLog(String url) {
        if (DEBUG) {
            Log.e("HttpManager", "-----------------------------------");
            Log.e("HttpManager", "| url => " + url);
            Log.e("HttpManager", "-----------------------------------");
        }
    }

    public static void postJson(String url, String jsonStr, final HttpCallback callback) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("key1", "value1");
//        params.put("key2", "这里是需要提交的json格式数据");
//        params.put("key3", "也可以使用三方工具将对象转成json字符串");
//        params.put("key4", "其实你怎么高兴怎么写都行");

//        JSONObject jsonObject = new JSONObject(params);
//        jsonObject.toString();

        OkGo.post(url)//
                .tag(url)//
                .upJson(jsonStr)//
                .execute(new StringCallback() {
                    public void onSuccess(String resultStr, Call call, Response response) {
                        if (callback != null) {
                            callback.onComplete(HttpConstants.SUCCESS, resultStr);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, final Exception e) {
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
                        callback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                    }
                });
        printLog(url);
    }

//    /**
//     * 传JSON参数
//     *
//     * @param url
//     * @param tag
//     * @param jsonStr   参数
//     * @param dataClass data的类型，是基本数据类型就传 null
//     * @param isList    data的类型是list
//     * @param callback
//     */
//    public static void postJson(final Activity activity, String url, String tag, String jsonStr, final Class<?> dataClass, final boolean isList, final HttpCallback callback) {
////        HashMap<String, String> params = new HashMap<>();
////        params.put("key1", "value1");
////        params.put("key2", "这里是需要提交的json格式数据");
////        params.put("key3", "也可以使用三方工具将对象转成json字符串");
////        params.put("key4", "其实你怎么高兴怎么写都行");
//////        JSONObject jsonObject = new JSONObject(params);
////        jsonObject.toString();
//        OkGo.post(url)//
//                .tag(tag)//
//                .upJson(jsonStr)//
//                .execute(new StringCallback() {
//                    public void onSuccess(String resultStr, Call call, Response response) {
//                        HttpResult httpResult = JSONObject.parseObject(resultStr, HttpResult.class);
//
//                        if (dataClass == null) {
//                            // 基本数据类型
//                            callback.onComplete(httpResult);
//                            return;
//                        }
//
//                        // data是Bean
//                        if (httpResult.result != null && !"".equals(httpResult.result.toString())) {
//                            if (isList) {
//                                httpResult.result = JSON.parseArray(httpResult.result.toString(), dataClass);
//                            } else {
//                                httpResult.result = JSON.parseObject(httpResult.result.toString(), dataClass);
//                            }
//                        }
//
//                        callback.onComplete(httpResult);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, final Exception e) {
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                callback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage().toString()));
//                            }
//                        });
//                    }
//                });
//        printLog(url);
//    }

    /**
     * 上传一个文件和参数
     *
     * @param url
     * @param httpParams
     * @param callback
     */
    public static void postParamsAndFile(String url, HttpParams httpParams, File file, final Class<?> cls, final HttpProgressCallback callback) {
        OkGo.post(url)//
                .tag(url)//
                .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
//                .params("param1", "paramValue1") 		// 这里可以上传参数
                .params("fileObj", file)   // 可以添加文件上传
//                .params("file2", new File("filepath2")) 	// 支持多文件同时添加上传
//                .addFileParams("key", List<File> files)	// 这里支持一个key传多个文件
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        try {
                            if (cls == null) {
                                callback.onComplete(HttpConstants.SUCCESS, resultStr);
                                return;
                            }

                            Object obj = JSONObject.parseObject(resultStr, cls);
                            if (obj != null) {
                                callback.onComplete(HttpConstants.SUCCESS, obj);
                            } else {
                                callback.onComplete(HttpConstants.APP_ERROR, resultStr);
                            }
                        } catch (Exception e) {
                            if (callback != null) {
                                callback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                        if (callback != null) {
                            callback.onProgress(currentSize, totalSize, progress, networkSpeed);
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        if (callback != null) {
                            callback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                        e.printStackTrace();
                    }
                });
        printLog(url);
    }

    /**
     * 上传多个文件和参数
     *
     * @param url
     * @param httpParams
     * @param fileList
     * @param cls
     * @param callback
     */
    public static void postParamsAndFileList(String url, HttpParams httpParams, List<File> fileList, final Class<?> cls, final HttpProgressCallback callback) {
        if (httpParams == null) {
            httpParams = new HttpParams();
        }

        OkGo.post(url)//
                .tag(url)//
                .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
//                .params("param1", "paramValue1") 		// 这里可以上传参数
//                .params("file1", new File("filepath1"))   // 可以添加文件上传
//                .params("file2", new File("filepath2")) 	// 支持多文件同时添加上传
                .addFileParams("fileList", fileList)    // 这里支持一个key传多个文件
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        try {
                            if (cls == null) {
                                callback.onComplete(HttpConstants.SUCCESS, resultStr);
                                return;
                            }

                            Object obj = JSONObject.parseObject(resultStr, cls);
                            if (obj != null) {
                                callback.onComplete(HttpConstants.SUCCESS, obj);
                            } else {
                                callback.onComplete(HttpConstants.APP_ERROR, resultStr);
                            }
                        } catch (Exception e) {
                            callback.onComplete(HttpConstants.APP_ERROR, resultStr);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                        callback.onProgress(currentSize, totalSize, progress, networkSpeed);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        if (callback != null) {
                            callback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        }
                        e.printStackTrace();
                    }
                });
        printLog(url);
    }

    /**
     * 下载图片
     *
     * @param url
     * @param httpProgressCallback
     */
    public static void downloadBitmap(String url, final HttpCallback httpProgressCallback) {

        OkGo.get(url).tag(url).execute(new BitmapCallback() {
            @Override
            public void onSuccess(Bitmap bitmap, Call call, Response response) {
                // bitmap 即为返回的图片数据
                if (bitmap == null) {
                    httpProgressCallback.onComplete(HttpConstants.APP_ERROR, bitmap);
                } else {
                    httpProgressCallback.onComplete(HttpConstants.SUCCESS, bitmap);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                httpProgressCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url
     * @param path
     * @param fileName
     * @param httpProgressCallback
     */
    public static void downloadFile(String url, String path, String fileName, final HttpProgressCallback httpProgressCallback) {
        OkGo.get(url).tag(url)//
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new FileCallback(path, fileName) {  //文件下载时，可以指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        if (file == null) {
                            httpProgressCallback.onComplete(HttpConstants.APP_ERROR, "文件不存在");
                        } else {
                            httpProgressCallback.onComplete(HttpConstants.SUCCESS, file);
                        }
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        httpProgressCallback.onProgress(currentSize, totalSize, progress, networkSpeed);
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
//                        Log.e("downloadProgress", currentSize + "/" + totalSize + "; progress:" + progress + "; networkSpeed = " + networkSpeed);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        httpProgressCallback.onComplete(HttpConstants.APP_ERROR, e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

//    /**
//     * 下载图片或文件
//     *
//     * @param url
//     * @param tag
//     * @return
//     */
//    public static GetRequest downloads(String url, String tag) {
//        return OkGo.get(url).tag(tag);
//        OkGo.get(url).tag(tag).execute(new BitmapCallback() {
//            @Override
//            public void onSuccess(Bitmap bitmap, Call call, Response response) {
//                // bitmap 即为返回的图片数据
//            }
//
//            @Override
//            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                //这里回调上传进度(该回调在主线程,可以直接更新ui)
//                httpProgressCallback.upProgress(currentSize, totalSize, progress, networkSpeed);
//            }
//        });
// ==============================================================================================================================
    //  FileCallback():空参构造
//        FileCallback(String destFileName):可以额外指定文件下载完成后的文件名
//        FileCallback(String destFileDir, String destFileName):可以额外指定文件的下载目录和下载完成后的文件名

//        OkGo.get(Urls.URL_DOWNLOAD)//
//                .tag(this)//
//                .execute(new FileCallback() {  //文件下载时，可以指定下载的文件目录和文件名
//                    @Override
//                    public void onSuccess(File file, Call call, Response response) {
//                        // file 即为文件数据，文件保存在指定目录
//                    }
//
//                    @Override
//                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
//                    }
//                });
    // =====================================使用=======================================
//        HttpManager.download("", "").execute(new BitmapCallback() {
//            @Override
//            public void onSuccess(Bitmap bitmap, Call call, Response response) {
//
//            }
//
//            @Override
//            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                //这里回调上传进度(该回调在主线程,可以直接更新ui)
//            }
//        });
//
//        HttpManager.download("", "").execute(new FileCallback("", "") {
//            @Override
//            public void onSuccess(File file, Call call, Response response) {
//
//            }
//
//            @Override
//            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                //这里回调上传进度(该回调在主线程,可以直接更新ui)
//            }
//        });
//    }
}
