package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;

import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.domain.PickedActivityApply;

public class PickedActivityApplyDtoBuilder {

	public static PickedActivityApplyDto build(PickedActivityApply pickedActivityApply) {
		PickedActivityApplyDto dto = new PickedActivityApplyDto();
		dto.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pickedActivityApply.getApplyTime()));
		dto.setApplyUserId(pickedActivityApply.getApplyUserId());
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pickedActivityApply.getLastModify()));
		dto.setPickedActivityApplyId(pickedActivityApply.getPickedActivityApplyId());
		dto.setPickedActivityId(pickedActivityApply.getPickedActivityId());
		dto.setStatus(ApiStatus.fromStatus(pickedActivityApply.getStatus()).getDescription());
		return dto;
	}

}
