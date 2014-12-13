package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
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

	public void createPickedActivityApply(PickedActivityApply pickedActivityApply) {
		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		if (!pickedActivity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID)) {
			throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_INVALID_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_INVALID_ACTIVITY.getMessage());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityDao.update(pickedActivity);

		pickedActivityApplyDao.save(pickedActivityApply);
	}

	public void approvePickedActivityApply(int pickedActivityApplyId) {
		PickedActivityApply pickedActivityApply = pickedActivityApplyDao.find(pickedActivityApplyId);
		if (pickedActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickedActivityApply.setStatus(ApiStatus.APPLY_STATUS_APPROVED);
		pickedActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityApplyDao.update(pickedActivityApply);

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_FINISHED);
		pickedActivityDao.update(pickedActivity);
	}

	public void unApprovePickedActivityApply(int pickedActivityApplyId) {
		PickedActivityApply pickedActivityApply = pickedActivityApplyDao.find(pickedActivityApplyId);
		if (pickedActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickedActivityApply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED);
		pickedActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityApplyDao.update(pickedActivityApply);

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID);
		pickedActivityDao.update(pickedActivity);
	}

	public List<PickedActivityApply> findPickedActivityApplyByUser(int uid) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getMessage());
		}

		List<PickedActivityApply> result = pickedActivityApplyDao.findPickedActivityApplyByUser(uid);
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
		pickedActivityApply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED);
		pickedActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityApplyDao.update(pickedActivityApply);

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityApply.getPickedActivityId());
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		if (oldStatus.equals(ApiStatus.APPLY_STATUS_APPROVED)) {
			pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID);
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityDao.update(pickedActivity);
	}

}
