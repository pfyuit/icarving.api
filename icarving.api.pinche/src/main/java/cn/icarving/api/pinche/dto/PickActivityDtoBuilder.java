package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;

import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.domain.PickActivity;

public class PickActivityDtoBuilder {

	public static PickActivityDto build(PickActivity pick) {
		PickActivityDto dto = new PickActivityDto();
		dto.setApplyNumber(pick.getApplyNumber());
		dto.setApproveNumber(pick.getApproveNumber());
		dto.setCapacity(pick.getCapacity());
		dto.setCarType(pick.getCarType());
		dto.setCharge(pick.getCharge());
		dto.setDestAddress(pick.getDestAddress());
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getLastModify()));
		dto.setNote(pick.getNote());
		dto.setOwnerId(pick.getOwnerId());
		dto.setPickActivityId(pick.getPickActivityId());
		dto.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getPublishTime()));
		dto.setReturnTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getReturnTime()));
		dto.setSourceAddress(pick.getSourceAddress());
		dto.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pick.getStartTime()));
		dto.setStatus(ApiStatus.fromStatus(pick.getStatus()).getDescription());
		return dto;
	}

}
