package com.keti.homeservice.msg;

import com.keti.homeservice.util.Constants;

public class HeaderMsg {
	protected String from;
	protected String to;
	protected String username;
	protected int type;
	protected int cmd;
	protected int count;
	protected int content;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}
	
	/***************************** 응답 메시지의 종류 ********************************/
	public static final int UNKNOWN_MSG = -1;
	public static final int SELECT_ALL_DEV = 0;
	public static final int SELECT_ALL_SRV = 1;
	public static final int SELECT_ONE_DEV = 2;
	public static final int SELECT_ONE_SRV = 3;
	public static final int INSERT_ALL_DEV = 4;
	public static final int INSERT_ALL_SRV = 5;
	public static final int INSERT_ONE_DEV = 6;
	public static final int INSERT_ONE_SRV = 7;
	public static final int DELETE_ONE_SRV = 8;
	public static final int STATE_ALL_DEV = 9;
	public static final int STATE_ALL_SRV = 10;
	public static final int STATUS_ONE_DEV = 11;
	public static final int STATUS_ONE_SRV = 12;
	public static final int START_ONE_SRV = 13;
	public static final int STOP_ONE_SRV = 14;
	public static final int SELECT_ONE_QRCODE = 15;
	public static final int SELECT_ALL_USER = 16;
	public static final int INSERT_ONE_USER = 17;
	public static final int STATE_USER = 18;

	/**
	 * 현재 메시지가 어떤 종류의 메시지인지를 구분한다.
	 * */
	public int whatMsg() {
		switch(cmd) {
		case Constants.CMD_SELECT:
			if (count == Constants.COUNT_ALL) {
				if (content == Constants.CT_DEV) {
					return SELECT_ALL_DEV;
				} else if (content == Constants.CT_SRV) {
					return SELECT_ALL_SRV;
				} else if(content == Constants.CT_USER) {
					return SELECT_ALL_USER;
				}
			} else if (count == Constants.COUNT_ONE) {
				if (content == Constants.CT_DEV) {
					return SELECT_ONE_DEV;
				} else if (type == Constants.CT_SRV) {
					return SELECT_ONE_SRV;
				}
			}
			break;
			
		case Constants.CMD_INSERT:
			if (count == Constants.COUNT_ALL) {
				if (content == Constants.CT_DEV) {
					return INSERT_ALL_DEV;
				} else if (content == Constants.CT_SRV) {
					return INSERT_ALL_SRV;
				}
			} else if (count == Constants.COUNT_ONE) {
				if (content == Constants.CT_DEV) {
					return INSERT_ONE_DEV;
				} else if (content == Constants.CT_SRV) {
					return INSERT_ONE_SRV;
				} else if (content == Constants.CT_USER) {
					return INSERT_ONE_USER;
				}
			}
			break;
			
		case Constants.CMD_DELETE:
			if (count == Constants.COUNT_ONE) {
				if (content == Constants.CT_SRV) {
					return DELETE_ONE_SRV;
				}
			}
			break;
			
		case Constants.CMD_STATUS:
			if (count == Constants.COUNT_ALL) {
				if (content == Constants.CT_DEV) {
					return STATE_ALL_DEV;
				} else if (content == Constants.CT_SRV) {
					return STATE_ALL_SRV;
				}
			} else if (count == Constants.COUNT_ONE) {
				if (content == Constants.CT_DEV) {
					return STATUS_ONE_DEV;
				} else if (content == Constants.CT_SRV) {
					return STATUS_ONE_SRV;
				} else if (content == Constants.CT_USER) {
					return STATE_USER;
				}
			}
			break;
		}


		return UNKNOWN_MSG;
	}

}
