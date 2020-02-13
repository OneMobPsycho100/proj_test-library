package com.dio.frame.support.domain;

import java.io.Serializable;

public class Result<T> implements Serializable {

	public static final int	OK		= 200;	// 请求调用成功
	public static final int	FAILURE	= 500;	// 接口异常
	public static final int	REPEAT	= 500;	// 重复请求

	private static final long serialVersionUID = 1L;

	public static <T> Result<T> getSuccess() {
		return new Result<T>(Result.OK);
	}

	public static <T> Result<T> getSuccess(T t) {
		return new Result<T>(Result.OK, t);
	}


	public static <T> Result<T> getFailure(String msg) {
		return new Result<T>(Result.FAILURE, msg);
	}


	public static Result<Object> getFailure(int code, String msg) {
		return new Result<Object>(code, msg);
	}

	public static <T> Result<T> repeatFailure() {
		return new Result<T>(Result.REPEAT, "重复请求");
	}

	private int		status;	// 调用是否成功
	private String	msg;	// 失败原因

	private T result; // 返回具体结果

	public Result() {
		super();
	}

	public Result(int status) {
		super();
		this.status = status;
	}

	public Result(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public Result(int status, T result) {
		super();
		this.status = status;
		this.result = result;
	}

	// Getter and Setter
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
