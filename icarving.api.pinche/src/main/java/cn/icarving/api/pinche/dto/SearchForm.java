package cn.icarving.api.pinche.dto;

public class SearchForm {
	private String startTime;
	private String returnTime;
	private String sourceAddress;
	private String destAddress;

	public SearchForm() {
	}

	public SearchForm(String startTime, String returnTime, String sourceAddress, String destAddress) {
		this.startTime = startTime;
		this.returnTime = returnTime;
		this.sourceAddress = sourceAddress;
		this.destAddress = destAddress;
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

}
