package cn.icarving.api.pinche.dto;

public class PickedActivityApplyDto {

	private int pickedActivityApplyId;
	private int pickedActivityId;
	private int applyUserId;
	private String status;
	private String applyTime;
	private String lastModify;

	public int getPickedActivityApplyId() {
		return pickedActivityApplyId;
	}

	public void setPickedActivityApplyId(int pickedActivityApplyId) {
		this.pickedActivityApplyId = pickedActivityApplyId;
	}

	public int getPickedActivityId() {
		return pickedActivityId;
	}

	public void setPickedActivityId(int pickedActivityId) {
		this.pickedActivityId = pickedActivityId;
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
