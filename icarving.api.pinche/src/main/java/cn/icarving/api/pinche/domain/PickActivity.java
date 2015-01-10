package cn.icarving.api.pinche.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pick_activity")
public class PickActivity {

	@Id
	@GeneratedValue
	@Column(name = "pick_activity_id")
	private int pickActivityId;

	@Column(name = "owner_id")
	private int ownerId;

	@Column(name = "owner_name")
	private String ownerName;

	@Column(name = "owner_phone")
	private int ownerPhone;

	@Column(name = "owner_avatar")
	private String ownerAvatar;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "return_time")
	private Timestamp returnTime;

	@Column(name = "source_address")
	private String sourceAddress;

	@Column(name = "dest_address")
	private String destAddress;

	private String venue;

	private String charge;

	@Column(name = "car_type")
	private String carType;

	private int capacity;

	@Column(name = "apply_number")
	private int applyNumber;

	@Column(name = "approve_number")
	private int approveNumber;

	private String status;

	private String note;

	@Column(name = "publish_time")
	private Timestamp publishTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	public int getPickActivityId() {
		return pickActivityId;
	}

	public void setPickActivityId(int pickActivityId) {
		this.pickActivityId = pickActivityId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(int ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getOwnerAvatar() {
		return ownerAvatar;
	}

	public void setOwnerAvatar(String ownerAvatar) {
		this.ownerAvatar = ownerAvatar;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(int applyNumber) {
		this.applyNumber = applyNumber;
	}

	public int getApproveNumber() {
		return approveNumber;
	}

	public void setApproveNumber(int approveNumber) {
		this.approveNumber = approveNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Timestamp getLastModify() {
		return lastModify;
	}

	public void setLastModify(Timestamp lastModify) {
		this.lastModify = lastModify;
	}

}
