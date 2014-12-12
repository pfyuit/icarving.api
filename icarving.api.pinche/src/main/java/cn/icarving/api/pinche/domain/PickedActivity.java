package cn.icarving.api.pinche.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "picked_activity_id")
public class PickedActivity {

	@Id
	@Column(name = "picked_activity_id")
	private String pickedActivityId;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "return_time")
	private Timestamp returnTime;

	@Column(name = "source_address")
	private String sourceAddress;

	@Column(name = "dest_address")
	private String destAddress;

	private BigDecimal charge;

	@Column(name = "car_type")
	private String carType;

	private String status;

	private String note;

	@Column(name = "publish_time")
	private Timestamp publishTime;

	@Column(name = "last_modify")
	private Timestamp lastModify;

	public String getPickedActivityId() {
		return pickedActivityId;
	}

	public void setPickedActivityId(String pickedActivityId) {
		this.pickedActivityId = pickedActivityId;
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

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
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
