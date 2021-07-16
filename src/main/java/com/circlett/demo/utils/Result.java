package com.circlett.demo.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

	private int state;
	private String msg;
	private Object data;

	public static Result succ(Object data) {
		return succ(1, "操作成功", data);
	}

	public static Result succ(int state, String msg, Object data) {
		Result r = new Result();
		r.setState(state);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static Result fail(String msg) {
		return fail(2, msg,null);
	}

	public static Result fail(int state, String msg, Object data) {
		Result r = new Result();
		r.setState(state);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

}
