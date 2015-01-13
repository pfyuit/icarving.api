package cn.icarving.api.pinche.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue
	@Column(name = "message_id")
	private int messageId;

	/**
	 * 1-notify, 2-message
	 */
	@Column(name = "message_type")
	private int messageType;

	@Column(name = "activity_id")
	private int activityId;

	@Column(name = "activity_source_address")
	private String activitySourceAddress;

	@Column(name = "activity_dest_address")
	private String activityDestAddress;

	@Column(name = "apply_id")
	private int applyId;

	/**
	 * uid =0 means from system, it indicates the system event, such as the
	 * 'activity update/cancel event','activity apply event', apply
	 * 'approve/unapprove/cancel event', etc.
	 */
	@Column(name = "from_uid")
	private int fromUid;

	@Column(name = "from_name")
	private String fromName;

	@Column(name = "to_uid")
	private int toUid;

	@Column(name = "to_name")
	private String toName;

	private String content;

	/**
	 * 0 - unread, 1- read
	 */
	private int status;

	@Column(name = "is_reply")
	private int isReply;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	public int getUserMessageId() {
		return messageId;
	}

	public void setUserMessageId(int userMessageId) {
		this.messageId = userMessageId;
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

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getApplyId() {
		return applyId;
	}

	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}

	public String getActivitySourceAddress() {
		return activitySourceAddress;
	}

	public void setActivitySourceAddress(String activitySourceAddress) {
		this.activitySourceAddress = activitySourceAddress;
	}

	public String getActivityDestAddress() {
		return activityDestAddress;
	}

	public void setActivityDestAddress(String activityDestAddress) {
		this.activityDestAddress = activityDestAddress;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public int getIsReply() {
		return isReply;
	}

	public void setIsReply(int isReply) {
		this.isReply = isReply;
	}

}
