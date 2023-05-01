package com.hobbyvillage.backend.admin_users;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminUsersServiceImpl implements AdminUsersService {

	private AdminUsersMapper mapper;

	public AdminUsersServiceImpl(AdminUsersMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public int getUserCount() {
		return mapper.getUserCount();
	}
	
	@Override
	public int getSearchUserCount(String condition, String keyword) {
		return mapper.getSearchUserCount(condition, keyword);
	}
	
	@Override
	public List<AdminUsersDTO> getUserList(String sort, int pageNum) {
		return mapper.getUserList(sort, pageNum);
	}
	
	@Override
	public List<AdminUsersDTO> getSearchUserList(String condition, String keyword, String sort, int pageNum) {
		return mapper.getSearchUserList(condition, keyword, sort, pageNum);
	}
	
	@Override
	public void deleteUser(int userCode) {
		mapper.deleteUser(userCode);
	}

}
