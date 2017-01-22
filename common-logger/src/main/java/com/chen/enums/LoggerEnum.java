package com.chen.enums;


public enum LoggerEnum {

	DEFAULT("default"), ERROR("error");

	private String loggerName;

	LoggerEnum(String loggerName) {
		this.loggerName = loggerName;
	}

	@Override
	public String toString() {
		return loggerName;
	}
}
