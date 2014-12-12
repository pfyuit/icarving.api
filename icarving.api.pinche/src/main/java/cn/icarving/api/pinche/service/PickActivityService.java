package cn.icarving.api.pinche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.PickActivityDao;
import cn.icarving.api.pinche.domain.PickActivity;

@Service
@Transactional
public class PickActivityService {

	@Autowired
	private PickActivityDao pickActivityDao;

	public void save(PickActivity pickActivity) {
		pickActivityDao.save(pickActivity);
	}

	public void delete(PickActivity pickActivity) {
		pickActivityDao.delete(pickActivity);
	}

	public void update(PickActivity pickActivity) {
		pickActivityDao.update(pickActivity);
	}

	public List<PickActivity> findAll() {
		return pickActivityDao.findAll();
	}

	public List<PickActivity> findPickActivityByUser(long uid) {
		List<PickActivity> result = pickActivityDao.findPickActivityByUser(uid);
		return result;
	}

}
