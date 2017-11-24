package com.sinothk.http.v1;


public class HttpResult {
    public int code;
    public Object data;
    public String msg;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        res.code = code;
        res.msg = msg;
        return res;
    }
}
