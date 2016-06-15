package com.keti.homeservice.msg;

/**
 * �� Ŭ�����z ��û �޽����� �ʿ��� ������ ����
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
