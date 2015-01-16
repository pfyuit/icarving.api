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
		Apply apply = applyService.approveApply(applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ApplyDtoBuilder.buildApply(apply));
	}

	@RequestMapping(value = "/unapprove", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse unApproveApply(@RequestParam(value = "applyId", required = true) int applyId) {
		Apply apply = applyService.unApproveApply(applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ApplyDtoBuilder.buildApply(apply));
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse cancelApply(@RequestParam(value = "uid", required = true) int uid, @RequestParam(value = "applyId", required = true) int applyId) {
		Apply apply = applyService.cancelApply(uid, applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ApplyDtoBuilder.buildApply(apply));
	}

	@RequestMapping(value = "/renew", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse renewApply(@RequestParam(value = "applyId", required = true) int applyId) {
		Apply apply = applyService.renewApply(applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ApplyDtoBuilder.buildApply(apply));
	}

	@RequestMapping(value = "/findByApplyId", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findApplyByApplyId(@RequestParam(value = "applyId", required = true) int applyId) {
		Apply apply = applyService.findApplyByApplyId(applyId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), ApplyDtoBuilder.buildApply(apply));
	}

	@RequestMapping(value = "/findByActivityId", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findApplyByActivityId(@RequestParam(value = "activityId", required = true) int activityId) {
		List<ApplyDto> dtos = Lists.newArrayList();
		List<Apply> list = applyService.findApplyByActivityId(activityId);
		for (Apply apply : list) {
			dtos.add(ApplyDtoBuilder.buildApply(apply));
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), dtos);
	}

}
