package com.pi.service.Impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.pi.bean.SchedulePayLoad;
import com.pi.service.PiService;
import com.pi.util.CommonFunction;
import com.pi.util.GSONHandler;
import com.pi.util.PiStatic;

public class PiServiceImpl implements PiService {
	private static final Logger logger = Logger.getLogger("piclientLogger");
	private static int preSchdIndx = 0;
	private static List<SchedulePayLoad> curSchList;
	private static CopyOnWriteArrayList<SchedulePayLoad> tempSchList = new CopyOnWriteArrayList<SchedulePayLoad>();
	
	@Override
	public void getLocalSchedules() {
		List<SchedulePayLoad> localSchds = GSONHandler.schdFrmJSONFileToObjLst(PiStatic.jsonDir + PiStatic.schdJsonFile);
		PiStatic.setMemory(localSchds);
		logger.info("Schedute size after reading local file :: " + PiStatic.getMemory().size());
		//return localSchds;
	}
	@Override
	public SchedulePayLoad getNxtSchdForAdWindow() {
		SchedulePayLoad schd = new SchedulePayLoad();
		// get full schedule
		List<SchedulePayLoad> allSch = PiStatic.getMemory();
		logger.debug("allSch size :: " + allSch.size());
		logger.debug("PiStatic.isScheduleListChnged :: " + PiStatic.isScheduleListChnged);
		
		if (PiStatic.isScheduleListChnged) { 
			// Sync tempList
			CommonFunction.syncList(allSch, tempSchList); // Create a temp list of alllist, where ordering of schedule is maintained.
			PiStatic.isScheduleListChnged = false; // chng status after sync
			
			// Need to put a better algorithm to calculate schedules based on nooftimes displayed and priority
			curSchList = tempSchList; //CommonFunction.getSchedulesForCurrentWindow(tempSchList, PiStatic.advWindow);
		}

		// Logic for Choosing nxt schedule
		if (curSchList != null && curSchList.size() > 0) {
			// do not pick same image
			logger.debug("Current schedule list size is :: " + curSchList.size());
			int curSchdIndx = 0;
			Random r = new Random();
			if (curSchList.size() > 1) {
				do {
					curSchdIndx = r.nextInt(curSchList.size());
					//System.out.println("preSchdIndx : "+preSchdIndx+" :: curSchdIndx: "+curSchdIndx);
				} while (curSchdIndx == preSchdIndx);
				preSchdIndx = curSchdIndx;
			} else {
				curSchdIndx = r.nextInt(curSchList.size());
			}

			schd = curSchList.get(curSchdIndx); // CommonFunction.getSchedule(allSch);
			schd.increaseDisplayedTimes(); // increse the no of times displayed
			logger.debug("Randomly choosing " + schd.getScheduleId() + " Schedule.");
			////////////////
		}else{
			logger.debug("Current schedule list is empty");
		}
		return schd;
	}
	@Override
	public void removeAndSyncScheduleDtl(List<String> schds) {
		CommonFunction.removeAndSyncScheduleDtl(schds);
		
	}
	@Override
	public void retrieveServerOldContent() {
		CommonFunction.retrieveServerOldContent(PiStatic.macAdd);
		
	}

}
