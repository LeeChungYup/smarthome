package com.keti.homeservice.msg;

import com.keti.homeservice.util.Constants;

/**
 * 이 클래스는 응답 메시지에 필요한 정보를 정의
 * */
public class ResMsg extends HeaderMsg {

	private Object result;

	public Object getResult() {
		return result;
	}

}
