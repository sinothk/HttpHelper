package com.sinothk.http.v4;

/**
 * Created by 梁玉涛 on 2017/12/15/015.
 * 功能描述：
 */

public class Response {
    private String rspCode;
    private String rspDesc;

    public Response() {
    }

    public Response(String rspCode, String rspDesc) {
        this.rspCode = rspCode;
        this.rspDesc = rspDesc;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }
}
