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
import cn.icarving.api.pinche.common.ApiStatus;
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
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ActivityDtoBuilder.buildActivity(activity, null));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updateActivity(@RequestBody ActivityUpdateForm form) {
		Activity activity = activityService.updateActivity(form);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ActivityDtoBuilder.buildActivity(activity, null));
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelActivity(@RequestParam(value = "uid", required = true) int uid, @RequestParam(value = "activityId", required = true) int activityId) {
		activityService.cancelActivity(uid, activityId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findActivityByUser(@RequestParam(value = "uid", required = true) int uid) {
		List<Activity> list = activityService.findActivityByUser(uid);
		List<ActivityDto> dtos = Lists.newArrayList();
		for (Activity activity : list) {
			List<Apply> applies = applyService.findApplyByActivity(activity.getActivityId());
			dtos.add(ActivityDtoBuilder.buildActivity(activity, applies));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

	@RequestMapping(value = "/findMy", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findActivityMy(@RequestParam(value = "uid", required = true) int uid) {
		List<ActivityDto> dtos = Lists.newArrayList();
		List<Activity> list = activityService.findActivityAll();
		for (Activity activity : list) {
			List<Apply> applies = applyService.findApplyByActivity(activity.getActivityId());
			boolean hasApplied = false;
			for (Apply apply : applies) {
				if (apply.getOwnerId() == uid) {
					hasApplied = true;
					break;
				}
			}
			if (activity.getOwnerId() == uid || hasApplied == true) {
				dtos.add(ActivityDtoBuilder.buildActivity(activity, applies));
			}
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findActivityAll() {
		List<ActivityDto> dtos = Lists.newArrayList();
		List<Activity> list = activityService.findActivityAll();
		for (Activity activity : list) {
			if (!activity.getStatus().equals(ApiStatus.ACTIVITY_STATUS_VALID.getStatus())) {
				continue;
			}
			List<Apply> applies = applyService.findApplyByActivity(activity.getActivityId());
			dtos.add(ActivityDtoBuilder.buildActivity(activity, applies));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

}
