package cn.icarving.api.pinche.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pick_activity_apply")
public class PickActivityApply {

	@Id
	@Column(name = "pick_activity_apply_id")
	private long pickActivityApplyId;

	@Column(name = "pick_activity_id")
	private long pickActivityId;

	@Column(name = "apply_user_id")
	private long applyUserId;

	private String status;

	@Column(name = "apply_time")
	private Timestamp applyTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	public long getPickActivityApplyId() {
		return pickActivityApplyId;
	}

	public void setPickActivityApplyId(long pickActivityApplyId) {
		this.pickActivityApplyId = pickActivityApplyId;
	}

	public long getPickActivityId() {
		return pickActivityId;
	}

	public void setPickActivityId(long pickActivityId) {
		this.pickActivityId = pickActivityId;
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
