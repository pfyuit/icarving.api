package cn.icarving.api.pinche.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.domain.PickActivityApply;
import cn.icarving.api.pinche.domain.PickedActivityApply;
import cn.icarving.api.pinche.dto.PickActivityApplyForm;
import cn.icarving.api.pinche.dto.PickedActivityApplyForm;
import cn.icarving.api.pinche.service.PickActivityApplyService;
import cn.icarving.api.pinche.service.PickedActivityApplyService;

@Controller
@RequestMapping("/apply")
public class ApplyController {

	@Autowired
	private PickActivityApplyService pickActivityApplyService;

	@Autowired
	private PickedActivityApplyService pickedActivityApplyService;

	@RequestMapping(value = "/pick/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createPickActivityApply(@RequestBody PickActivityApplyForm form) {
		PickActivityApply apply = new PickActivityApply();
		apply.setApplyUserId(form.getApplyUserId());
		apply.setPickActivityId(form.getPickActivityId());
		apply.setStatus("start");
		apply.setApplyTime(new Timestamp(new Date().getTime()));
		apply.setLastModify(new Timestamp(new Date().getTime()));

		pickActivityApplyService.save(apply);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), apply);
	}

	@RequestMapping(value = "/pick/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updatePickActivityApply(@RequestBody PickActivityApply apply) {
		pickActivityApplyService.save(apply);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), apply);
	}

	@RequestMapping(value = "/pick/find", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickActivityApply() {
		return null;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/picked/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createPickedActivityApply(@RequestBody PickedActivityApplyForm form) {
		PickedActivityApply apply = new PickedActivityApply();
		apply.setApplyUserId(form.getApplyUserId());
		apply.setPickedActivityId(form.getPickedActivityId());
		apply.setStatus("start");
		apply.setApplyTime(new Timestamp(new Date().getTime()));
		apply.setLastModify(new Timestamp(new Date().getTime()));

		pickedActivityApplyService.save(apply);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), apply);
	}

	@RequestMapping(value = "/picked/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse updatePickedActivityApply(@RequestBody PickedActivityApply apply) {
		pickedActivityApplyService.save(apply);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), apply);
	}

	@RequestMapping(value = "/picked/find", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickedActivityApply() {
		return null;
	}
}
