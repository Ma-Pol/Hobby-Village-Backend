package com.hobbyvillage.backend.admin_login;

import org.springframework.stereotype.Service;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

	private AdminLoginMapper mapper;

	public AdminLoginServiceImpl(AdminLoginMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public String adminLoginCheck(AdminLoginDTO admin) {
		String result = "disabled";
		int check = mapper.loginCheck(admin);

		if (check == 1) {
			result = mapper.getNickname(admin);
		}

		return result;
	}

}
