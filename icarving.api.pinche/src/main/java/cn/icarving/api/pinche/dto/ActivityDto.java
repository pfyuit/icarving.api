package cn.icarving.api.pinche.dto;

public class ActivityDto {
	private int activityId;
	private int activityType; // 1-pick, 2-picked
	private int ownerId;
	private String ownerName;
	private String ownerAvatar;
	private int ownerPhone;
	private String startTime;
	private String returnTime;
	private String sourceAddress;
	private String destAddress;
	private String charge;
	private String carType;
	private int capacity;
	private int applyNumber;
	private int approveNumber;
	private String status;
	private String note;
	private String publishTime;
	private String lastModify;

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerAvatar() {
		return ownerAvatar;
	}

	public void setOwnerAvatar(String ownerAvatar) {
		this.ownerAvatar = ownerAvatar;
	}

	public int getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(int ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(int applyNumber) {
		this.applyNumber = applyNumber;
	}

	public int getApproveNumber() {
		return approveNumber;
	}

	public void setApproveNumber(int approveNumber) {
		this.approveNumber = approveNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getLastModify() {
		return lastModify;
	}

	public void setLastModify(String lastModify) {
		this.lastModify = lastModify;
	}

}
