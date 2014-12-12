package cn.icarving.api.pinche.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "picked_activity_apply")
public class PickedActivityApply {

	@Id
	@GeneratedValue
	@Column(name = "picked_activity_apply_id")
	private int pickedActivityApplyId;

	@Column(name = "picked_activity_id")
	private int pickedActivityId;

	@Column(name = "apply_user_id")
	private int applyUserId;

	private String status;

	@Column(name = "apply_time")
	private Timestamp applyTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

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
