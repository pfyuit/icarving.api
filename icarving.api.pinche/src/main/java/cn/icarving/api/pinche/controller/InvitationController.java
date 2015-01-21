package cn.icarving.api.pinche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.domain.Invitation;
import cn.icarving.api.pinche.service.InvitationService;

@Controller
@RequestMapping("/invitation")
public class InvitationController {

	@Autowired
	private InvitationService invitationService;

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse userRegister(@RequestParam(value="code", required = true) final int invitationCode) {
		Invitation invitation = invitationService.verifyInvitationCode(invitationCode);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), invitation);
	}

}
