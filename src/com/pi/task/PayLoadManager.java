package com.pi.task;

import java.util.Timer;

import org.apache.log4j.Logger;

import com.pi.util.Config;

public class PayLoadManager {
	
	private static final Logger logger = Logger.getLogger("piclientLogger");
	PayLoadTimerTask task = null;

	public PayLoadManager(String macAdd) {
		logger.info("Timer Task Started");
		Timer t = new Timer();
		task = new PayLoadTimerTask(macAdd);
		t.schedule(task, Long.valueOf(Config.getKey("task.starttm")).longValue(), 
				Long.valueOf(Config.getKey("task.interval")).longValue());
	}

}
