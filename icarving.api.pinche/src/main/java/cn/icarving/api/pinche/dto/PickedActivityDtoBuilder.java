package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;

import cn.icarving.api.pinche.domain.PickedActivity;

public class PickedActivityDtoBuilder {

	public static PickedActivityDto build(PickedActivity picked) {
		PickedActivityDto dto = new PickedActivityDto();
		dto.setCarType(picked.getCarType());
		dto.setCharge(picked.getCharge());
		dto.setDestAddress(picked.getDestAddress());
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getLastModify()));
		dto.setNote(picked.getNote());
		dto.setOwnerId(picked.getOwnerId());
		dto.setPickedActivityId(picked.getPickedActivityId());
		dto.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getPublishTime()));
		dto.setReturnTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getReturnTime()));
		dto.setSourceAddress(picked.getSourceAddress());
		dto.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(picked.getStartTime()));
		dto.setStatus(picked.getStatus());
		return dto;
	}

}
