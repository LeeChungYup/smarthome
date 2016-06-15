package com.keti.homeservice.msg;

/**
 * 이 클래스틑 요청 메시지에 필요한 정보를 정의
 * */
@SuppressWarnings("unused")
public class ReqMsg extends HeaderMsg {

	private Object data;

	public ReqMsg(String from, String to, String username, int type, int cmd, int count, int content, Object data) {
		super.from = from;
		super.to = to;
		super.username = username;
		super.type = type;
		super.cmd = cmd;
		super.count = count;
		super.content = content;
		this.data = data;
	}

}
