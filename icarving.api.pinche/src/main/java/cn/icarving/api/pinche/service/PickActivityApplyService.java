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

@Service
@Transactional(rollbackFor = Exception.class)
public class PickActivityApplyService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PickActivityDao pickActivityDao;

	@Autowired
	private PickActivityApplyDao pickActivityApplyDao;

	@Autowired
	private UserMessageService userMessageService;

	public void createPickActivityApply(PickActivityApply pickActivityApply) {
		int applyUserId = pickActivityApply.getApplyUserId();
		List<PickActivityApply> applies = findPickActivityApplyByUser(applyUserId);
		for (PickActivityApply apply : applies) {
			if (apply.getPickActivityId() == pickActivityApply.getPickActivityId()) {
				throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_ALREADY_APPLIED_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_ALREADY_APPLIED_ACTIVITY.getMessage());
			}
		}

		PickActivity pickActivity = pickActivityDao.find(pickActivityApply.getPickActivityId());
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		if (!pickActivity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
			throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_INVALID_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_INVALID_ACTIVITY.getMessage());
		}
		int applyNumber = pickActivity.getApplyNumber();
		applyNumber = applyNumber + 1;
		pickActivity.setApplyNumber(applyNumber);
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityDao.update(pickActivity);

		pickActivityApplyDao.save(pickActivityApply);

		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivity.getOwnerId(), "您的捡人活动" + pickActivity.getPickActivityId() + "有一条新的申请");
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, applyUserId, "您已申请捡人活动" + pickActivity.getPickActivityId());
	}

	public void approvePickActivityApply(int pickActivityApplyId) {
		PickActivityApply pickActivityApply = pickActivityApplyDao.find(pickActivityApplyId);
		if (pickActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickActivityApply.setStatus(ApiStatus.APPLY_STATUS_APPROVED.getStatus());
		pickActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityApplyDao.update(pickActivityApply);

		PickActivity pickActivity = pickActivityDao.find(pickActivityApply.getPickActivityId());
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		int approveNumber = pickActivity.getApproveNumber();
		approveNumber = approveNumber + 1;
		int applyNumber = pickActivity.getApplyNumber();
		applyNumber = applyNumber - 1;
		pickActivity.setApplyNumber(applyNumber);
		pickActivity.setApproveNumber(approveNumber);
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		if (pickActivity.getApplyNumber() == pickActivity.getCapacity()) {
			pickActivity.setStatus(ApiStatus.ACTIVITY_STATUS_FINISHED.getStatus());
		}
		pickActivityDao.update(pickActivity);

		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivity.getOwnerId(), "您已批准捡人活动" + pickActivity.getPickActivityId() + "的申请" + pickActivityApplyId);
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivityApply.getApplyUserId(), "您的捡人活动申请" + pickActivityApply.getPickActivityApplyId() + "已被批准");
	}

	public void unApprovePickActivityApply(int pickActivityApplyId) {
		PickActivityApply pickActivityApply = pickActivityApplyDao.find(pickActivityApplyId);
		if (pickActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickActivityApply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		pickActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityApplyDao.update(pickActivityApply);

		PickActivity pickActivity = pickActivityDao.find(pickActivityApply.getPickActivityId());
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(),
					ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		int approveNumber = pickActivity.getApproveNumber();
		approveNumber = approveNumber - 1;
		int applyNumber = pickActivity.getApplyNumber();
		applyNumber = applyNumber + 1;
		pickActivity.setApplyNumber(applyNumber);
		pickActivity.setApproveNumber(approveNumber);
		pickActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityDao.update(pickActivity);
		
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivity.getOwnerId(), "您已拒绝捡人活动" + pickActivity.getPickActivityId() + "的申请" + pickActivityApplyId);
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivityApply.getApplyUserId(), "您的捡人活动申请" + pickActivityApply.getPickActivityApplyId() + "已被拒绝");
	}

	public List<PickActivityApply> findPickActivityApplyByUser(int uid) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getMessage());
		}

		List<PickActivityApply> result = pickActivityApplyDao.findPickActivityApplyByUser(uid);
		return result;
	}

	public List<PickActivityApply> findPickActivityApplyByPickActivity(int pickActivityId) {
		List<PickActivityApply> result = pickActivityApplyDao.findPickActivityApplyByPickActivity(pickActivityId);
		return result;
	}

	public void cancelPickActivityApply(int uid, int pickActivityApplyId) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_USER.getMessage());
		}

		PickActivityApply pickActivityApply = pickActivityApplyDao.find(pickActivityApplyId);
		if (pickActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		if (pickActivityApply.getApplyUserId() != uid) {
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_NOT_OWNER_OF_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_CANCEL_FAILED_NOT_OWNER_OF_PICK_ACTIVITY_APPLY.getMessage());
		}
		String oldStatus = pickActivityApply.getStatus();
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_CANCELLED.getStatus())) {
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_ALREADY_CANCELLED_APPLY.getCode(), ApiEnum.APPLY_CANCEL_FAILED_ALREADY_CANCELLED_APPLY.getMessage());
		}
		pickActivityApply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED.getStatus());
		pickActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityApplyDao.update(pickActivityApply);

		PickActivity pickActivity = pickActivityDao.find(pickActivityApply.getPickActivityId());
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(),
					ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		int applyNumber = pickActivity.getApplyNumber();
		int approveNumber = pickActivity.getApproveNumber();
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_APPROVED.getStatus())) {
			approveNumber = approveNumber - 1;
		}
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus())) {
			applyNumber = applyNumber - 1;
		}
		pickActivity.setApplyNumber(applyNumber);
		pickActivity.setApproveNumber(approveNumber);
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		pickActivityDao.update(pickActivity);
		
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivityApply.getApplyUserId(), "您已取消捡人活动申请" + pickActivityApply.getPickActivityApplyId());
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickActivity.getOwnerId(), "捡人活动申请" + pickActivityApply.getPickActivityApplyId()+"已被申请人取消");
	}

}
