package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;

import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickedActivity;

public class ActivityDtoBuilder {
	private static final int ACTIVITY_TYPE_PICK = 1;
	private static final int ACTIVITY_TYPE_PICKED = 2;

	public static ActivityDto buildPickActivity(PickActivity pick) {
		ActivityDto dto = new ActivityDto();
		dto.setApplyNumber(pick.getApplyNumber());
		dto.setApproveNumber(pick.getApproveNumber());
		dto.setCapacity(pick.getCapacity());
		dto.setCarType(pick.getCarType());
		dto.setCharge(pick.getCharge());
		dto.setDestAddress(pick.getDestAddress());
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getLastModify()));
		dto.setNote(pick.getNote());
		dto.setOwnerId(pick.getOwnerId());
		dto.setOwnerName(pick.getOwnerName());
		dto.setOwnerPhone(pick.getOwnerPhone());
		dto.setOwnerAvatar(pick.getOwnerAvatar());
		dto.setActivityId(pick.getPickActivityId());
		dto.setActivityType(ACTIVITY_TYPE_PICK);
		dto.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getPublishTime()));
		dto.setReturnTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getReturnTime()));
		dto.setSourceAddress(pick.getSourceAddress());
		dto.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getStartTime()));
		dto.setStatus(ApiStatus.fromStatus(pick.getStatus()).getDescription());
		return dto;
	}

	public static ActivityDto buildPickedActivity(PickedActivity picked) {
		ActivityDto dto = new ActivityDto();
		dto.setCapacity(picked.getCapacity());
		dto.setCarType(picked.getCarType());
		dto.setCharge(picked.getCharge());
		dto.setDestAddress(picked.getDestAddress());
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getLastModify()));
		dto.setNote(picked.getNote());
		dto.setOwnerId(picked.getOwnerId());
		dto.setOwnerName(picked.getOwnerName());
		dto.setOwnerPhone(picked.getOwnerPhone());
		dto.setOwnerAvatar(picked.getOwnerAvatar());
		dto.setActivityId(picked.getPickedActivityId());
		dto.setActivityType(ACTIVITY_TYPE_PICKED);
		dto.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getPublishTime()));
		dto.setReturnTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getReturnTime()));
		dto.setSourceAddress(picked.getSourceAddress());
		dto.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getStartTime()));
		dto.setStatus(ApiStatus.fromStatus(picked.getStatus()).getDescription());
		return dto;
	}

}
