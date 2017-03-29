package com.pi.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.pi.bean.InfoPayLoad;
import com.pi.bean.PayLoad;
import com.pi.bean.SchedulePayLoad;

public class GSONHandler {
	private static final Logger logger = Logger.getLogger("piclientLogger");
	
	/**
	 * @param fileName
	 * Reads data from schedule json,containing all schedule details,
	 * file and stores into memory
	 */
	public static List<SchedulePayLoad> schdFrmJSONFileToObjLst(String filePathName) {
		//String str = "";
		List<SchedulePayLoad> schdList = new ArrayList<SchedulePayLoad>();
		try {
			File schdFile = new File(filePathName);
			if(schdFile.exists()){
				JsonReader reader = new JsonReader(new FileReader(schdFile));
				Gson gson = new Gson();
				TypeToken<List<SchedulePayLoad>> token = new TypeToken<List<SchedulePayLoad>>() {};
				schdList = gson.fromJson(reader, token.getType());
				//str = gson.toJson(schdList);
			}else{
				logger.info("<<readScheduleFromJSONFileToString>> File Not Created Yet.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("<<readScheduleFromJSONFileToString>> File Not found.");
			logger.error(e.getMessage());
		}
		return schdList;
	}

	public static void main(String[] args) {
		/*List<SchedulePayLoad> s = schdFrmJSONFileToObjLst("D:/home/pi/piclient/json/schd.json");
		
		System.out.println(s.size());*/
		SchedulePayLoad s = new SchedulePayLoad();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String str = gson.toJson("");
		System.out.println(s.getScheduleId()==null?"y":"n");
		System.out.println(str);
		
	}
	
	
	/**
	 * @param data
	 * @param fileName
	 *  List -> json -> file
	 */
	public static boolean objLstToJsonFile(List data,String filePathName) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		boolean status = false;
		try {
			String jstr = gson.toJson(data);

			// write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter(filePathName);
			writer.write(jstr);
			writer.close();
			status = true;

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return status;
	}

	/**
	 * @param data
	 * @param filePath
	 * Any payload type obj to -> json -> file
	 */
	public static boolean objToJsonFile(PayLoad data,String filePath) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		boolean status = false;
		try {
			String jstr = gson.toJson(data);

			// write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter(filePath);
			writer.write(jstr);
			writer.close();
			status = true;

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return status;
	}
	
	
	/**
	 * @param jStr
	 * json -> schedule object
	 */
	public static List<SchedulePayLoad> jsonStrToScheduleObj(String jStr) {
		Gson gson = new Gson();
		TypeToken<List<SchedulePayLoad>> token = new TypeToken<List<SchedulePayLoad>>() {};
		List<SchedulePayLoad> ll = gson.fromJson(jStr, token.getType());
		return ll;
	}

	/**
	 * @param configJsonStr
	 * json to config obj
	 */
	public static InfoPayLoad jsonStrToConfigObj(String configJsonStr) {
		Gson gson = new Gson();
		TypeToken<InfoPayLoad> token = new TypeToken<InfoPayLoad>() {};

		InfoPayLoad info = gson.fromJson(configJsonStr, token.getType());
		return info;
	}
}
