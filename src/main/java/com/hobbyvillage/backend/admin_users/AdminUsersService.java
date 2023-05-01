package com.hobbyvillage.backend.admin_users;

import java.util.List;

public interface AdminUsersService {
	int getUserCount();
	int getSearchUserCount(String condition, String keyword);
	List<AdminUsersDTO> getUserList(String sort, int pageNum);
	List<AdminUsersDTO> getSearchUserList(String condition, String keyword, String sort, int pageNum);
	void deleteUser(int userCode);
}
