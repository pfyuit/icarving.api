package cn.icarving.api.pinche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickedActivity;
import cn.icarving.api.pinche.dto.PickActivityDto;
import cn.icarving.api.pinche.dto.PickActivityDtoBuilder;
import cn.icarving.api.pinche.dto.PickedActivityDto;
import cn.icarving.api.pinche.dto.PickedActivityDtoBuilder;
import cn.icarving.api.pinche.dto.SearchPickActivityForm;
import cn.icarving.api.pinche.dto.SearchPickedActivityForm;
import cn.icarving.api.pinche.service.SearchService;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/pickActivity", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse searchPickActivity(@RequestBody SearchPickActivityForm form) {
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
		List<PickedActivity> list = searchService.searchPickedActivity(form);
		List<PickedActivityDto> dtos = Lists.newArrayList();
		for (PickedActivity pick : list) {
			dtos.add(PickedActivityDtoBuilder.build(pick));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

}
