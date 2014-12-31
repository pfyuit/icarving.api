package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
import cn.icarving.api.pinche.common.ApiMessage;
import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.dao.PickActivityApplyDao;
import cn.icarving.api.pinche.dao.PickActivityDao;
import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickActivityApply;
import cn.icarving.api.pinche.domain.User;
import cn.icarving.api.pinche.dto.PickActivityUpdateForm;

import com.google.common.base.Strings;

@Service
@Transactional(rollbackFor = Exception.class)
public class PickActivityService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PickActivityDao pickActivityDao;

	@Autowired
	private PickActivityApplyDao pickActivityApplyDao;

	@Autowired
	private UserMessageService userMessageService;

	@Autowired
	private PickActivityApplyService pickActivityApplyService;

	public void createPickActivity(PickActivity pickActivity) {
		pickActivityDao.save(pickActivity);
	}

	public PickActivity updatePickActivity(PickActivityUpdateForm form) {
		int pickActiviyId = form.getPickActivityId();
		PickActivity pickActivity = pickActivityDao.find(pickActiviyId);
		if (!Strings.isNullOrEmpty(form.getCarType())) {
			pickActivity.setCarType(form.getCarType());
		}
		if (!Strings.isNullOrEmpty(form.getDestAddress())) {
			pickActivity.setDestAddress(form.getDestAddress());
		}
		if (!Strings.isNullOrEmpty(form.getNote())) {
			pickActivity.setNote(form.getNote());
		}
		if (!Strings.isNullOrEmpty(form.getReturnTime())) {
			pickActivity.setReturnTime(Timestamp.valueOf(form.getReturnTime()));
		}
		if (!Strings.isNullOrEmpty(form.getSourceAddress())) {
			pickActivity.setSourceAddress(form.getSourceAddress());
		}
		if (!Strings.isNullOrEmpty(form.getStartTime())) {
			pickActivity.setStartTime(Timestamp.valueOf(form.getStartTime()));
		}
		if (form.getCharge().doubleValue() > 0) {
			pickActivity.setCharge(form.getCharge());
		}
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityDao.update(pickActivity);

		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivity.getOwnerId(), "您已更新捡人活动" + pickActiviyId);
		List<PickActivityApply> result = pickActivityApplyService.findPickActivityApplyByPickActivity(pickActiviyId);
		for (PickActivityApply apply : result) {
			userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, apply.getApplyUserId(), "捡人活动" + pickActiviyId + "已被发起人更新");
		}

		return pickActivity;
	}

	public List<PickActivity> findPickActivityByUser(int uid) {
		List<PickActivity> result = pickActivityDao.findPickActivityByUser(uid);
		return result;
	}

	public List<PickActivity> findPickActivityAll() {
		List<PickActivity> result = pickActivityDao.findPickActivityAll();
		return result;
	}

	public void cancelPickActivity(int uid, int pickActivityId) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER.getMessage());
		}

		PickActivity pickActivity = pickActivityDao.find(pickActivityId);
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(),
					ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		pickActivity.setStatus(ApiStatus.ACTIVITY_STATUS_CANCELLED.getStatus());
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityDao.update(pickActivity);

		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivity.getOwnerId(), "您已取消捡人活动" + pickActivityId + "。所有申请被自动取消");
		List<PickActivityApply> pickActivityApplies = pickActivityApplyDao.findPickActivityApplyByPickActivity(pickActivityId);
		for (PickActivityApply pickActivityApply : pickActivityApplies) {
			pickActivityApply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED.getStatus());
			pickActivityApplyDao.update(pickActivityApply);
			userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivityApply.getApplyUserId(), "捡人活动" + pickActivityApply + "已被发起人取消，您的申请被自动取消");
		}
	}

}
