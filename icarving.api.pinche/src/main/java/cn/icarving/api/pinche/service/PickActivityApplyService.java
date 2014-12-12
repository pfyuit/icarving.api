package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
import cn.icarving.api.pinche.dao.PickActivityApplyDao;
import cn.icarving.api.pinche.dao.PickActivityDao;
import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickActivityApply;
import cn.icarving.api.pinche.domain.User;

@Service
@Transactional
public class PickActivityApplyService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PickActivityDao pickActivityDao;

	@Autowired
	private PickActivityApplyDao pickActivityApplyDao;

	public void createPickActivityApply(PickActivityApply pickActivityApply) {
		PickActivity pickActivity = pickActivityDao.find(pickActivityApply.getPickActivityId());
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		int applyNumber = pickActivity.getApplyNumber();
		applyNumber = applyNumber + 1;
		pickActivity.setApplyNumber(applyNumber);
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityDao.save(pickActivity);

		pickActivityApplyDao.save(pickActivityApply);
	}

	public void approvePickActivityApply(long pickActivityApplyId) {
		PickActivityApply pickActivityApply = pickActivityApplyDao.find(pickActivityApplyId);
		if (pickActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickActivityApply.setStatus("approved");
		pickActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityApplyDao.save(pickActivityApply);

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
		pickActivityDao.save(pickActivity);
	}

	public void unApprovePickActivityApply(long pickActivityApplyId) {
		PickActivityApply pickActivityApply = pickActivityApplyDao.find(pickActivityApplyId);
		if (pickActivityApply == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getCode(),
					ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY.getMessage());
		}
		pickActivityApply.setStatus("unapproved");
		pickActivityApply.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityApplyDao.save(pickActivityApply);

		PickActivity pickActivity = pickActivityDao.find(pickActivityApply.getPickActivityId());
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		int approveNumber = pickActivity.getApproveNumber();
		approveNumber = approveNumber - 1;
		int applyNumber = pickActivity.getApplyNumber();
		applyNumber = applyNumber + 1;
		pickActivity.setApplyNumber(applyNumber);
		pickActivity.setApproveNumber(approveNumber);
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityDao.save(pickActivity);
	}

	public List<PickActivityApply> findPickActivityApplyByUser(long uid) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.APPLY_FIND_FAILED_CANNOT_FIND_USER.getMessage());
		}

		List<PickActivityApply> result = pickActivityApplyDao.findPickActivityApplyByUser(uid);
		return result;
	}

}
