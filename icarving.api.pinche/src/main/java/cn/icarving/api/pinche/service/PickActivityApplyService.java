package cn.icarving.api.pinche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.PickActivityApplyDao;
import cn.icarving.api.pinche.domain.PickActivityApply;

@Service
@Transactional
public class PickActivityApplyService {

	@Autowired
	private PickActivityApplyDao pickActivityApplyDao;

	public void save(PickActivityApply pickActivityApply) {
		pickActivityApplyDao.save(pickActivityApply);
	}

	public void delete(PickActivityApply pickActivityApply) {
		pickActivityApplyDao.delete(pickActivityApply);
	}

	public void update(PickActivityApply pickActivityApply) {
		pickActivityApplyDao.update(pickActivityApply);
	}

	public List<PickActivityApply> findAll() {
		return pickActivityApplyDao.findAll();
	}

}
