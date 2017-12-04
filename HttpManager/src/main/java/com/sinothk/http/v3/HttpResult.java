package com.sinothk.http.v3;


public class HttpResult {

    public int errcode;
    public String errmsg;
    public Object result;

    public int startIndex = 0;
    public int pageSize = 0;
    public int dataCount = 0;
    /**
     * 常量
     */
    public static int OK = 200;
    public static int ERROR = 1; // 本地错误
    public static int ERROR_DATA = 2;// 数据格式错误

    public static int Exception = -1;
    public static int APP_ERROR = 30;
    public static int SERVER_ERROR = 60;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public static HttpResult getExceptionResult(int code, String msg) {
        HttpResult res = new HttpResult();
        res.errcode = code;
        res.errmsg = msg;
        return res;
    }
}
