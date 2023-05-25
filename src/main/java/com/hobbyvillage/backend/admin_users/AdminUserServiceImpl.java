package com.hobbyvillage.backend.admin_users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("Service")
public class AdminUserServiceImpl {

	private AdminUserMapper mapper;
	
	public AdminUserServiceImpl(AdminUserMapper mapper) {
		this.mapper = mapper;
	}
	
	public AdminUserDTO getTotalUsers(int userCode) {
	return mapper.getTotalUsers(userCode);
	}
	
	

}
