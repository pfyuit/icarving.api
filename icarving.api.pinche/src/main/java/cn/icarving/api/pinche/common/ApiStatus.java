package cn.icarving.api.pinche.common;

public enum ApiStatus {
	
	ACTIVITY_STATUS_VALID("valid","有效"),
	ACTIVITY_STATUS_FINISHED("finished","已结束"),
	ACTIVITY_STATUS_CANCELLED("cancelled","已取消"),
	
	APPLY_STATUS_UNAPPROVED("unapproved","等待批准"),
	APPLY_STATUS_APPROVED("approved","已批准"),
	APPLY_STATUS_REJECTED("rejected","已拒绝"),
	APPLY_STATUS_CANCELLED("cancelled","已取消");

	private String status;
	private String description;

	private ApiStatus(String status, String description) {
		this.status = status;
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static ApiStatus fromStatus(String status) {
		for (ApiStatus apiStatus : ApiStatus.values()) {
			if (apiStatus.getStatus().equals(status)) {
				return apiStatus;
			}
		}
		throw new IllegalStateException("Not a valid status");
	}
	
}
