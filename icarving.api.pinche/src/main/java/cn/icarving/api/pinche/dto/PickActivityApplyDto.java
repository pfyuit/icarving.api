package cn.icarving.api.pinche.dto;

public class PickActivityApplyDto {
	
	private int pickActivityApplyId;
	private int pickActivityId;
	private int applyUserId;
	private String status;
	private String applyTime;
	private String lastModify;

	public int getPickActivityApplyId() {
		return pickActivityApplyId;
	}

	public void setPickActivityApplyId(int pickActivityApplyId) {
		this.pickActivityApplyId = pickActivityApplyId;
	}

	public int getPickActivityId() {
		return pickActivityId;
	}

	public void setPickActivityId(int pickActivityId) {
		this.pickActivityId = pickActivityId;
	}

	public int getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(int applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getLastModify() {
		return lastModify;
	}

	public void setLastModify(String lastModify) {
		this.lastModify = lastModify;
	}

}
