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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class GSONManager<T> {
	private static final Logger logger = Logger.getLogger("piclientLogger");

	public List<T> jsonFileToObj1(String filePathName,	Class<T> clz) {
		List<T> obj =  new ArrayList<T>();
		try {
			File schdFile = new File(filePathName);
			if (schdFile.exists()) {
				JsonReader reader = new JsonReader(new FileReader(schdFile));
				
				Gson gson = new Gson();
				JsonParser parser = new JsonParser();
				JsonArray array = parser.parse(reader).getAsJsonArray();
				//List<T> lst =  new ArrayList<T>();
				
		        for(JsonElement json: array){
		            T entity = gson.fromJson(json, clz);
		            obj.add(entity);
		        }
				
				//TypeToken<List<T>> token = new TypeToken<List<T>>() {
				//};
				//obj = gson.fromJson(reader, token.getType());
				
				
				
			} else {
				logger.info("<<GSONManager.jsonFileToObj()>> File Not present.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("<<GSONManager.jsonFileToObj()>> File Not found.");
			logger.error(e.getMessage());
		}
		return obj;
	}

	/**
	 * @param data
	 * @param fileName
	 * Obj -> json -> file
	 */
	public boolean objToJSONFile1(T data, String filePathName) {
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
	 * @param jStr
	 *  json -> schedule object
	 */
	public T jsonStrToObj1(String jStr, Class<T> clazz) {
		Gson gson = new Gson();
		//TypeToken<T> token = new TypeToken<T>() {	};
		T obj = gson.fromJson(jStr, clazz);
		return obj;
	}
	
	public List<T> jsonStrToObjList1(String jStr, Class<T> clazz) {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(jStr).getAsJsonArray();
		List<T> lst =  new ArrayList<T>();
        for(JsonElement json: array){
            T entity = gson.fromJson(json, clazz);
            lst.add(entity);
        }
		return lst;
	}
	
	public static void main(String[] args) {
		
	}
}
