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
import cn.icarving.api.pinche.dao.ApplyDao;
import cn.icarving.api.pinche.dao.ActivityDao;
import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.Activity;
import cn.icarving.api.pinche.domain.Apply;
import cn.icarving.api.pinche.domain.User;
import cn.icarving.api.pinche.dto.ActivityCreateForm;
import cn.icarving.api.pinche.dto.ActivityUpdateForm;

import com.google.common.base.Strings;

@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private ApplyDao applyDao;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ApplyService applyService;

	public Activity createActivity(ActivityCreateForm form) {
		User user = userService.findUserByUid(form.getOwnerId());
		Activity activity = new Activity();
		activity.setActivityType(form.getActivityType());
		activity.setOwnerId(form.getOwnerId());
		activity.setOwnerName(user.getName());
		activity.setOwnerPhone(form.getPhone());
		activity.setOwnerAvatar(user.getAvatar());
		activity.setOwnerCountry(user.getCountry());
		activity.setOwnerProvince(user.getProvince());
		activity.setOwnerCity(user.getCity());
		activity.setStartTime(Timestamp.valueOf(form.getStartTime()));
		activity.setReturnTime(Timestamp.valueOf(form.getReturnTime()));
		activity.setSourceAddress(form.getSourceAddress());
		activity.setDestAddress(form.getDestAddress());
		activity.setCharge(form.getCharge());
		activity.setVenue(form.getVenue());
		activity.setCarType(form.getCarType());
		activity.setCapacity(form.getCapacity());
		activity.setApplyNumber(0);
		activity.setApproveNumber(0);
		activity.setNote(form.getNote());
		activity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		activity.setPublishTime(new Timestamp(new Date().getTime()));
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.save(activity);
		return activity;
	}

	public Activity updateActivity(ActivityUpdateForm form) {
		int activiyId = form.getActivityId();
		Activity activity = activityDao.find(activiyId);
		if (!Strings.isNullOrEmpty(form.getCarType())) {
			activity.setCarType(form.getCarType());
		}
		if (!Strings.isNullOrEmpty(form.getDestAddress())) {
			activity.setDestAddress(form.getDestAddress());
		}
		if (!Strings.isNullOrEmpty(form.getNote())) {
			activity.setNote(form.getNote());
		}
		if (!Strings.isNullOrEmpty(form.getReturnTime())) {
			activity.setReturnTime(Timestamp.valueOf(form.getReturnTime()));
		}
		if (!Strings.isNullOrEmpty(form.getSourceAddress())) {
			activity.setSourceAddress(form.getSourceAddress());
		}
		if (!Strings.isNullOrEmpty(form.getStartTime())) {
			activity.setStartTime(Timestamp.valueOf(form.getStartTime()));
		}
		if (!Strings.isNullOrEmpty(form.getCharge())) {
			activity.setCharge(form.getCharge());
		}
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activiyId, activity.getSourceAddress(), activity.getDestAddress(), ApiMessage.NO_APPLY_ID,
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您已更新捡人活动", 0);
		List<Apply> result = applyService.findApplyByActivity(activiyId);
		for (Apply apply : result) {
			messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activiyId, activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
					ApiMessage.SYSTEM_UID, apply.getOwnerId(), activity.getOwnerName(), "捡人活动已被发起人更新", 0);
		}

		return activity;
	}

	public List<Activity> findActivityByUser(int uid) {
		List<Activity> result = activityDao.findActivityByUser(uid);
		return result;
	}

	public List<Activity> findActivityAll() {
		List<Activity> result = activityDao.findActivityAll();
		return result;
	}

	public void cancelActivity(int uid, int activityId) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}

		Activity activity = activityDao.find(activityId);
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		if (ApiStatus.fromStatus(activity.getStatus()) == ApiStatus.ACTIVITY_STATUS_CANCELLED) {
			throw new ApiException(ApiEnum.ACTIVITY_ALREADY_CANCELLED.getCode(), ApiEnum.ACTIVITY_ALREADY_CANCELLED.getMessage());
		}
		activity.setStatus(ApiStatus.ACTIVITY_STATUS_CANCELLED.getStatus());
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activityId, activity.getSourceAddress(), activity.getDestAddress(), ApiMessage.NO_APPLY_ID,
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您已取消捡人活动。所有申请被自动取消", 0);
		List<Apply> applies = applyDao.findApplyByActivity(activityId);
		for (Apply apply : applies) {
			apply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED.getStatus());
			applyDao.update(apply);
			messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activityId, activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
					ApiMessage.SYSTEM_UID, apply.getOwnerId(), activity.getOwnerName(), "捡人活动已被发起人取消，您的申请被自动取消", 0);
		}
	}

}
