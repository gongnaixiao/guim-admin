package com.sinoiift.domain.result;

/**
 * Created by xg on 2017/12/31.
 */
public class Result {
    private boolean success = true;
    private String msg = "";

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return this.success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return this.msg;
    }
}
