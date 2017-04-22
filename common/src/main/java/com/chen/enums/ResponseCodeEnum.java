package com.chen.enums;

/**
 * 全局返回码
 * @author Chenwl
 * @version 1.0.0
 */
public enum ResponseCodeEnum {
	SUCCESS(1, "成功"), FAIL(0, "失败"), ERROR(-1, "异常");

	private Integer code;
	private String value;

	ResponseCodeEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

}
