package com.library.library.domain;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/9 15:09
 */
public class LoginValidateResult {
    private boolean	isOk;
    private String	msg;

    public LoginValidateResult() {
    }

    public static LoginValidateResult getFailure(String msg) {
      return new LoginValidateResult(false,msg);
    }

    public static LoginValidateResult getSuccess() {
        return new LoginValidateResult(true,null);
    }

    private LoginValidateResult(boolean isOk, String msg) {
        this.isOk = isOk;
        this.msg = msg;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
