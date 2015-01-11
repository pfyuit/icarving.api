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
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickedActivity;
import cn.icarving.api.pinche.dto.ActivityDto;
import cn.icarving.api.pinche.dto.ActivityDtoBuilder;
import cn.icarving.api.pinche.dto.PickActivityDto;
import cn.icarving.api.pinche.dto.PickActivityDtoBuilder;
import cn.icarving.api.pinche.dto.PickedActivityDto;
import cn.icarving.api.pinche.dto.PickedActivityDtoBuilder;
import cn.icarving.api.pinche.dto.SearchPickActivityForm;
import cn.icarving.api.pinche.dto.SearchPickedActivityForm;
import cn.icarving.api.pinche.service.SearchService;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/pickActivity", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse searchPickActivity(@RequestBody SearchPickActivityForm form) {
		if (Strings.isNullOrEmpty(form.getSourceAddress())) {
			throw new ApiException(ApiEnum.SEARCH_FAILED_SOURCE_ADDRESS_CANNOT_EMPTY.getCode(), ApiEnum.SEARCH_FAILED_SOURCE_ADDRESS_CANNOT_EMPTY.getMessage());
		}
		List<PickActivity> list = searchService.searchPickActivity(form);
		List<PickActivityDto> dtos = Lists.newArrayList();
		for (PickActivity pick : list) {
			dtos.add(PickActivityDtoBuilder.build(pick));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

	@RequestMapping(value = "/pickedActivity", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse searchPickedActivity(@RequestBody SearchPickedActivityForm form) {
		if (Strings.isNullOrEmpty(form.getSourceAddress())) {
			throw new ApiException(ApiEnum.SEARCH_FAILED_SOURCE_ADDRESS_CANNOT_EMPTY.getCode(), ApiEnum.SEARCH_FAILED_SOURCE_ADDRESS_CANNOT_EMPTY.getMessage());
		}
		List<PickedActivity> list = searchService.searchPickedActivity(form);
		List<PickedActivityDto> dtos = Lists.newArrayList();
		for (PickedActivity pick : list) {
			dtos.add(PickedActivityDtoBuilder.build(pick));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

	@RequestMapping(value = "/activity", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse searchActivity(@RequestBody SearchPickedActivityForm form) {
		List<ActivityDto> dtos = Lists.newArrayList();
		if (Strings.isNullOrEmpty(form.getSourceAddress())) {
			throw new ApiException(ApiEnum.SEARCH_FAILED_SOURCE_ADDRESS_CANNOT_EMPTY.getCode(), ApiEnum.SEARCH_FAILED_SOURCE_ADDRESS_CANNOT_EMPTY.getMessage());
		}
		List<PickActivity> list = searchService.searchPickActivity(new SearchPickActivityForm(form.getStartTime(), form.getReturnTime(), form.getSourceAddress(), form
				.getDestAddress()));
		List<PickedActivity> list1 = searchService.searchPickedActivity(form);
		for (PickActivity pick : list) {
			if (!pick.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
				continue;
			}
			dtos.add(ActivityDtoBuilder.buildPickActivity(pick));
		}
		for (PickedActivity picked : list1) {
			if (!picked.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
				continue;
			}
			dtos.add(ActivityDtoBuilder.buildPickedActivity(picked));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

}
