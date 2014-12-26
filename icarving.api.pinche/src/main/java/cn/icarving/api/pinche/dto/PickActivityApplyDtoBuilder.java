package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;

import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.domain.PickActivityApply;

public class PickActivityApplyDtoBuilder {

	public static PickActivityApplyDto build(PickActivityApply pickActivityApply) {
		PickActivityApplyDto dto = new PickActivityApplyDto();
		dto.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pickActivityApply.getApplyTime()));
		dto.setApplyUserId(pickActivityApply.getApplyUserId());
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pickActivityApply.getLastModify()));
		dto.setPickActivityApplyId(pickActivityApply.getPickActivityApplyId());
		dto.setPickActivityId(pickActivityApply.getPickActivityId());
		dto.setStatus(ApiStatus.fromStatus(pickActivityApply.getStatus()).getDescription());
		return dto;
	}

}
