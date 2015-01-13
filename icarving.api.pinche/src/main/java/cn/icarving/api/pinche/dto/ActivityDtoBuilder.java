package cn.icarving.api.pinche.dto;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.domain.Activity;
import cn.icarving.api.pinche.domain.Apply;

import com.google.common.collect.Lists;

public class ActivityDtoBuilder {

	public static ActivityDto buildActivity(Activity activity, List<Apply> applies) {
		ActivityDto dto = new ActivityDto();
		dto.setApplyNumber(activity.getApplyNumber());
		dto.setApproveNumber(activity.getApproveNumber());
		dto.setCapacity(activity.getCapacity());
		dto.setCarType(activity.getCarType());
		dto.setCharge(activity.getCharge());
		dto.setDestAddress(activity.getDestAddress());
		dto.setLastModify(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getLastModify()));
		dto.setNote(activity.getNote());
		dto.setOwnerId(activity.getOwnerId());
		dto.setOwnerName(activity.getOwnerName());
		dto.setOwnerPhone(activity.getOwnerPhone());
		dto.setOwnerAvatar(activity.getOwnerAvatar());
		dto.setOwnerCountry(activity.getOwnerCountry());
		dto.setOwnerProvince(activity.getOwnerProvince());
		dto.setOwnerCity(activity.getOwnerCity());
		dto.setActivityId(activity.getActivityId());
		dto.setActivityType(activity.getActivityType());
		dto.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getPublishTime()));
		dto.setReturnTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getReturnTime()));
		dto.setSourceAddress(activity.getSourceAddress());
		dto.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getStartTime()));
		dto.setStatus(ApiStatus.fromStatus(activity.getStatus()).getDescription());

		if (applies != null && !applies.isEmpty()) {
			List<ApplyDto> applyDtos = Lists.newArrayList();
			for (Apply apply : applies) {
				ApplyDto applyDto = ApplyDtoBuilder.buildApply(apply);
				applyDtos.add(applyDto);
			}
			dto.setApplies(applyDtos);
		}

		return dto;
	}

}
