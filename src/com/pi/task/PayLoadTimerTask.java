package com.pi.task;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.pi.bean.InfoPayLoad;
import com.pi.bean.SchedulePayLoad;
import com.pi.util.CommonFunction;
import com.pi.util.GSONHandler;
import com.pi.util.PiStatic;
import com.pi.util.URLConnectionReader;

public class PayLoadTimerTask extends TimerTask {
	private static final Logger logger = Logger.getLogger("piclientLogger");
	private String macAdd = "";

	public PayLoadTimerTask(String macAdd) {
		super();
		this.macAdd = macAdd;
	}

	@Override
	public void run() {
		logger.debug("******************Calling Remote Server**********");
		try {
			
			// Additional Data
			// 1. Refresh sts - this will tell if the TV is connected and adv is running.
			// If tv is not connected switcher will not work and the flag file should be present, that signifies add window is never started.
			Date timeOflastRefresh = PiStatic.lastImgRefreshTime;
			Date now = new Date();
			
			long diffMin = (now.getTime() - timeOflastRefresh.getTime())/(60000);
			String refreshSts = "Y";
			if(diffMin > 30){
				refreshSts = "N";
			}
			///////// RefreshSTs logic end

			// Connect to remote server
			String configJsonStr = URLConnectionReader.getConfigInfo(macAdd,refreshSts);
			logger.debug("Config Info Received :: " + configJsonStr);
			// After getting response
			if (!configJsonStr.equals("")) {
				InfoPayLoad info = GSONHandler.jsonStrToConfigObj(configJsonStr);

				String configUpdate = info.getConfigUpdate();
				/*String softwareUpdate = info.getSoftwareUpdate();*/
				String contentUpdate = info.getContentUpdate();
				
				if (configUpdate.equals("Y")) {
					CommonFunction.writeToFile(info, PiStatic.configFile);
				}

				if (contentUpdate.equals("Y")) {
					String schdPayLoad = URLConnectionReader.getScheduleDetailsFromRemoteServer(macAdd);
					List<SchedulePayLoad> schDtls = GSONHandler.jsonStrToScheduleObj(schdPayLoad);
					
					if(schDtls!=null && schDtls.size()>0){
						logger.info("Number of new schedule received form Server :: " + schDtls.size());	
						logger.debug("SYncing remote schedule details");
						CommonFunction.syncScheduleDtl(schDtls);
						
						//Downloading media
						for(SchedulePayLoad schd:schDtls){
							URLConnectionReader.downloadMedia(schd);
							logger.info("Media downloaded for schedule ::"+schd.getFileName());
						}
					}
				}
				
				//Delete schedule
				if(info.getDelSchIds()!=null && info.getDelSchIds().size()>0){
					CommonFunction.removeAndSyncScheduleDtl(info.getDelSchIds());
					logger.debug("Schedule removed and synched");
				}
			}
		} catch (Exception e) {
			logger.error("Error Connecting to Remote Server." + e.getMessage());
			e.printStackTrace();
		}finally{
		}
	}
}
