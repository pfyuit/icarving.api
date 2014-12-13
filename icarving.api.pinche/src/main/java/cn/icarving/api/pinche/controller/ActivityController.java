package cn.icarving.api.pinche.controller;

import java.sql.Timestamp;
import java.util.Date;
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
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickedActivity;
import cn.icarving.api.pinche.dto.PickActivityCreateForm;
import cn.icarving.api.pinche.dto.PickActivityUpdateForm;
import cn.icarving.api.pinche.dto.PickedActivityCreateForm;
import cn.icarving.api.pinche.dto.PickedActivityUpdateForm;
import cn.icarving.api.pinche.service.PickActivityService;
import cn.icarving.api.pinche.service.PickedActivityService;

@Controller
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private PickActivityService pickActivityService;

	@Autowired
	private PickedActivityService pickedActivityService;

	@RequestMapping(value = "/pick/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createPickActivity(@RequestBody PickActivityCreateForm form) {
		PickActivity pickActivity = new PickActivity();
		pickActivity.setOwnerId(form.getOwnerId());
		pickActivity.setStartTime(Timestamp.valueOf(form.getStartTime()));
		pickActivity.setReturnTime(Timestamp.valueOf(form.getReturnTime()));
		pickActivity.setSourceAddress(form.getSourceAddress());
		pickActivity.setDestAddress(form.getDestAddress());
		pickActivity.setCharge(form.getCharge());
		pickActivity.setCarType(form.getCarType());
		pickActivity.setCapacity(form.getCapacity());
		pickActivity.setApplyNumber(0);
		pickActivity.setApproveNumber(0);
		pickActivity.setNote(form.getNote());
		pickActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID);
		pickActivity.setPublishTime(new Timestamp(new Date().getTime()));
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));

		pickActivityService.createPickActivity(pickActivity);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), pickActivity);
	}

	@RequestMapping(value = "/pick/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updatePickActivity(@RequestBody PickActivityUpdateForm form) {
		PickActivity updatedPickActivity = pickActivityService.updatePickActivity(form);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), updatedPickActivity);
	}

	@RequestMapping(value = "/pick/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickActivityByUser(@RequestParam(value = "uid", required = true) int uid) {
		List<PickActivity> list = pickActivityService.findPickActivityByUser(uid);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), list);
	}

	@RequestMapping(value = "/pick/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelPickActivity(@RequestParam(value = "uid", required = true) int uid, @RequestParam(value = "pickActivityId", required = true) int pickActivityId) {
		pickActivityService.cancelPickActivity(uid, pickActivityId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/picked/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createPickedActivity(@RequestBody PickedActivityCreateForm form) {
		PickedActivity pickedActivity = new PickedActivity();
		pickedActivity.setOwnerId(form.getOwnerId());
		pickedActivity.setStartTime(Timestamp.valueOf(form.getStartTime()));
		pickedActivity.setReturnTime(Timestamp.valueOf(form.getReturnTime()));
		pickedActivity.setSourceAddress(form.getSourceAddress());
		pickedActivity.setDestAddress(form.getDestAddress());
		pickedActivity.setCharge(form.getCharge());
		pickedActivity.setCarType(form.getCarType());
		pickedActivity.setNote(form.getNote());
		pickedActivity.setStatus(ApiStatus.ACTIVITY_STATUS_VALID);
		pickedActivity.setPublishTime(new Timestamp(new Date().getTime()));
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));

		pickedActivityService.createPickedActivity(pickedActivity);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), pickedActivity);
	}

	@RequestMapping(value = "/picked/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updatePickedActivity(@RequestBody PickedActivityUpdateForm form) {
		PickedActivity updatedPickedActivity = pickedActivityService.updatePickedActivity(form);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), updatedPickedActivity);
	}

	@RequestMapping(value = "/picked/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickedActivityByUser(@RequestParam(value = "uid", required = true) int uid) {
		List<PickedActivity> list = pickedActivityService.findPickedActivityByUser(uid);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), list);
	}

	@RequestMapping(value = "/picked/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelPickedActivity(@RequestParam(value = "uid", required = true) int uid, @RequestParam(value = "pickedActivityId", required = true) int pickedActivityId) {
		pickedActivityService.cancelPickedActivity(uid, pickedActivityId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

}
