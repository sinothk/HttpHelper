package com.sinothk.http.v3;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.GetRequest;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 梁玉涛 on 2017/6/4 0004.
 * 功能：
 */
public class HttpManager {
    private static Activity currActivity;

    public static void cancel(String tag) {
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(tag);
    }

    public static void cancelAll() {
        //取消所有请求
        OkGo.getInstance().cancelAll();
    }

    public static void init(Application mainApplication) {
//        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
//        //-----------------------------------------------------------------------------------//
        //必须调用初始化
        OkGo.init(mainApplication);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//              .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates();                                 //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

            //这两行同上，不需要就不要加入
//                    .addCommonHeaders(headers)  //设置全局公共头
//                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCommonHeader(HttpHeaders headers) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
        OkGo.getInstance().addCommonHeaders(headers);
    }

//    public static HttpManager start(Activity thisActivity) {
//        currActivity = thisActivity;
//        return new HttpManager();
//    }

    /**
     * 执行 get
     *
     * @param url
     * @param tag
     * @param httpCallback
     */
    public static void get(String url, String tag, final HttpCallback httpCallback) {
        OkGo.get(url)     // 请求方式和请求url
                .tag(tag)                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        if (httpCallback != null) {
                            HttpResult httpResult = JSONObject.parseObject(resultStr, HttpResult.class);
                            httpCallback.onComplete(httpResult);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        currActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                        httpCallback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage()));
//                            }
//                        });
                    }
                });
    }

    /**
     * 普通Post
     *
     * @param url
     * @param tag
     * @param httpParams
     * @param callback
     */
    public static void post(String url, String tag, HttpParams httpParams, final HttpCallback callback) {
        if (httpParams == null) {
            httpParams = new HttpParams();
        }

        OkGo.post(url)//
                .tag(tag)//
                //	.params("param1", "paramValue1")//  这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
//                .upString("这是要上传的长文本数据！")//
                //	.upString("这是要上传的长文本数据！", MediaType.parse("application/xml")) // 比如上传xml数据，这里就可以自己指定请求头
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        if (callback != null) {
                            HttpResult httpResult = JSONObject.parseObject(resultStr, HttpResult.class);
                            callback.onComplete(httpResult);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, final Exception e) {
                        if (callback != null) {
                            callback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage().toString()));
                        }
                    }
                });

        printLog(url);
    }

    /**
     * 传HttpParams参数，
     *
     * @param url        url
     * @param tag        取消标签
     * @param httpParams 参数
     * @param dataClass  data的类型，是基本数据类型就传 null
     * @param isList     data的类型是list
     * @param callback
     */
    public static void post(String url, String tag, HttpParams httpParams, final Class<?> dataClass, final boolean isList, final HttpCallback callback) {
        if (httpParams == null) {
            httpParams = new HttpParams();
        }

        OkGo.post(url)//
                .tag(tag)//
                //	.params("param1", "paramValue1")//  这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
//                .upString("这是要上传的长文本数据！")//
//	.upString("这是要上传的长文本数据！", MediaType.parse("application/xml")) // 比如上传xml数据，这里就可以自己指定请求头
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        try {
                            HttpResult httpResult = JSONObject.parseObject(resultStr, HttpResult.class);
                            if (dataClass == null && callback != null) {
                                // 基本数据类型
                                callback.onComplete(httpResult);
                                return;
                            }

                            // data是Bean
                            if (httpResult.result != null && !"".equals(httpResult.result.toString())) {
                                if (isList) {
                                    httpResult.result = JSON.parseArray(httpResult.result.toString(), dataClass);
                                } else {
                                    httpResult.result = JSON.parseObject(httpResult.result.toString(), dataClass);
                                }
                            }

                            if (callback != null) {
                                callback.onComplete(httpResult);
                            }
                        } catch (Exception e) {
                            if (callback != null) {
                                callback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR_DATA, resultStr));
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, final Exception e) {
                        if (callback != null) {
                            callback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage().toString()));
                        }
                    }
                });

        printLog(url);
    }

    private static void printLog(String url) {
        Log.e("HttpManager", "-----------------------------------");
        Log.e("HttpManager", "| url => " + url);
        Log.e("HttpManager", "-----------------------------------");
    }


    public static void postJson(final Activity activity, String url, String tag, String jsonStr, final HttpCallback callback) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("key1", "value1");
//        params.put("key2", "这里是需要提交的json格式数据");
//        params.put("key3", "也可以使用三方工具将对象转成json字符串");
//        params.put("key4", "其实你怎么高兴怎么写都行");
////        JSONObject jsonObject = new JSONObject(params);
//        jsonObject.toString();
        OkGo.post(url)//
                .tag(tag)//
                .upJson(jsonStr)//
                .execute(new StringCallback() {
                    public void onSuccess(String resultStr, Call call, Response response) {
                        if (callback != null) {
                            HttpResult httpResult = new HttpResult();
                            httpResult.errcode = HttpResult.OK;
                            httpResult.result = resultStr;
                            callback.onComplete(httpResult);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, final Exception e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage().toString()));
                            }
                        });
                    }
                });
        printLog(url);
    }

    /**
     * 传JSON参数
     *
     * @param url
     * @param tag
     * @param jsonStr   参数
     * @param dataClass data的类型，是基本数据类型就传 null
     * @param isList    data的类型是list
     * @param callback
     */
    public static void postJson(final Activity activity, String url, String tag, String jsonStr, final Class<?> dataClass, final boolean isList, final HttpCallback callback) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("key1", "value1");
