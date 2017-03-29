package com.pi.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.pi.bean.InfoPayLoad;
import com.pi.bean.SchedulePayLoad;

public class URLConnectionReader {
	private static final Logger logger = Logger.getLogger("piclientLogger");
	private static String http = "http://";
	
	public static String getScheduleDetailsFromRemoteServer(String macAdd) throws Exception{
		logger.debug("URLConnectionReader.getScheduleDetailsFromRemoteServer()");
		URL url;
		String responeOutput = "";
		try {
			String urlStr = http+Config.getEnvKey("remoteserver.address")+"/PiVisionWeb/client/Payload/getContent/"+macAdd;
			logger.debug(urlStr);
			url = new URL(urlStr);
			URLConnection urlCon = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				responeOutput += inputLine;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		}
		return responeOutput;
	}
	
	public static String getOldScheduleDetailsFromRemoteServer(String macAdd) throws Exception{
		logger.debug("URLConnectionReader.getOldScheduleDetailsFromRemoteServer()");
		URL url;
		String responeOutput = "";
		try {
			String urlStr = http+Config.getEnvKey("remoteserver.address")+"/PiVisionWeb/client/Payload/getOldContent/"+macAdd;
			logger.debug(urlStr);
			url = new URL(urlStr);
			URLConnection urlCon = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				responeOutput += inputLine;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		}
		return responeOutput;
	}
	
	public static String getConfigInfo(String macAdd,String refreshSts) throws Exception{
		logger.debug("URLConnectionReader.getConfigInfo()");
		URL url;
		String responeOutput = "";
		try {
			String urlStr = http+Config.getEnvKey("remoteserver.address")+"/PiVisionWeb/client/Payload/getInfo/"+macAdd+"/"+refreshSts;
			logger.debug(urlStr);
			url = new URL(urlStr);
			URLConnection urlCon = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				responeOutput += inputLine;
			}
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return responeOutput;
	}
	
	public static void downloadMedia(SchedulePayLoad schd) throws Exception{
		logger.debug("URLConnectionReader.getConfigInfo()");
		URL url;
		try {
			String urlStr = schd.getMediaUrl();
			logger.debug(urlStr);
			url = new URL(urlStr);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(Config.getEnvKey("mediaServerPath")+schd.getFileName());
			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		//return responeOutput;
	}

	public static void sentSoftwareUpdateStsToRemoteServer(String macAdd) {
		// TODO Auto-generated method stub
		
	}
}
