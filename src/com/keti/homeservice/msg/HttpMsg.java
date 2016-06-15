package com.keti.homeservice.msg;

import java.util.HashMap;
import java.util.Map;

public class HttpMsg {
	private static final String API_KEY = "keti_kim_han_lee";
	
	private static final String KEY_API = "key";
	private static final String KEY_FROM = "from";
	private static final String KEY_TO = "to";
	private static final String KEY_NAME = "username";
	private static final String KEY_TYPE = "type";
	private static final String KEY_CMD = "cmd";
	private static final String KEY_COUNT = "count";
	private static final String KEY_CONTENT = "content";
	private static final String KEY_DATA = "data";
	
	private static final String KEY_MODEL = "modelName";

	private Map<String, String> item;

	public HttpMsg(String from, String to, String username, int type, int cmd, int count, int content, String data) {
		item = new HashMap<String, String>();
		item.put(KEY_API, API_KEY);
		item.put(KEY_FROM, from);
		item.put(KEY_TO, to);
		item.put(KEY_NAME, username);
		item.put(KEY_TYPE, String.valueOf(type));
		item.put(KEY_CMD, String.valueOf(cmd));
		item.put(KEY_COUNT, String.valueOf(count));
		item.put(KEY_CONTENT, String.valueOf(content));
		item.put(KEY_DATA, data);
	}
	
	public HttpMsg (String from, String to, String username, int type, int cmd, int count, int content) {
		item = new HashMap<String, String>();
		item.put(KEY_API, API_KEY);
		item.put(KEY_FROM, from);
		item.put(KEY_TO, to);
		item.put(KEY_NAME, username);
		item.put(KEY_TYPE, String.valueOf(type));
		item.put(KEY_CMD, String.valueOf(cmd));
		item.put(KEY_COUNT, String.valueOf(count));
		item.put(KEY_CONTENT, String.valueOf(content));
	}

	public void setModelName(String modelName) {
		item.put(KEY_MODEL, modelName);
	}
	
	public Map<String, String> getItem() {
		return item;
	}
}
