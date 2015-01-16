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
import cn.icarving.api.pinche.dao.ActivityDao;
import cn.icarving.api.pinche.dao.ApplyDao;
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
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}

		Apply apply = new Apply();
		apply.setActivityId(form.getActivityId());
		apply.setOwnerId(form.getApplyUserId());
		apply.setOwnerName(user.getName());
		apply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		apply.setApplyTime(new Timestamp(new Date().getTime()));
		apply.setLastModify(new Timestamp(new Date().getTime()));
		List<Apply> applies = applyDao.findApplyByUser(apply.getOwnerId());
		for (Apply app : applies) {
			if (app.getActivityId() == apply.getActivityId()) {
				throw new ApiException(ApiEnum.APPLY_ALREADY_APPLIED_ACTIVITY.getCode(), ApiEnum.APPLY_ALREADY_APPLIED_ACTIVITY.getMessage());
			}
		}
		applyDao.save(apply);

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

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您的活动有一条新的申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), apply.getOwnerName(), "您已申请活动", 0);
		return apply;
	}

	public Apply approveApply(int applyId) {
		Apply apply = applyDao.find(applyId);
		if (apply == null) {
			throw new ApiException(ApiEnum.APPLY_CANNOT_FIND.getCode(), ApiEnum.APPLY_CANNOT_FIND.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_APPROVED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_APPROVED.getCode(), ApiEnum.APPLY_ALREADY_APPROVED.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_REJECTED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_REJECTED.getCode(), ApiEnum.APPLY_ALREADY_REJECTED.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_CANCELLED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_CANCELLED.getCode(), ApiEnum.APPLY_ALREADY_CANCELLED.getMessage());
		}
		apply.setStatus(ApiStatus.APPLY_STATUS_APPROVED.getStatus());
		apply.setLastModify(new Timestamp(new Date().getTime()));
		applyDao.update(apply);

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		if (!activity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
			throw new ApiException(ApiEnum.APPLY_ACTIVITY_INVALID.getCode(), ApiEnum.APPLY_ACTIVITY_INVALID.getMessage());
		}
		int approveNumber = activity.getApproveNumber();
		approveNumber = approveNumber + 1;
		activity.setApproveNumber(approveNumber);
		activity.setLastModify(new Timestamp(new Date().getTime()));
		if (activity.getActivityType() == 1) {
			if (activity.getApproveNumber() == activity.getCapacity()) {
				activity.setStatus(ApiStatus.ACTIVITY_STATUS_FINISHED.getStatus());
			}
		}
		if (activity.getActivityType() == 2) {
			activity.setStatus(ApiStatus.ACTIVITY_STATUS_FINISHED.getStatus());
		}
		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您已批准活动申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), apply.getOwnerName(), "您的活动申请已被批准", 0);

		return apply;
	}

	public Apply unApproveApply(int applyId) {
		Apply apply = applyDao.find(applyId);
		if (apply == null) {
			throw new ApiException(ApiEnum.APPLY_CANNOT_FIND.getCode(), ApiEnum.APPLY_CANNOT_FIND.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_REJECTED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_REJECTED.getCode(), ApiEnum.APPLY_ALREADY_REJECTED.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_CANCELLED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_CANCELLED.getCode(), ApiEnum.APPLY_ALREADY_CANCELLED.getMessage());
		}
		String oldStatus = apply.getStatus();
		apply.setStatus(ApiStatus.APPLY_STATUS_REJECTED.getStatus());
		apply.setLastModify(new Timestamp(new Date().getTime()));
		applyDao.update(apply);

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		if (activity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_CANCELLED.getStatus())) {
			throw new ApiException(ApiEnum.ACTIVITY_ALREADY_CANCELLED.getCode(), ApiEnum.ACTIVITY_ALREADY_CANCELLED.getMessage());
		}
		if (ApiStatus.fromStatus(oldStatus) == ApiStatus.APPLY_STATUS_APPROVED) {
			int approveNumber = activity.getApproveNumber();
			approveNumber = approveNumber - 1;
			activity.setApproveNumber(approveNumber);
			activity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		}
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您已拒绝活动申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), apply.getOwnerName(), "您的活动申请已被拒绝", 0);

		return apply;
	}

	public Apply cancelApply(int uid, int applyId) {
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
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_CANCELLED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_CANCELLED.getCode(), ApiEnum.APPLY_ALREADY_CANCELLED.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_REJECTED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_REJECTED.getCode(), ApiEnum.APPLY_ALREADY_REJECTED.getMessage());
		}
		String oldStatus = apply.getStatus();
		apply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED.getStatus());
		apply.setLastModify(new Timestamp(new Date().getTime()));
		applyDao.update(apply);

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		if (activity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_CANCELLED.getStatus())) {
			throw new ApiException(ApiEnum.ACTIVITY_ALREADY_CANCELLED.getCode(), ApiEnum.ACTIVITY_ALREADY_CANCELLED.getMessage());
		}
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_APPROVED.getStatus())) {
			int approveNumber = activity.getApproveNumber();
			approveNumber = approveNumber - 1;
			activity.setApproveNumber(approveNumber);
			activity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		}
		activity.setLastModify(new Timestamp(new Date().getTime()));
		activityDao.update(activity);

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), apply.getOwnerName(), "您已取消活动申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "活动申请已被申请人取消", 0);

		return apply;
	}

	public Apply renewApply(int applyId) {
		Apply apply = applyDao.find(applyId);
		if (apply == null) {
			throw new ApiException(ApiEnum.APPLY_CANNOT_FIND.getCode(), ApiEnum.APPLY_CANNOT_FIND.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_UNAPPROVED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_UNAPPROVED.getCode(), ApiEnum.APPLY_ALREADY_UNAPPROVED.getMessage());
		}
		if (ApiStatus.fromStatus(apply.getStatus()) == ApiStatus.APPLY_STATUS_APPROVED) {
			throw new ApiException(ApiEnum.APPLY_ALREADY_APPROVED.getCode(), ApiEnum.APPLY_ALREADY_APPROVED.getMessage());
		}
		apply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		apply.setLastModify(new Timestamp(new Date().getTime()));
		applyDao.update(apply);

		Activity activity = activityDao.find(apply.getActivityId());
		if (activity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANNOT_FIND.getCode(), ApiEnum.ACTIVITY_CANNOT_FIND.getMessage());
		}
		if (!activity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
			throw new ApiException(ApiEnum.APPLY_ACTIVITY_INVALID.getCode(), ApiEnum.APPLY_ACTIVITY_INVALID.getMessage());
		}

		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, activity.getOwnerId(), activity.getOwnerName(), "您的活动有一条再次申请", 0);
		messageService.createUserMessage(ApiMessage.MESSAGE_TYPE_NOTIFY, activity.getActivityId(), activity.getSourceAddress(), activity.getDestAddress(), apply.getApplyId(),
				ApiMessage.SYSTEM_UID, apply.getOwnerId(), apply.getOwnerName(), "您已再次申请活动", 0);

		return apply;
	}

	public Apply findApplyByApplyId(int applyId) {
		Apply result = applyDao.find(applyId);
		return result;
	}

	public List<Apply> findApplyByActivityId(int activityId) {
		List<Apply> applies = applyDao.findApplyByActivity(activityId);
		return applies;
	}

}
