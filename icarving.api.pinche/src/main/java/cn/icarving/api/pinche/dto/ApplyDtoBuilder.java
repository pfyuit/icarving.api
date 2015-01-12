package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;

import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.domain.Apply;

public class ApplyDtoBuilder {

	public static ApplyDto buildApply(Apply apply) {
		ApplyDto dto = new ApplyDto();
		dto.setApplyId(apply.getApplyId());
		dto.setActivityId(apply.getActivityId());
		dto.setOwnerId(apply.getOwnerId());
		dto.setOwnerName(apply.getOwnerName());
		dto.setStatus(ApiStatus.fromStatus(apply.getStatus()).getDescription());
		dto.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(apply.getApplyTime()));
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(apply.getLastModify()));
		return dto;
	}
}
