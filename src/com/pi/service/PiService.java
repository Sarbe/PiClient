package com.pi.service;

import java.util.List;

import com.pi.bean.SchedulePayLoad;

public interface PiService {
	
	public void getLocalSchedules();
	public SchedulePayLoad getNxtSchdForAdWindow();
	public void removeAndSyncScheduleDtl(List<String> schds);
	public void retrieveServerOldContent();
	

}
