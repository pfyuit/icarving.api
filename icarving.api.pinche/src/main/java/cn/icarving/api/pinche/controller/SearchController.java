package cn.icarving.api.pinche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.common.ApiStatus;
import cn.icarving.api.pinche.domain.Activity;
import cn.icarving.api.pinche.dto.ActivityDto;
import cn.icarving.api.pinche.dto.ActivityDtoBuilder;
import cn.icarving.api.pinche.dto.SearchForm;
import cn.icarving.api.pinche.service.ApplyService;
import cn.icarving.api.pinche.service.SearchService;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private ApplyService applyService;

	@RequestMapping(value = "/activity", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse searchActivity(@RequestBody SearchForm form) {
		List<ActivityDto> dtos = Lists.newArrayList();
		if (Strings.isNullOrEmpty(form.getSourceAddress())) {
			throw new ApiException(ApiEnum.SEARCH_ADDRESS_CANNOT_EMPTY.getCode(), ApiEnum.SEARCH_ADDRESS_CANNOT_EMPTY.getMessage());
		}
		List<Activity> list = searchService.searchActivity(new SearchForm(form.getStartTime(), form.getReturnTime(), form.getSourceAddress(), form.getDestAddress()));
		for (Activity activity : list) {
			if (!activity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
				continue;
			}
			dtos.add(ActivityDtoBuilder.buildActivity(activity));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

}
