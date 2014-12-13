package cn.icarving.api.pinche.dto;

import java.math.BigDecimal;

public class PickActivityUpdateForm {

	private int pickActivityId;

	private String startTime;

	private String returnTime;

	private String sourceAddress;

	private String destAddress;

	private BigDecimal charge;

	private String carType;

	private String note;

	public int getPickActivityId() {
		return pickActivityId;
	}

	public void setPickActivityId(int pickActivityId) {
		this.pickActivityId = pickActivityId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
