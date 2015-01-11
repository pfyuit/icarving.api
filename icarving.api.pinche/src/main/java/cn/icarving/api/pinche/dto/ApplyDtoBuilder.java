package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;

import cn.icarving.api.pinche.domain.PickActivityApply;
import cn.icarving.api.pinche.domain.PickedActivityApply;

public class ApplyDtoBuilder {

	public static ApplyDto buildPickActivityApply(PickActivityApply pickApply, String ownerName) {
		ApplyDto dto = new ApplyDto();
		dto.setApplyId(pickApply.getPickActivityApplyId());
		dto.setActivityId(pickApply.getPickActivityId());
		dto.setOwnerId(pickApply.getApplyUserId());
		dto.setOwnerName(ownerName);
		dto.setStatus(pickApply.getStatus());
		dto.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(pickApply.getApplyTime()));
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(pickApply.getLastModify()));
		return dto;
	}

	public static ApplyDto buildPickedActivityApply(PickedActivityApply pickedApply, String ownerName) {
		ApplyDto dto = new ApplyDto();
		dto.setApplyId(pickedApply.getPickedActivityApplyId());
		dto.setActivityId(pickedApply.getPickedActivityId());
		dto.setOwnerId(pickedApply.getApplyUserId());
		dto.setOwnerName(ownerName);
		dto.setStatus(pickedApply.getStatus());
		dto.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(pickedApply.getApplyTime()));
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(pickedApply.getLastModify()));
		return dto;
	}

}
