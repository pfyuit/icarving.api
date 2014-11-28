package cn.icarving.api.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@RequestMapping("/callback")
	public String authCallback() {
		return null;
	}

}
