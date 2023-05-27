package com.hobbyvillage.backend.admin_users;

import java.util.List;

public interface AdminUsersService {
	int getUserCount();
	int getSearchUserCount(String condition, String keyword);
	List<AdminUsersDTO> getUserList(String sort, int pageNum);
	List<AdminUsersDTO> getSearchUserList(String condition, String keyword, String sort, int pageNum);
	int checkUser(int userCode);
	AdminUsersDTO getUserDetail(int userCode);
	int checkRequest(String email);
	int checkOrderProduct(String email);
	int deleteUser(String email, String nickname, String profPicture);
}
