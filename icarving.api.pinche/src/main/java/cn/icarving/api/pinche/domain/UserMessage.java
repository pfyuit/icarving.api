package cn.icarving.api.pinche.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_message")
public class UserMessage {

	@Id
	@GeneratedValue
	@Column(name = "user_message_id")
	private int userMessageId;

	/**
	 * uid =0 means from system, it indicates the system event, such as the
	 * 'activity update/cancel event','activity apply event', apply
	 * 'approve/unapprove/cancel event', etc.
	 */
	@Column(name = "from_uid")
	private int fromUid;

	@Column(name = "to_uid")
	private int toUid;

	private String content;

	/**
	 * 0 - unread, 1- read
	 */
	private int status;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	public int getUserMessageId() {
		return userMessageId;
	}

	public void setUserMessageId(int userMessageId) {
		this.userMessageId = userMessageId;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

}
