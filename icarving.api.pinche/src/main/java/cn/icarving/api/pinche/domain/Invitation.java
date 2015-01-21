package cn.icarving.api.pinche.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invitation")
public class Invitation {

	@Id
	@GeneratedValue
	@Column(name = "invitation_id")
	private int invitationId;

	@Column(name = "creator_id")
	private int creatorId;

	@Column(name = "invitation_code")
	private int invitationCode;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	@Column(name = "is_used")
	private int isUsed;

	public int getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(int invitationId) {
		this.invitationId = invitationId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(int invitationCode) {
		this.invitationCode = invitationCode;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastModify() {
		return lastModify;
	}

	public void setLastModify(Timestamp lastModify) {
		this.lastModify = lastModify;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

}
