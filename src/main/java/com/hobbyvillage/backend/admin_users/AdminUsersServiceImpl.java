package com.hobbyvillage.backend.admin_users;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminUsersServiceImpl implements AdminUsersService {

	private AdminUsersMapper mapper;

	public AdminUsersServiceImpl(AdminUsersMapper mapper) {
		this.mapper = mapper;
	}

	@Override // 미검색 상태에서 회원 수 조회
	public int getUserCount() {
		return mapper.getUserCount();
	}

	@Override // 검색 상태에서 회원 수 조회
	public int getSearchUserCount(String condition, String keyword) {
		return mapper.getSearchUserCount(condition, keyword);
	}

	@Override // 미검색 상태에서 회원 목록 조회
	public List<AdminUsersDTO> getUserList(String sort, int pageNum) {
		return mapper.getUserList(sort, pageNum);
	}

	@Override // 검색 상태에서 회원 목록 조회
	public List<AdminUsersDTO> getSearchUserList(String condition, String keyword, String sort, int pageNum) {
		return mapper.getSearchUserList(condition, keyword, sort, pageNum);
	}

	@Override // 회원 삭제
	public void deleteUser(int userCode) {
		mapper.deleteUser(userCode);
	}

}
