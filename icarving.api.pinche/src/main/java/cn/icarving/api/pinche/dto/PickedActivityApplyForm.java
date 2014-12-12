package cn.icarving.api.pinche.dto;

public class PickedActivityApplyForm {

	private long pickedActivityId;

	private long applyUserId;

	public long getPickedActivityId() {
		return pickedActivityId;
	}

	public void setPickedActivityId(long pickedActivityId) {
		this.pickedActivityId = pickedActivityId;
	}

	public long getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(long applyUserId) {
		this.applyUserId = applyUserId;
	}

}
