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
import cn.icarving.api.pinche.dto.PickedActivityUpdateForm;

import com.google.common.base.Strings;

@Service
@Transactional(rollbackFor = Exception.class)
public class PickedActivityService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PickedActivityDao pickedActivityDao;

	@Autowired
	private PickedActivityApplyDao pickedActivityApplyDao;

	public void createPickedActivity(PickedActivity pickedActivity) {
		pickedActivityDao.save(pickedActivity);
	}

	public PickedActivity updatePickedActivity(PickedActivityUpdateForm form) {
		int pickedActiviyId = form.getPickedActivityId();
		PickedActivity pickedActivity = pickedActivityDao.find(pickedActiviyId);
		if (!Strings.isNullOrEmpty(form.getCarType())) {
			pickedActivity.setCarType(form.getCarType());
		}
		if (!Strings.isNullOrEmpty(form.getDestAddress())) {
			pickedActivity.setDestAddress(form.getDestAddress());
		}
		if (!Strings.isNullOrEmpty(form.getNote())) {
			pickedActivity.setNote(form.getNote());
		}
		if (!Strings.isNullOrEmpty(form.getReturnTime())) {
			pickedActivity.setReturnTime(Timestamp.valueOf(form.getReturnTime()));
		}
		if (!Strings.isNullOrEmpty(form.getSourceAddress())) {
			pickedActivity.setSourceAddress(form.getSourceAddress());
		}
		if (!Strings.isNullOrEmpty(form.getStartTime())) {
			pickedActivity.setStartTime(Timestamp.valueOf(form.getStartTime()));
		}
		if (form.getCharge().doubleValue() > 0) {
			pickedActivity.setCharge(form.getCharge());
		}
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityDao.update(pickedActivity);
		return pickedActivity;
	}

	public List<PickedActivity> findPickedActivityByUser(int uid) {
		List<PickedActivity> result = pickedActivityDao.findPickedActivityByUser(uid);
		return result;
	}
	
	public List<PickedActivity> findPickedActivityAll() {
		List<PickedActivity> result = pickedActivityDao.findPickedActivityAll();
		return result;
	}

	public void cancelPickedActivity(int uid, int pickedActivityId) {
		User user = userDao.find(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER.getMessage());
		}

		PickedActivity pickedActivity = pickedActivityDao.find(pickedActivityId);
		if (pickedActivity == null) {
			throw new ApiException(ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY.getCode(), ApiEnum.ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY.getMessage());
		}
		pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_CANCELLED.getStatus());
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityDao.update(pickedActivity);

		List<PickedActivityApply> pickedActivityApplies = pickedActivityApplyDao.findPickedActivityApplyByPickedActivity(pickedActivityId);
		for (PickedActivityApply pickedActivityApply : pickedActivityApplies) {
			pickedActivityApply.setStatus(ApiStatus.APPLY_STATUS_CANCELLED.getStatus());
			pickedActivityApplyDao.update(pickedActivityApply);
		}
	}

}
