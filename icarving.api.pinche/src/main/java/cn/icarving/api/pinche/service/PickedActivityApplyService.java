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
import cn.icarving.api.pinche.dao.PickedActivityApplyDao;
import cn.icarving.api.pinche.dao.PickedActivityDao;
import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.PickedActivity;
import cn.icarving.api.pinche.domain.PickedActivityApply;
import cn.icarving.api.pinche.domain.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class PickedActivityApplyService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PickedActivityDao pickedActivityDao;

	@Autowired
	private PickedActivityApplyDao pickedActivityApplyDao;
	
	@Autowired
	private UserMessageService userMessageService;

	public void createPickedActivityApply(PickedActivityApply pickedActivityApply) {
		int applyUserId = pickedActivityApply.getApplyUserId();
		List<PickedActivityApply> applies = findPickedActivityApplyByUser(applyUserId);
		for (PickedActivityApply apply : applies) {
			if (apply.getPickedActivityId() == pickedActivityApply.getPickedActivityId()) {
				throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_ALREADY_APPLIED_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_ALREADY_APPLIED_ACTIVITY.getMessage());
			}
		}

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		if (!pickedActivity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
			throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_INVALID_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_INVALID_ACTIVITY.getMessage());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityDao.update(pickedActivity);

		pickedActivityApplyDao.save(pickedActivityApply);
		
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickedActivity.getOwnerId(), "您的搭车活动" + pickedActivity.getPickedActivityId() + "有一条新的申请");
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, applyUserId, "您已申请搭车活动" + pickedActivity.getPickedActivityId());
	}

	public void approvePickedActivityApply(int pickedActivityApplyId) {
		PickedActivityApply pickedActivityApply = pickedActivityApplyDao.find(pickedActivityApplyId);
		if (pickedActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickedActivityApply.setStatus(ApiStatus.APPLY_STATUS_APPROVED.getStatus());
		pickedActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityApplyDao.update(pickedActivityApply);

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_FINISHED.getStatus());
		pickedActivityDao.update(pickedActivity);
		
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickedActivity.getOwnerId(), "您已批准搭车活动" + pickedActivity.getPickedActivityId() + "的申请" + pickedActivityApplyId);
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickedActivityApply.getApplyUserId(), "您的搭车活动申请" + pickedActivityApply.getPickedActivityApplyId() + "已被批准");
	}

	public void unApprovePickedActivityApply(int pickedActivityApplyId) {
		PickedActivityApply pickedActivityApply = pickedActivityApplyDao.find(pickedActivityApplyId);
		if (pickedActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickedActivityApply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		pickedActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityApplyDao.update(pickedActivityApply);

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		pickedActivityDao.update(pickedActivity);
		
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickedActivity.getOwnerId(), "您已拒绝搭车活动" + pickedActivity.getPickedActivityId() + "的申请" + pickedActivityApplyId);
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickedActivityApply.getApplyUserId(), "您的搭车活动申请" + pickedActivityApply.getPickedActivityApplyId() + "已被拒绝");
	}

	public List<PickedActivityApply> findPickedActivityApplyByUser(int uid) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getMessage());
		}

		List<PickedActivityApply> result = pickedActivityApplyDao.findPickedActivityApplyByUser(uid);
		return result;
	}

	public List<PickedActivityApply> findPickedActivityApplyByPickedActivity(int pickedActivityId) {
		List<PickedActivityApply> result = pickedActivityApplyDao.findPickedActivityApplyByPickedActivity(pickedActivityId);
		return result;
	}

	public void cancelPickedActivityApply(int uid, int pickedActivityApplyId) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_USER.getMessage());
		}

		PickedActivityApply pickedActivityApply = pickedActivityApplyDao.find(pickedActivityApplyId);
		if (pickedActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(), ApiEnum.APPLY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		if (pickedActivityApply.getApplyUserId() != uid) {
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_NOT_OWNER_OF_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_CANCEL_FAILED_NOT_OWNER_OF_PICK_ACTIVITY_APPLY.getMessage());
		}
		String oldStatus = pickedActivityApply.getStatus();
		if(oldStatus.equals(ApiStatus.APPLY_STATUS_CANCELLED.getStatus())){
			throw new ApiException(ApiEnum.APPLY_CANCEL_FAILED_ALREADY_CANCELLED_APPLY.getCode(), ApiEnum.APPLY_CANCEL_FAILED_ALREADY_CANCELLED_APPLY.getMessage());
		}
		pickedActivityApply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED.getStatus());
		pickedActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityApplyDao.update(pickedActivityApply);

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_APPROVED.getStatus())) {
			pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID.getStatus());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityDao.update(pickedActivity);
		
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickedActivityApply.getApplyUserId(), "您已取消搭车活动申请" + pickedActivityApply.getPickedActivityApplyId());
		userMessageService.createUserMessage(ApiMessage.SYSTEM_UID, pickedActivity.getOwnerId(), "搭车活动申请" + pickedActivityApply.getPickedActivityApplyId()+"已被申请人取消");
	}

}
