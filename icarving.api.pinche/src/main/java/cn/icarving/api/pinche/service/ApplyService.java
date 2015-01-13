package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

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
import cn.icarving.api.pinche.dto.ApplyCreateForm;

@Service
@Transactional(rollbackFor = Exception.class)
public class ApplyService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private ApplyDao applyDao;

	@Autowired
	private MessageService messageService;

	public Apply createApply(ApplyCreateForm form) {
		User user = userDao.find(form.getApplyUserId());
		Apply apply = new Apply();
		apply.setActivityId(form.getActivityId());
		apply.setOwnerId(form.getApplyUserId());
		apply.setOwnerName(user.getName());
		apply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		apply.setApplyTime(new Timestamp(new Date().getTime()));
		apply.setLastModify(new Timestamp(new Date().getTime()));

		int applyUserId = apply.getOwnerId();
		List<Apply> applies = findApplyByUser(applyUserId);
		for (Apply app : applies) {
			if (app.getActivityId() == app.getActivityId()) {
				throw new ApiException(ApiEnum.APPLY_ALREADY_APPLIED_ACTIVITY.getCode(), ApiEnum.APPLY_ALREADY_APPLIED_ACTIVITY.getMessage());
			}
		}

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		if (!activity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
			throw new ApiException(ApiEnum.APPLY_ACTIVITY_INVALID.getCode(), ApiEnum.APPLY_ACTIVITY_INVALID.getMessage());
		}
		int applyNumber = activity.getApplyNumber();
		applyNumber = applyNumber + 1;
		activity.setApplyNumber(applyNumber);
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.update(activity);

		applyDao.save(apply);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您的捡人活动有一条新的申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, applyUserId, activity.getOwnerName(), "您已申请捡人活动", 0);
		return apply;
	}

	public void approveApply(int applyId) {
		Apply apply = applyDao.find(applyId);
		if (apply == null) {
			throw new ApiException(ApiEnum.APPLY_CANNOT_FIND.getCode(), ApiEnum.APPLY_CANNOT_FIND.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_APPROVED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_APPROVED.getCode(), ApiEnum.APPLY_ALREADY_APPROVED.getMessage());
		}
		apply.setStatus(ApiStatus.APPLY_STATUS_APPROVED.getStatus());
		apply.setLastModify(new Timestamp(new Date().getTime()));
		applyDao.update(apply);

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		int approveNumber = activity.getApproveNumber();
		approveNumber = approveNumber + 1;
		activity.setApproveNumber(approveNumber);
		activity.setLastModify(new Timestamp(new Date().getTime()));
		if (activity.getActivityType() == 1) {
			if (activity.getApplyNumber() == activity.getCapacity()) {
				activity.setStatus(ApiStatus.ACTIVITY_STATUS_FINISHED.getStatus());
			}
		}
		if (activity.getActivityType() == 2) {
			activity.setStatus(ApiStatus.ACTIVITY_STATUS_FINISHED.getStatus());
		}

		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您已批准捡人活动申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), activity.getOwnerName(), "您的捡人活动申请已被批准", 0);
	}

	public void unApproveApply(int applyId) {
		Apply apply = applyDao.find(applyId);
		if (apply == null) {
			throw new ApiException(ApiEnum.APPLY_CANNOT_FIND.getCode(), ApiEnum.APPLY_CANNOT_FIND.getMessage());
		}
		apply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		apply.setLastModify(new Timestamp(new Date().getTime()));
		applyDao.update(apply);

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}

		List<Apply> applies = findApplyByActivity(activity.getActivityId());
		for (Apply app : applies) {
			if (app.getApplyId() == applyId) {
				int approveNumber = activity.getApproveNumber();
				approveNumber = approveNumber - 1;
				activity.setApproveNumber(approveNumber);
				break;
			}
		}

		activity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您已拒绝捡人活动申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), activity.getOwnerName(), "您的捡人活动申请已被拒绝", 0);
	}

	public List<Apply> findApplyByUser(int uid) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}

		List<Apply> applies = applyDao.findApplyByUser(uid);
		List<Apply> result = Lists.newArrayList();
		for (Apply apply : applies) {
			if (apply.getStatus().equals(ApiStatus.APPLY_STATUS_APPROVED.getStatus()) || apply.getStatus().equals(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus())) {
				result.add(apply);
			}
		}
		return result;
	}

	public List<Apply> findApplyByActivity(int activityId) {
		List<Apply> applies = applyDao.findApplyByActivity(activityId);
		List<Apply> result = Lists.newArrayList();
		for (Apply apply : applies) {
			if (apply.getStatus().equals(ApiStatus.APPLY_STATUS_APPROVED.getStatus()) || apply.getStatus().equals(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus())) {
				result.add(apply);
			}
		}
		return result;
	}

	public void cancelApply(int uid, int applyId) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}

		Apply apply = applyDao.find(applyId);
		if (apply == null) {
			throw new ApiException(ApiEnum.APPLY_CANNOT_FIND.getCode(), ApiEnum.APPLY_CANNOT_FIND.getMessage());
		}
		if (apply.getOwnerId() != uid) {
			throw new ApiException(ApiEnum.APPLY_CANNOT_FIND.getCode(), ApiEnum.APPLY_CANNOT_FIND.getMessage());
		}
		String oldStatus = apply.getStatus();
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_CANCELLED.getStatus())) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_CANCELLED.getCode(), ApiEnum.APPLY_ALREADY_CANCELLED.getMessage());
		}
		apply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED.getStatus());
		apply.setLastModify(new Timestamp(new Date().getTime()));
		applyDao.update(apply);

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		int applyNumber = activity.getApplyNumber();
		int approveNumber = activity.getApproveNumber();
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_APPROVED.getStatus())) {
			approveNumber = approveNumber - 1;
			activity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		}
		applyNumber = applyNumber - 1;
		activity.setApplyNumber(applyNumber);
		activity.setApproveNumber(approveNumber);
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), apply.getOwnerName(), "您已取消捡人活动申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), apply.getOwnerName(), "捡人活动申请已被申请人取消", 0);
	}

}
