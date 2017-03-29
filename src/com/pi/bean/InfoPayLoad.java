package com.pi.bean;

import java.util.List;

public class InfoPayLoad implements PayLoad {

	private String configUpdate = "N"; // respond with what type of info
	// configuration or content or both
	private String contentUpdate = "N";

	/*private String softwareUpdate = "N";
	private String downloaLink = "";
	private String fileChkSum = "";*/
	
	private int duration; // needed to change slot duration if required
	private int frequency; // needed to change HDMI switching frequency
	private List<String> delSchIds;

	// delete schedule ids

	public InfoPayLoad() {
		// TODO Auto-generated constructor stub
	}

	/*public InfoPayLoad(String infoType, int duration, int frequency,
			List<String> delSchIds) {
		super();
		this.infoType = infoType;
		this.duration = duration;
		this.frequency = frequency;
		this.delSchIds = delSchIds;
	}*/



	public int getDuration() {
		return duration;
	}

	public String getConfigUpdate() {
		return configUpdate;
	}

	public void setConfigUpdate(String configUpdate) {
		this.configUpdate = configUpdate;
	}

	public String getContentUpdate() {
		return contentUpdate;
	}

	public void setContentUpdate(String contentUpdate) {
		this.contentUpdate = contentUpdate;
	}

	/*public String getSoftwareUpdate() {
		return softwareUpdate;
	}

	public void setSoftwareUpdate(String softwareUpdate) {
		this.softwareUpdate = softwareUpdate;
	}

	public String getDownloaLink() {
		return downloaLink;
	}

	public void setDownloaLink(String downloaLink) {
		this.downloaLink = downloaLink;
	}

	public String getFileChkSum() {
		return fileChkSum;
	}

	public void setFileChkSum(String fileChkSum) {
		this.fileChkSum = fileChkSum;
	}
*/
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public List<String> getDelSchIds() {
		return delSchIds;
	}

	public void setDelSchIds(List<String> delSchIds) {
		this.delSchIds = delSchIds;
	}

	public static void main(String[] args) {
		/*InfoPayLoad i = new InfoPayLoad("NONE", 45, 67, null);
		Gson gson = new Gson();
		String str = gson.toJson(i);
		//System.out.println(str);

		InfoPayLoad info = GSONHandler.JSONToConfigObj(str);
*/
		// System.out.println(info.getDelSchIds().length);
	}

}
