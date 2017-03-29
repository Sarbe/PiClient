package com.pi.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pi.bean.SchedulePayLoad;

public class PiStatic {

	public static final Object env_LOCAL = "LOCAL";
	public static final Object env_PI = "PI";
	///////////
	public static String homeDir = Config.getEnvKey("homeDir");//"/home/pi/piclient/";
	//public static String homeDir = "D:/tmp/";
	
	public static String imgDir =Config.getEnvKey("mediaServerPath");
	
	public static String touchFile = homeDir + Config.getKey("touchFile");//"startFlag.tmp";
	public static String jsonDir = homeDir + Config.getKey("jsonDir");// "json/";
	public static String configDir = homeDir + Config.getKey("configDir");// "config/";
	
	//////////////////////
	public static String schdJsonFile = "schd.json";
	public static String configFile = "cofig.sys";
	public static String jsonFileExt = ".json";

	private static List<SchedulePayLoad> memory = new ArrayList<SchedulePayLoad>();
	public static String macAdd;
	public static Date lastImgRefreshTime = new Date();
	
	public static boolean isScheduleListChnged = true;
	

	public static int advWindow = 60000;

	
	private PiStatic() {
	}
	public static List<SchedulePayLoad> getMemory() {
		if (memory == null)
			memory = new ArrayList<SchedulePayLoad>();
		return memory;
	}

	public static void setMemory(List<SchedulePayLoad> memory) {
		if (memory != null)
			PiStatic.memory = memory;

	}

}
