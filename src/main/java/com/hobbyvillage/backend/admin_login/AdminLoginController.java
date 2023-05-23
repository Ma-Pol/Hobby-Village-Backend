package com.hobbyvillage.backend.admin_login;

import org.springframework.web.bind.annotation.*;

@RestController
public class AdminLoginController {

	private AdminLoginServiceImpl adminLoginServiceImpl;

	public AdminLoginController(AdminLoginServiceImpl adminLoginServiceImpl) {
		this.adminLoginServiceImpl = adminLoginServiceImpl;
	}

	@PostMapping("/m/loginCheck")
	public String login(@RequestBody AdminLoginDTO admin) {
		return adminLoginServiceImpl.adminLoginCheck(admin);
	}
}
