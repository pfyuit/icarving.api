package cn.icarving.api.pinche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.domain.Activity;
import cn.icarving.api.pinche.domain.Apply;
import cn.icarving.api.pinche.dto.ActivityCreateForm;
import cn.icarving.api.pinche.dto.ActivityDto;
import cn.icarving.api.pinche.dto.ActivityDtoBuilder;
import cn.icarving.api.pinche.dto.ActivityUpdateForm;
import cn.icarving.api.pinche.service.ActivityService;
import cn.icarving.api.pinche.service.ApplyService;
import cn.icarving.api.pinche.service.UserService;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ApplyService applyService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createActivity(@RequestBody ActivityCreateForm form) {
		Activity activity = activityService.createActivity(form);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ActivityDtoBuilder.buildActivity(activity));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updateActivity(@RequestBody ActivityUpdateForm form) {
		Activity activity = activityService.updateActivity(form);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ActivityDtoBuilder.buildActivity(activity));
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelActivity(@RequestParam(value = "uid", required = true) int uid, @RequestParam(value = "activityId", required = true) int activityId) {
		Activity activity = activityService.cancelActivity(uid, activityId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ActivityDtoBuilder.buildActivity(activity));
	}

	@RequestMapping(value = "/findByActivityId", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findActivityByActivityId(@RequestParam(value = "activityId", required = true) int activityId) {
		Activity activity = activityService.findActivityByActivityId(activityId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ActivityDtoBuilder.buildActivity(activity));
	}

	@RequestMapping(value = "/findAllMy", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findAllActivityByMy(@RequestParam(value = "uid", required = true) int uid) {
		List<ActivityDto> dtos = Lists.newArrayList();
		List<Activity> list = activityService.findActivityAll();
		for (Activity activity : list) {
			List<Apply> applies = applyService.findApplyByActivityId(activity.getActivityId());
			boolean hasApplied = false;
			for (Apply apply : applies) {
				if (apply.getOwnerId() == uid) {
					hasApplied = true;
					break;
				}
			}
			if (activity.getOwnerId() == uid || hasApplied == true) {
				dtos.add(ActivityDtoBuilder.buildActivity(activity));
			}
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

	@RequestMapping(value = "/findAllValid", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findAllActivityByValid() {
		List<ActivityDto> dtos = Lists.newArrayList();
		List<Activity> list = activityService.findActivityAll();
		for (Activity activity : list) {
			dtos.add(ActivityDtoBuilder.buildActivity(activity));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

}
