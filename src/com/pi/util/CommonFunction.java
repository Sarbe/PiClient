package com.pi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pi.bean.InfoPayLoad;
import com.pi.bean.SchedulePayLoad;

public class CommonFunction {
	private static final Logger logger = Logger.getLogger("piclientLogger");

	public static String getMACAddress() {
		InetAddress ip;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			//System.out.println("Current IP address : " + ip.getHostAddress());
			logger.info("System IP : " + ip.getHostAddress());
			logger.info("System Name : " + ip.getHostName());
			NetworkInterface network = NetworkInterface.getByName("eth0");// getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : ""));
			}
			//System.out.println(sb.toString());
			logger.info("System MAC :: " + sb.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String searchForMac() throws SocketException {
	    String firstInterface = null;        
	    Map<String, String> addressByNetwork = new HashMap<String,String>();
	    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

	    while(networkInterfaces.hasMoreElements()){
	        NetworkInterface network = networkInterfaces.nextElement();

	        byte[] bmac = network.getHardwareAddress();
	        if(bmac != null){
	            StringBuilder sb = new StringBuilder();
	            for (int i = 0; i < bmac.length; i++){
	                sb.append(String.format("%02X%s", bmac[i], (i < bmac.length - 1) ? "-" : ""));        
	            }

	            if(sb.toString().isEmpty()==false){
	                addressByNetwork.put(network.getName(), sb.toString());
	                logger.info("Address = "+sb.toString()+" @ ["+network.getName()+"] "+network.getDisplayName());
	            }

	            if(sb.toString().isEmpty()==false && firstInterface == null){
	                firstInterface = network.getName();
	            }
	        }
	    }

	    if(firstInterface != null){
	        return addressByNetwork.get(firstInterface);
	    }

	    return null;
	}

	/**
	 * @param info
	 * @param fileName
	 *            Write config data to file
	 */
	public static void writeToFile(InfoPayLoad info, String fileName) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File file = new File(PiStatic.configDir + fileName);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write("duration=" + info.getDuration());
			bw.newLine();
			bw.write("frequency=" + info.getFrequency());

		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public Date dateStringToDtae(String date) throws ParseException {
		// May 1, 2016
		DateFormat f = new SimpleDateFormat("MMM d, yyyy");
		return f.parse(date);
	}

	public Date timeStringToDtae(String time) throws ParseException {
		// 05:00:48 PM
		DateFormat f = new SimpleDateFormat("hh:mm:ss a");
		return f.parse(time);
	}

	
	public static String differentDateFormat(String date, String curFormat,
			String requiredFormat) throws ParseException {
		DateFormat inFmt = new SimpleDateFormat(curFormat);
		DateFormat outFmt = new SimpleDateFormat(requiredFormat);
		Date inDt = inFmt.parse(date);
		return outFmt.format(inDt);
	}

	public static void syncScheduleDtl(List<SchedulePayLoad> schDtls) {
		logger.info("Schedule Details received for for Sync :: "+ schDtls.size());
		
		List<SchedulePayLoad> allSchList = PiStatic.getMemory();
		for (int i = 0; i < schDtls.size(); i++) {
			SchedulePayLoad schd = schDtls.get(i);
			
			// Write New/Replace Image Detail
			//writeImageDataToFile(schd);
			
			if (allSchList.contains(schd)) { // if present replace.
				//schd.setMedia(null);
				allSchList.set(allSchList.indexOf(schd), schd);
			} else { // if not present, add schedule and create image json
				//System.out.println("New schedule detected.");
				logger.info("New Schedule Detected");
				//schd.setMedia(null); // erase the media value from schedule pay load
				// Add to list
				allSchList.add(schd);
			}
		}
		
		// Write all schedule details again to file(override).
		GSONHandler.objLstToJsonFile(allSchList, PiStatic.jsonDir + PiStatic.schdJsonFile);
		PiStatic.isScheduleListChnged = true; // set schedule chnge status
		
		logger.info("Data is synced to memory and written to file.");
		
	}

/*	public static void writeImageDataToFile(SchedulePayLoad schd) {
		ImagePayLoad ip = new ImagePayLoad();
		ip.setScheduleId(schd.getScheduleId());
		ip.setFileName(schd.getFileName());
		ip.setFileType(schd.getFileType());
		ip.setMimeType(schd.getMimeType());
		ip.setDuration(schd.getDuration());
		//ip.setMediaByteArray(new String(schd.getMedia()));
		
		GSONManager<ImagePayLoad> gmgr = new GSONManager<ImagePayLoad>();
		gmgr.objToJSONFile(ip, PiStatic.jsonDir + ip.getScheduleId()+ PiStatic.jsonFileExt);
		
		//GSONHandler.writeObjToFile(ip, PiStatic.jsonDir + ip.getScheduleId()+ PiStatic.jsonFileExt);
		logger.info("Image JSON Created");
	}*/

/*	public static void removeAndSyncScheduleDtl(SchedulePayLoad schd) {
		List<SchedulePayLoad> allSchList = PiStatic.getMemory();
		if (allSchList.contains(schd)) { 
			allSchList.remove(allSchList.indexOf(schd));
		}
		GSONHandler.objLstToJsonFile(allSchList, PiStatic.jsonDir + PiStatic.schdJsonFile);
		System.out.println("Data is synced to memory and removed from file. Sch Id-" +schd.getScheduleId());
		logger.info("Data is synced to memory and removed from file. Sch Id-" +schd.getScheduleId());
		
	}*/
	
	public static void removeAndSyncScheduleDtl(List<String> ids) {
		List<SchedulePayLoad> allSchList = PiStatic.getMemory();
		
		for(int i =0;i<ids.size();i++){
			SchedulePayLoad tempSch = new SchedulePayLoad();
			tempSch.setScheduleId(ids.get(i));
			
			if (allSchList.contains(tempSch)) { 
				allSchList.remove(allSchList.indexOf(tempSch));
				logger.info("schedule removed from memory :: " +tempSch.getScheduleId());
			}
		}
		GSONHandler.objLstToJsonFile(allSchList, PiStatic.jsonDir + PiStatic.schdJsonFile);
		PiStatic.isScheduleListChnged = true;
	}
	
	
		
	//timeStringToDate
	private static Date ttd(String HHmm) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date d = sdf.parse(HHmm);
		return d;
	}

