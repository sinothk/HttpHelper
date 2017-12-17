package com.sinothk.http.v4;


public class HttpResult {

    public String processTime;//当前时间戳
    public String userName;//当前用户账号
    public String imei;//设备号
    public String bipVer; // 版本名称

    public String bipCode; // 模块
    public String activityCode;//方法编号

    public Response response;//返回结果
    public String secretKey;//当前用户密钥
    public String testFlag;//请求方法耗时
    public Object serviceContent; // 结果数据

    public String result;

    /**
     * 常量
     */
    public static String SUCCESS = "0000";//其他失败
    public static String ERROR_LOCAL = "1"; // 本地错误
    public static String ERROR_DATA = "2";// 数据格式错误

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBipVer() {
        return bipVer;
    }

    public void setBipVer(String bipVer) {
        this.bipVer = bipVer;
    }

    public String getBipCode() {
        return bipCode;
    }

    public void setBipCode(String bipCode) {
        this.bipCode = bipCode;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag;
    }

    public Object getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(Object serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static HttpResult getExceptionResult(String code, String msg) {
        HttpResult res = new HttpResult();
        res.setResponse(new Response(code, msg));
        return res;
    }

}
