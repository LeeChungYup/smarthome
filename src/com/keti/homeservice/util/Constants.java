package com.keti.homeservice.util;

public class Constants {
	public static final String USER_NAME = "user2";
	/*
	 * Define name of the place to send message
	 * */
	public static final String MY_NAME = "HOMESERVICE_CONTROLLER";
	public static final String TO_MASHUP_DB = "MASHUP_DB";
	public static final String TO_QRCODE_DB = "QRCODE_DB";
	public static final String TO_USER_DB = "USER_DB";
	public static final String TO_HOME_GATEWAY = "HOME_GATEWAY";
	
	/*
	 * Define content command
	 * */
	public static final int CMD_SELECT = 0;
	public static final int CMD_INSERT = 1;
	public static final int CMD_DELETE = 2;
	public static final int CMD_STATUS = 3;
	public static final int CMD_UPDATE = 6;
	
	/*
	 * Define content type
	 * */
	public static final int CT_DEV = 0;
	public static final int CT_SRV = 1;
	public static final int CT_PHONE = 2;
	public static final int CT_LOC = 3;
	public static final int CT_SCH = 4;
	public static final int CT_QRCODE = 5;
	public static final int CT_ARALM = 6;
	public static final int CT_SMARTBAND = 7;
	public static final int CT_USER = 8;
	public static final int CT_USER_STATE = 11;
	/*
	 * Define content count information
	 * */
	public static final int COUNT_ALL = 0;
	public static final int COUNT_ONE = 1;
	
	/*
	 * Define message type
	 * */
	public static final int TYPE_RES_FAIL = 0;
	public static final int TYPE_RES_SUC = 1;
	public static final int TYPE_NOTI = 2;
	public static final int TYPE_REQ = 3;

	/*
	 * Define device type
	 * */
	public static final String DEV_TYPE_TV = "TV";
	public static final String DEV_TYPE_AC = "AC";
	public static final String DEV_TYPE_DVD = "DVD";
	public static final String DEV_TYPE_AUDIO = "AUDIO";
	public static final String DEV_TYPE_ROBOTCLEANER = "ROBOT_CLEANER";
	public static final String DEV_TYPE_ACCLEANER = "AC_CLEANER";
	public static final String DEV_TYPE_DEHUMIDIFIER = "DEHUMIDIFIER";
	public static final String DEV_TYPE_HUMIDIFIER = "HUMIDIFIER";
	public static final String DEV_TYPE_LAMP = "LAMP";
	public static final String DEV_TYPE_WARMER = "WARMER";
	public static final String DEV_TYPE_HEATER = "HEATER";
	public static final String DEV_TYPE_FAN = "FAN";
	public static final String DEV_TYPE_THERMO_HYGROMETH = "S_THERMO_HYGROMETH";
	public static final String DEV_TYPE_AIR_QUALITY = "AIR_QUALITY";
	public static final String DEV_TYPE_WASHINGMACHINE = "WASHINGMACHINE";
	
	/*
	 * Define service name
	 * */
	public static final String SN_OUT_MODE = "OUT_MODE";
	public static final String SN_IN_MODE = "IN_MODE";
	public static final String SN_INNER_TEMP_CTR = "INNER_TEMP_CTR";
	public static final String SN_CLEAN_MODE = "CLEAN_MODE";
	public static final String SN_AIRCLEANER = "AIR_CLEANER";
	public static final String SN_AIRCON = "AIRCON";
	public static final String SN_DEHUMIDIFIER = "DEHUMIDIFIER";
	public static final String SN_HUMIDIFIER = "HUMIDIFIER";

	/*
	 * Define service category
	 * */
	public static final String CATEGORY_ENVR = "environment";
	public static final String CATEGORY_SECURITY = "security";
	
	
	/*
	 * Define control type
	 * */
	public static final String CONTROL_TYPE_IR = "IR";
	public static final String CONTROL_TYPE_TCP = "TCP";
	public static final String CONTROL_TYPE_SERIAL = "SERIAL";
	
	/*
	 * Define location
	 * */
	public static final String LOC_LIVINGROOM = "livingroom";
	public static final String LOC_BEDROOM_ONE = "bedroom1";
	public static final String LOC_BEDROOM_TWO = "bedroom2";
	public static final String LOC_KITCHEN = "kitchen";
	public static final String LOC_BATHROOM = "bathroom";
	
	
	/**
	 * Define user
	 * */
	public static final int USER_STATE_NORMAL = 1;
	public static final int USER_STATE_EMERGENCY = 2;
	
}
