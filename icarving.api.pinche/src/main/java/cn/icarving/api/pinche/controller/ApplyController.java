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
import cn.icarving.api.pinche.domain.PickActivityApply;
import cn.icarving.api.pinche.domain.PickedActivityApply;
import cn.icarving.api.pinche.dto.PickActivityApplyDto;
import cn.icarving.api.pinche.dto.PickActivityApplyDtoBuilder;
import cn.icarving.api.pinche.dto.PickActivityApplyForm;
import cn.icarving.api.pinche.dto.PickedActivityApplyDto;
import cn.icarving.api.pinche.dto.PickedActivityApplyDtoBuilder;
import cn.icarving.api.pinche.dto.PickedActivityApplyForm;
import cn.icarving.api.pinche.service.PickActivityApplyService;
import cn.icarving.api.pinche.service.PickedActivityApplyService;

import com.google.common.collect.Lists;

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
		apply.setPickActivityId(form.getPickActivityId());
		apply.setApplyUserId(form.getApplyUserId());
		apply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		apply.setApplyTime(new Timestamp(new Date().getTime()));
		apply.setLastModify(new Timestamp(new Date().getTime()));
		pickActivityApplyService.createPickActivityApply(apply);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), PickActivityApplyDtoBuilder.build(apply));
	}

	@RequestMapping(value = "/pick/approve", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse approvePickActivityApply(@RequestParam(value = "pickActivityApplyId", required = true) int pickActivityApplyId) {
		pickActivityApplyService.approvePickActivityApply(pickActivityApplyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/pick/unapprove", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse unApprovePickActivityApply(@RequestParam(value = "pickActivityApplyId", required = true) int pickActivityApplyId) {
		pickActivityApplyService.unApprovePickActivityApply(pickActivityApplyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/pick/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickActivityApplyByUser(@RequestParam(value = "uid", required = true) int uid) {
		List<PickActivityApply> list = pickActivityApplyService.findPickActivityApplyByUser(uid);
		List<PickActivityApplyDto> dtos = Lists.newArrayList();
		for(PickActivityApply pickActivityApply : list){
			dtos.add(PickActivityApplyDtoBuilder.build(pickActivityApply));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}
	
	@RequestMapping(value = "/pick/findByPickActivity", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickActivityApplyByPickActivity(@RequestParam(value = "pickActivityId", required = true) int pickActivityId) {
		List<PickActivityApply> list = pickActivityApplyService.findPickActivityApplyByPickActivity(pickActivityId);
		List<PickActivityApplyDto> dtos = Lists.newArrayList();
		for (PickActivityApply pickActivityApply : list) {
			dtos.add(PickActivityApplyDtoBuilder.build(pickActivityApply));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

	@RequestMapping(value = "/pick/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelPickActivityApply(@RequestParam(value = "uid", required = true) int uid,
			@RequestParam(value = "pickActivityApplyId", required = true) int pickActivityApplyId) {
		pickActivityApplyService.cancelPickActivityApply(uid, pickActivityApplyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/picked/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createPickedActivityApply(@RequestBody PickedActivityApplyForm form) {
		PickedActivityApply apply = new PickedActivityApply();
		apply.setPickedActivityId(form.getPickedActivityId());
		apply.setApplyUserId(form.getApplyUserId());
		apply.setStatus(ApiStatus.APPLY_STATUS_UNAPPROVED.getStatus());
		apply.setApplyTime(new Timestamp(new Date().getTime()));
		apply.setLastModify(new Timestamp(new Date().getTime()));
		pickedActivityApplyService.createPickedActivityApply(apply);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), PickedActivityApplyDtoBuilder.build(apply));
	}

	@RequestMapping(value = "/picked/approve", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse approvePickedActivityApply(@RequestParam(value = "pickedActivityApplyId", required = true) int pickedActivityApplyId) {
		pickedActivityApplyService.approvePickedActivityApply(pickedActivityApplyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/picked/unapprove", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse unApprovePickedActivityApply(@RequestParam(value = "pickedActivityApplyId", required = true) int pickedActivityApplyId) {
		pickedActivityApplyService.unApprovePickedActivityApply(pickedActivityApplyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/picked/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickedActivityApplyByUser(@RequestParam(value = "uid", required = true) int uid) {
		List<PickedActivityApply> list = pickedActivityApplyService.findPickedActivityApplyByUser(uid);
		List<PickedActivityApplyDto> dtos = Lists.newArrayList();
		for(PickedActivityApply pickedActivityApply : list){
			dtos.add(PickedActivityApplyDtoBuilder.build(pickedActivityApply));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}
	
	@RequestMapping(value = "/picked/findByPickedActivity", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findPickedActivityApplyByPickedActivity(@RequestParam(value = "pickedActivityId", required = true) int pickedActivityId) {
		List<PickedActivityApply> list = pickedActivityApplyService.findPickedActivityApplyByPickedActivity(pickedActivityId);
		List<PickedActivityApplyDto> dtos = Lists.newArrayList();
		for (PickedActivityApply pickedActivityApply : list) {
			dtos.add(PickedActivityApplyDtoBuilder.build(pickedActivityApply));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}
	
	@RequestMapping(value = "/picked/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelPickedActivityApply(@RequestParam(value = "uid", required = true) int uid,
			@RequestParam(value = "pickedActivityApplyId", required = true) int pickedActivityApplyId) {
		pickedActivityApplyService.cancelPickedActivityApply(uid, pickedActivityApplyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}
}