//        params.put("key2", "这里是需要提交的json格式数据");
//        params.put("key3", "也可以使用三方工具将对象转成json字符串");
//        params.put("key4", "其实你怎么高兴怎么写都行");
////        JSONObject jsonObject = new JSONObject(params);
//        jsonObject.toString();
        OkGo.post(url)//
                .tag(tag)//
                .upJson(jsonStr)//
                .execute(new StringCallback() {
                    public void onSuccess(String resultStr, Call call, Response response) {
                        HttpResult httpResult = JSONObject.parseObject(resultStr, HttpResult.class);

                        if (dataClass == null) {
                            // 基本数据类型
                            callback.onComplete(httpResult);
                            return;
                        }

                        // data是Bean
                        if (httpResult.result != null && !"".equals(httpResult.result.toString())) {
                            if (isList) {
                                httpResult.result = JSON.parseArray(httpResult.result.toString(), dataClass);
                            } else {
                                httpResult.result = JSON.parseObject(httpResult.result.toString(), dataClass);
                            }
                        }

                        callback.onComplete(httpResult);
                    }

                    @Override
                    public void onError(Call call, Response response, final Exception e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage().toString()));
                            }
                        });
                    }
                });
        printLog(url);
    }

    /**
     * 上传一个文件和参数
     *
     * @param url
     * @param tag
     * @param httpParams
     * @param httpProgressCallback
     */
    public static void postHttpParamsAndFile(String url, String tag, HttpParams httpParams, File file, final HttpProgressCallback httpProgressCallback) {
        OkGo.post(url)//
                .tag(tag)//
                .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
//                .params("param1", "paramValue1") 		// 这里可以上传参数
                .params("fileFlag", file)   // 可以添加文件上传
//                .params("file2", new File("filepath2")) 	// 支持多文件同时添加上传
//                .addFileParams("key", List<File> files)	// 这里支持一个key传多个文件
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        if (httpProgressCallback != null) {
                            HttpResult httpResult = new HttpResult();
                            httpResult.errcode = HttpResult.OK;
                            httpResult.result = resultStr;
                            httpProgressCallback.onComplete(httpResult);
                        }
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                        httpProgressCallback.onProgress(currentSize, totalSize, progress, networkSpeed);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpProgressCallback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage()));
                    }
                });
        printLog(url);
    }

    /**
     * 上传一个文件和参数
     *
     * @param url
     * @param tag
     * @param httpParams
     * @param dataClass
     * @param isList
     * @param httpProgressCallback
     */
    public static void postHttpParamsAndFile(String url, String tag, HttpParams httpParams, File file, final Class<?> dataClass, final boolean isList, final HttpProgressCallback httpProgressCallback) {
        OkGo.post(url)//
                .tag(tag)//
                .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
//                .params("param1", "paramValue1") 		// 这里可以上传参数
                .params("fileFlag", file)   // 可以添加文件上传
//                .params("file2", new File("filepath2")) 	// 支持多文件同时添加上传
//                .addFileParams("key", List<File> files)	// 这里支持一个key传多个文件
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        HttpResult httpResult = JSONObject.parseObject(resultStr, HttpResult.class);

                        if (dataClass != null) {// 基本数据类型
                            // data是Bean
                            if (httpResult.result != null && !"".equals(httpResult.result.toString())) {
                                if (isList) {
                                    httpResult.result = JSON.parseArray(httpResult.result.toString(), dataClass);
                                } else {
                                    httpResult.result = JSON.parseObject(httpResult.result.toString(), dataClass);
                                }
                            }
                        }

                        httpProgressCallback.onComplete(httpResult);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                        httpProgressCallback.onProgress(currentSize, totalSize, progress, networkSpeed);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpProgressCallback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage()));
                    }
                });
        printLog(url);
    }

    /**
     * 上传多个文件和参数
     *
     * @param url
     * @param tag
     * @param httpParams
     * @param fileList
     * @param dataClass
     * @param isList
     * @param httpProgressCallback
     */
    public static void postHttpParamsAndFileList(String url, String tag, HttpParams httpParams, List<File> fileList, final Class<?> dataClass, final boolean isList, final HttpProgressCallback httpProgressCallback) {
        OkGo.post(url)//
                .tag(tag)//
                .isMultipart(true)       // 强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
//                .params("param1", "paramValue1") 		// 这里可以上传参数
//                .params("file1", new File("filepath1"))   // 可以添加文件上传
//                .params("file2", new File("filepath2")) 	// 支持多文件同时添加上传
                .addFileParams("fileList", fileList)    // 这里支持一个key传多个文件
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String resultStr, Call call, Response response) {
                        HttpResult httpResult = JSONObject.parseObject(resultStr, HttpResult.class);

                        if (dataClass == null) {
                            // 基本数据类型
                            httpProgressCallback.onComplete(httpResult);
                            return;
                        }

                        // data是Bean
                        if (httpResult.result != null && !"".equals(httpResult.result.toString())) {
                            if (isList) {
                                httpResult.result = JSON.parseArray(httpResult.result.toString(), dataClass);
                            } else {
                                httpResult.result = JSON.parseObject(httpResult.result.toString(), dataClass);
                            }
                        }

                        httpProgressCallback.onComplete(httpResult);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                        httpProgressCallback.onProgress(currentSize, totalSize, progress, networkSpeed);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        httpProgressCallback.onComplete(HttpResult.getExceptionResult(HttpResult.ERROR, e.getMessage()));
                    }
                });
        printLog(url);
    }

    /**
     * 下载图片或文件
     *
     * @param url
     * @param tag
     * @return
     */
    public static GetRequest download(String url, String tag) {
        return OkGo.get(url).tag(tag);
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
    }
}
