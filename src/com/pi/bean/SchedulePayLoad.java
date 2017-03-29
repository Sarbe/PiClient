package com.pi.bean;


public class SchedulePayLoad implements PayLoad {
	private static final long serialVersionUID = -341864896857119622L;
	private String scheduleId;
	private String startDate;
	private String endDate;
	private String timeOfDay;
	private String fileName;
	private String fileType;
	private String mimeType;
	private int duration;
	private int priority;
	private String mediaUrl;
	
	private int displayedTimes;
	

	public int increaseDisplayedTimes() {
		return displayedTimes++;
	}

	public void decreaseDisplayedTimes(int displayedTimes) {
		this.displayedTimes--;
	}

	public SchedulePayLoad() {
	}

	public SchedulePayLoad(String scheduleId, String startDate,
			String fileName, String fileType) {
		super();
		this.scheduleId = scheduleId;
		this.startDate = startDate;
		this.fileName = fileName;
		this.fileType = fileType;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((scheduleId == null) ? 0 : scheduleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulePayLoad other = (SchedulePayLoad) obj;
		if (scheduleId == null) {
			if (other.scheduleId != null)
				return false;
		} else if (!scheduleId.equals(other.scheduleId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SchedulePayLoad [scheduleId=" + scheduleId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", timeOfDay="
				+ timeOfDay + ", fileName=" + fileName + ", fileType="
				+ fileType + ", mimeType=" + mimeType + ", duration="
				+ duration + ", priority=" + priority + ", mediaUrl="
				+ mediaUrl + "]";
	}
}
