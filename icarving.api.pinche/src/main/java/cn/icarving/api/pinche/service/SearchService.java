package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.SearchPickActivityDao;
import cn.icarving.api.pinche.dao.SearchPickedActivityDao;
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickedActivity;
import cn.icarving.api.pinche.dto.SearchPickActivityForm;
import cn.icarving.api.pinche.dto.SearchPickedActivityForm;

import com.google.common.base.Strings;

@Service
@Transactional(rollbackFor = Exception.class)
public class SearchService {

	@Autowired
	private SearchPickActivityDao searchPickActivityDao;

	@Autowired
	private SearchPickedActivityDao searchPickedActivityDao;

	public List<PickActivity> searchPickActivity(SearchPickActivityForm form) {
		Timestamp starts = Strings.isNullOrEmpty(form.getStartTime()) ? null : Timestamp.valueOf(form.getStartTime());
		Timestamp returns = Strings.isNullOrEmpty(form.getReturnTime()) ? null : Timestamp.valueOf(form.getReturnTime());
		List<PickActivity> result = searchPickActivityDao.searchPickActivity(starts, returns, form.getSourceAddress(), form.getDestAddress());
		return result;
	}

	public List<PickedActivity> searchPickedActivity(SearchPickedActivityForm form) {
		Timestamp starts = Strings.isNullOrEmpty(form.getStartTime()) ? null : Timestamp.valueOf(form.getStartTime());
		Timestamp returns = Strings.isNullOrEmpty(form.getReturnTime()) ? null : Timestamp.valueOf(form.getReturnTime());
		List<PickedActivity> result = searchPickedActivityDao.searchPickedActivity(starts, returns, form.getSourceAddress(), form.getDestAddress());
		return result;
	}

}
