package com.hobbyvillage.backend.admin_login;

import org.springframework.stereotype.Service;


@Service
public class AdminLoginServiceImpl {
	
	private AdminLoginMapper mapper;
	
	public AdminLoginServiceImpl(AdminLoginMapper mapper) {
		this.mapper = mapper;
	}

	public int login(AdminLoginDTO admins) {
		return mapper.login(admins);
	}

}
