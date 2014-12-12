package cn.icarving.api.pinche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.PickedActivityDao;
import cn.icarving.api.pinche.domain.PickedActivity;

@Service
@Transactional
public class PickedActivityService {

	@Autowired
	private PickedActivityDao pickedActivityDao;

	public void save(PickedActivity pickedActivity) {
		pickedActivityDao.save(pickedActivity);
	}

	public void update(PickedActivity pickedActivity) {
		pickedActivityDao.update(pickedActivity);
	}

	public List<PickedActivity> findPickedActivityByUser(long uid) {
		List<PickedActivity> result = pickedActivityDao.findPickedActivityByUser(uid);
		return result;
	}

}
