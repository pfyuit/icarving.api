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
	private String pickedActivityApplyId;

	@Column(name = "picked_activity_id")
	private String pickedActivityId;

	@Column(name = "apply_user_id")
	private String applyUserId;

	private String status;

	@Column(name = "apply_time")
	private Timestamp applyTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	public String getPickedActivityApplyId() {
		return pickedActivityApplyId;
	}

	public void setPickedActivityApplyId(String pickedActivityApplyId) {
		this.pickedActivityApplyId = pickedActivityApplyId;
	}

	public String getPickedActivityId() {
		return pickedActivityId;
	}

	public void setPickedActivityId(String pickedActivityId) {
		this.pickedActivityId = pickedActivityId;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
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
