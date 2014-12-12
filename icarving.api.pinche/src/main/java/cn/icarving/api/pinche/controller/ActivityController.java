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
import cn.icarving.api.pinche.domain.PickActivity;
import cn.icarving.api.pinche.domain.PickedActivity;
import cn.icarving.api.pinche.dto.PickActivityForm;
import cn.icarving.api.pinche.dto.PickedActivityForm;
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
	ApiResponse createPickActivity(@RequestBody PickActivityForm form) {
		PickActivity pickActivity = new PickActivity();
		pickActivity.setOwnerId(form.getOwnerId());
		pickActivity.setStartTime(form.getStartTime());
		pickActivity.setReturnTime(form.getReturnTime());
		pickActivity.setSourceAddress(form.getSourceAddress());
		pickActivity.setDestAddress(form.getDestAddress());
		pickActivity.setCharge(form.getCharge());
		pickActivity.setCarType(form.getCarType());
		pickActivity.setCapacity(form.getCapacity());
		pickActivity.setApplyNumber(0);
		pickActivity.setApproveNumber(0);
		pickActivity.setNote(form.getNote());
		pickActivity.setStatus("start");
		pickActivity.setPublishTime(new Timestamp(new Date().getTime()));
		pickActivity.setLastModify(new Timestamp(new Date().getTime()));

		pickActivityService.save(pickActivity);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), pickActivity);
	}

	@RequestMapping(value = "/pick/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updatePickActivity(@RequestBody PickActivity pickActivity) {
		pickActivityService.update(pickActivity);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), pickActivity);
	}

	@RequestMapping(value = "/pick/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickActivityByUser(@RequestParam(value="uid", required=true) long uid) {
		List<PickActivity> list = pickActivityService.findPickActivityByUser(uid);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), list);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/picked/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createPickedActivity(@RequestBody PickedActivityForm form) {
		PickedActivity pickedActivity = new PickedActivity();
		pickedActivity.setOwnerId(form.getOwnerId());
		pickedActivity.setStartTime(form.getStartTime());
		pickedActivity.setReturnTime(form.getReturnTime());
		pickedActivity.setSourceAddress(form.getSourceAddress());
		pickedActivity.setDestAddress(form.getDestAddress());
		pickedActivity.setCharge(form.getCharge());
		pickedActivity.setCarType(form.getCarType());
		pickedActivity.setNote(form.getNote());
		pickedActivity.setStatus("start");
		pickedActivity.setPublishTime(new Timestamp(new Date().getTime()));
		pickedActivity.setLastModify(new Timestamp(new Date().getTime()));

		pickedActivityService.save(pickedActivity);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), pickedActivity);
	}

	@RequestMapping(value = "/picked/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updatePickedActivity(@RequestBody PickedActivity pickedActivity) {
		pickedActivityService.update(pickedActivity);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), pickedActivity);
	}

	@RequestMapping(value = "/picked/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickedActivityByUser(@RequestParam(value="uid", required=true) long uid) {
		List<PickedActivity> list = pickedActivityService.findPickedActivityByUser(uid);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), list);
	}

}
