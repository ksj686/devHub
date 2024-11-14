package model;

import java.sql.Date;

public class Content {
	private int contentId;
	private String userId;
	private Date dateCreated;
	private Date lastUpdated;
	private int fileId;
	private int recommendCnt;

	
	public int getContentId() {
		return contentId;
	}
	
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserID(String userId) {
		this.userId = userId;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public int getFileId() {
		return fileId;
	}
	
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	public int getRecommendCnt() {
		return recommendCnt;
	}
	
	public void setRecommendCnt(int recommendCnt) {
		this.recommendCnt = recommendCnt;
	}
	
}
