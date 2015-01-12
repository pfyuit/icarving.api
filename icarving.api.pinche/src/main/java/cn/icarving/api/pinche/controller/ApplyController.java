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
import cn.icarving.api.pinche.domain.Apply;
import cn.icarving.api.pinche.dto.ApplyCreateForm;
import cn.icarving.api.pinche.dto.ApplyDto;
import cn.icarving.api.pinche.dto.ApplyDtoBuilder;
import cn.icarving.api.pinche.service.ApplyService;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/apply")
public class ApplyController {
	
	@Autowired
	private ApplyService applyService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse createApply(@RequestBody ApplyCreateForm form) {
		Apply apply = applyService.createApply(form);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ApplyDtoBuilder.buildApply(apply));
	}

	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse approveApply(@RequestParam(value = "applyId", required = true) int applyId) {
		applyService.approveApply(applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/unapprove", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse unApproveApply(@RequestParam(value = "applyId", required = true) int applyId) {
		applyService.unApproveApply(applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelApply(@RequestParam(value = "uid", required = true) int uid, @RequestParam(value = "applyId", required = true) int applyId) {
		applyService.cancelApply(uid, applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/findByUser", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findApplyByUser(@RequestParam(value = "uid", required = true) int uid) {
		List<Apply> list = applyService.findApplyByUser(uid);
		List<ApplyDto> dtos = Lists.newArrayList();
		for (Apply apply : list) {
			dtos.add(ApplyDtoBuilder.buildApply(apply));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

	@RequestMapping(value = "/findByActivity", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findApplyByActivity(@RequestParam(value = "activityId", required = true) int activityId) {
		List<ApplyDto> dtos = Lists.newArrayList();
		List<Apply> list = applyService.findApplyByActivity(activityId);
		for (Apply apply : list) {
			dtos.add(ApplyDtoBuilder.buildApply(apply));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

}
