package cn.icarving.api.pinche.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "picked_activity_apply")
public class PickedActivityApply {

	@Id
	@Column(name = "picked_activity_apply_id")
	private long pickedActivityApplyId;

	@Column(name = "picked_activity_id")
	private long pickedActivityId;

	@Column(name = "apply_user_id")
	private long applyUserId;

	private String status;

	@Column(name = "apply_time")
	private Timestamp applyTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	public long getPickedActivityApplyId() {
		return pickedActivityApplyId;
	}

	public void setPickedActivityApplyId(long pickedActivityApplyId) {
		this.pickedActivityApplyId = pickedActivityApplyId;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public Timestamp getLastModify() {
		return lastModify;
	}

	public void setLastModify(Timestamp lastModify) {
		this.lastModify = lastModify;
	}

}
