package cn.icarving.api.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.wechat.message.basic.GetAccessTokenResponse;
import cn.icarving.api.wechat.message.user.GetServerIPResponse;
import cn.icarving.api.wechat.service.BasicService;

@Controller
@RequestMapping("/basic")
public class BasicController {

	@Autowired
	private BasicService basicService;

	@RequestMapping("/accessToken")
	public @ResponseBody
	GetAccessTokenResponse getAccessToken(@RequestParam(value = "grant_type ", required = true) String grantType, @RequestParam(value = "appid", required = true) String appid,
			@RequestParam(value = "secret", required = true) String secret) {
		return basicService.getAccessToken(grantType, appid, secret);
	}
	
	@RequestMapping("/serverIP")
	public @ResponseBody
	GetServerIPResponse getServerIP() {
		return basicService.getServerIP();
	}

}
