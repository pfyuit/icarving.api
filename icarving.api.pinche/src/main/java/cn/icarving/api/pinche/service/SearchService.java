package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.SearchDao;
import cn.icarving.api.pinche.domain.Activity;
import cn.icarving.api.pinche.dto.SearchForm;

import com.google.common.base.Strings;

@Service
@Transactional(rollbackFor = Exception.class)
public class SearchService {

	@Autowired
	private SearchDao searchDao;

	public List<Activity> searchActivity(SearchForm form) {
		Timestamp starts = Strings.isNullOrEmpty(form.getStartTime()) ? null : Timestamp.valueOf(form.getStartTime());
		Timestamp returns = Strings.isNullOrEmpty(form.getReturnTime()) ? null : Timestamp.valueOf(form.getReturnTime());
		List<Activity> result = searchDao.searchActivity(starts, returns, form.getSourceAddress(), form.getDestAddress());
		return result;
	}

}
