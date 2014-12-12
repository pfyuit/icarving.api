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
import cn.icarving.api.pinche.dao.PickActivityApplyDao;
import cn.icarving.api.pinche.dao.PickActivityDao;
import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickActivityApply;
import cn.icarving.api.pinche.domain.User;

@Service
@Transactional
public class PickActivityService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PickActivityDao pickActivityDao;

	@Autowired
	private PickActivityApplyDao pickActivityApplyDao;

	public void createPickActivity(PickActivity pickActivity) {
		pickActivityDao.save(pickActivity);
	}

	public void updatePickActivity(PickActivity pickActivity) {
		User user = userDao.find(pickActivity.getOwnerId());
		if (user == null) {
			throw new ApiException(ApiEnum.ACTIVITY_UPDATE_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.ACTIVITY_UPDATE_FAILED_CANNOT_FIND_USER.getMessage());
		}
		pickActivityDao.update(pickActivity);
	}

	public List<PickActivity> findPickActivityByUser(long uid) {
		List<PickActivity> result = pickActivityDao.findPickActivityByUser(uid);
		return result;
	}

	public void cancelPickActivity(long uid, long pickActivityId) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER.getMessage());
		}

		PickActivity pickActivity = pickActivityDao.find(pickActivityId);
		if (pickActivity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		pickActivity.setStatus(ApiStatus.ACTIVITY_STATUS_CANCELLED);
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityDao.update(pickActivity);

		List<PickActivityApply> pickActivityApplies = pickActivityApplyDao.findPickActivityApplyByPickActivity(pickActivityId);
		for (PickActivityApply pickActivityApply : pickActivityApplies) {
			pickActivityApply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED);
			pickActivityApplyDao.update(pickActivityApply);
		}
	}

}
