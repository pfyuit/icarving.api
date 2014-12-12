package cn.icarving.api.pinche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.PickedActivityApplyDao;
import cn.icarving.api.pinche.domain.PickedActivityApply;

@Service
@Transactional
public class PickedActivityApplyService {

	@Autowired
	private PickedActivityApplyDao pickedActivityApplyDao;

	public void save(PickedActivityApply pickedActivityApply) {
		pickedActivityApplyDao.save(pickedActivityApply);
	}

	public void delete(PickedActivityApply pickedActivityApply) {
		pickedActivityApplyDao.delete(pickedActivityApply);
	}

	public void update(PickedActivityApply pickedActivityApply) {
		pickedActivityApplyDao.update(pickedActivityApply);
	}

	public List<PickedActivityApply> findAll() {
		return pickedActivityApplyDao.findAll();
	}

}