	public static void syncList(List<SchedulePayLoad> allSch, List<SchedulePayLoad> tempSchList) {
		for(SchedulePayLoad sch:allSch){
			if(!tempSchList.contains(sch)){
				tempSchList.add(0, sch); // if new, add to first
			}
		}
		
		// Remove if present in tempList
		for(SchedulePayLoad sch:tempSchList){
			if(!allSch.contains(sch)){
				tempSchList.remove(sch); 
			}
		}
	}

	public static List<SchedulePayLoad> getSchedulesForCurrentWindow(CopyOnWriteArrayList<SchedulePayLoad> tempSchList,int timeInMS) {
		List<SchedulePayLoad> currentSchList = new ArrayList<SchedulePayLoad>();
		int tempMS = 0;
		
		for (int i = 0; i < tempSchList.size(); i++) {
			SchedulePayLoad sch = tempSchList.get(0);
			if (tempMS < timeInMS) {
				if (tempMS + (sch.getDuration() * 1000) < timeInMS) {
					currentSchList.add(sch);
					tempSchList.remove(sch);
					tempSchList.add(tempSchList.size(), sch);
					tempMS += sch.getDuration() * 1000;
				}
			}else{
				break;
			}
		}
		return currentSchList;
	}

	public static void writeFileContent(File chksumFile, String fileChkSum) throws IOException {
		BufferedWriter bw = null;
		try {
			FileWriter fw = new FileWriter(chksumFile);
			bw = new BufferedWriter(fw);
			bw.write(fileChkSum);
			
		} catch (IOException e) {
			logger.error("CommonFunction.writeContent()");
			e.printStackTrace();
			throw e;
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static String readFileContent(File stsFile) {
		//File file = new File(stsFile);
		BufferedReader br = null;
		String statusOfdownload = "";
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(stsFile));
			while ((sCurrentLine = br.readLine()) != null) {
				statusOfdownload = sCurrentLine.trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return statusOfdownload;
	}
	
	
	
	public static void retrieveServerOldContent(String macAdd) {
		try {
			String schdPayLoad = URLConnectionReader.getOldScheduleDetailsFromRemoteServer(macAdd);
			List<SchedulePayLoad> schDtls = GSONHandler.jsonStrToScheduleObj(schdPayLoad);

			if (schDtls != null && schDtls.size() > 0) {
				logger.info("Number of old schedule received form Server :: " + schDtls.size());
				logger.debug("SYncing remote schedule details");
				CommonFunction.syncScheduleDtl(schDtls);
				// Downloading media
				for (SchedulePayLoad schd : schDtls) {
					URLConnectionReader.downloadMedia(schd);
					logger.info("Media downloaded for schedule ::" + schd.getFileName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//////////////////// Feeds
	
	/*public static String connectUrl() throws Exception {
		URL url;
		String responeOutput = "";
		try {
			String urlStr = "http://abcnews.go.com/International/us-cuba-relations-timeline/story?id=43789719";
			url = new URL(urlStr);
			URLConnection urlCon = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				responeOutput += inputLine;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		System.out.println(responeOutput);
		return responeOutput;
	}*/
	private static String allowedExtn = "JPG|PNG";
	public static String getImgFromLink(String url) throws IOException {
		//String url = "http://abcnews.go.com/International/us-cuba-relations-timeline/story?id=43789719";

		Document doc = Jsoup.connect(url).get();
		Elements media = doc.select("[src]");
		String imgurl = "";
		for (Element src : media) {
			if (src.tagName().equals("img")) {
				String imglink = src.attr("abs:src");
				//System.out.println(imglink);
				if(allowedExtn.contains(imglink.substring(imglink.length() - 3).toUpperCase())){
					imgurl = imglink;
					break;
				}
				
			}
		}
		return imgurl;
	}


}
