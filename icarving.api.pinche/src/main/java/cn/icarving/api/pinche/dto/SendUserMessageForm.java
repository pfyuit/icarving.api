package cn.icarving.api.pinche.dto;

public class SendUserMessageForm {
	private int messageType;
	private int activityId;
	private String activitySourceAddress;
	private String activityDestAddress;
	private int applyId;
	private int fromUid;
	private int toUid;
	private String content;

	public int getFromUid() {
		return fromUid;
	}

	public void setFromUid(int fromUid) {
		this.fromUid = fromUid;
	}

	public int getToUid() {
		return toUid;
	}

	public void setToUid(int toUid) {
		this.toUid = toUid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getActivitySourceAddress() {
		return activitySourceAddress;
	}

	public void setActivitySourceAddress(String activitySourceAddress) {
		this.activitySourceAddress = activitySourceAddress;
	}

	public String getActivityDestAddress() {
		return activityDestAddress;
	}

	public void setActivityDestAddress(String activityDestAddress) {
		this.activityDestAddress = activityDestAddress;
	}

	public int getApplyId() {
		return applyId;
	}

	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}

}
