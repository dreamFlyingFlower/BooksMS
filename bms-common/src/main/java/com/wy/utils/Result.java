package com.wy.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Result implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code;
	private String message;
	private Object data;
	private int pageIndex;
	private int pageSize;
	private int total;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public static Result resultOk() {
		return resultOk(null);
	}

	public static Result resultErr() {
		return resultErr(null);
	}

	public static Result resultOk(Object t) {
		return resultOk(null, t);
	}

	public static Result resultErr(Object t) {
		return resultErr(null, t);
	}

	public static Result resultOk(String message) {
		return resultOk(message, null);
	}

	public static Result resultErr(String message) {
		return resultErr(message, null);
	}

	public static Result resultOk(String message, Object t) {
		return result(1, message, t);
	}

	public static Result resultErr(String message, Object t) {
		return result(0, message, t);
	}

	public static Result resultOk(int code, String message) {
		return result(code, message, null);
	}

	public static Result resultErr(int code, String message) {
		return result(code, message, null);
	}

	public static Result resultOk(int code, Object t) {
		return result(code, "请求成功", t);
	}

	public static Result resultErr(int code, Object t) {
		return result(code, "请求失败", t);
	}

	public static Result result(boolean flag) {
		return flag ? resultOk() : resultErr();
	}

	public static Result result(Object t) {
		return t == null ? resultErr() : resultOk(t);
	}

	public static Result result(int code, String message, Object t) {
		return result(code, message, t, 0, 0, 0);
	}

	public static Result result(Object t, int pageIndex, int pageSize, int total) {
		return Objects.isNull(t) ? result(0, null, null, 0, 0, 0)
				: result(1, null, t, pageIndex, pageSize, total);
	}

	public static Result result(int code, String message, Object t, int pageIndex, int pageSize, int total) {
		Result res = new Result();
		res.message = StrUtils.isBlank(message) ? (code > 0 ? "请求成功" : "请求失败") : message;
		res.code = code;
		res.data = t;
		res.pageIndex = pageIndex;
		res.pageSize = pageSize;
		res.total = total;
		return res;
	}

	/**
	 * 将从数据库自定义sql语句取出的结果集的key转换为驼峰形式
	 * @param data 下划线形式的结果集
	 * @return 驼峰形式的结果集
	 */
	public static Map<String, Object> snake2Hump(Map<String, Object> data) {
		if (MapUtils.isBlank(data)) {
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			result.put(StrUtils.snake2Hump(entry.getKey()), entry.getValue());
		}
		data = null;
		return result;
	}

	/**
	 * 将从数据库自定义sql语句取出的结果集的key转换为驼峰形式
	 * @param datas 下划线形式的结果集
	 * @return 驼峰形式的结果集
	 */
	public static List<Map<String, Object>> snake2Hump(List<Map<String, Object>> datas) {
		if (datas == null || datas.isEmpty()) {
			return null;
		}
		List<Map<String, Object>> result = new ArrayList<>();
		for (Map<String, Object> data : datas) {
			Map<String, Object> res = snake2Hump(data);
			result.add(res);
		}
		datas = null;
		return result;
	}
}