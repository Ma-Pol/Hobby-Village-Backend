package com.hobbyvillage.backend.admin_login;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminLoginController {

	private AdminLoginServiceImpl mService;

	public AdminLoginController(AdminLoginServiceImpl mService) {
		this.mService = mService;
	}
	
	@RequestMapping("/m/login")
	public int login(@RequestBody AdminLoginDTO admins) throws Exception {
		int res = mService.login(admins);

		return res;
	}
}
