package com.hobbyvillage.backend.admin_users;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminUsersMapper {
	// 미검색 상태에서 회원 수 조회
	@Select("SELECT COUNT(*) FROM users WHERE userCode NOT IN('1');")
	int getUserCount();

	// 검색 상태에서 회원 수 조회(검색 대상 Like '%검색 키워드%')
	@Select("SELECT COUNT(*) FROM users WHERE userCode NOT IN('1') AND ${condition} LIKE '%${keyword}%';")
	int getSearchUserCount(@Param("condition") String condition, @Param("keyword") String keyword);

	// 미검색 상태에서 회원 목록 조회(sort 기준 정렬)
	@Select("SELECT userCode, email, name, deleted FROM users WHERE userCode NOT IN('1') "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminUsersDTO> getUserList(@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 검색 상태에서 회원 목록 조회(검색 대상 Like '%검색 키워드%', sort 기준 정렬)
	@Select("SELECT userCode, email, name, deleted FROM users WHERE userCode NOT IN('1') AND "
			+ "${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminUsersDTO> getSearchUserList(@Param("condition") String condition, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 회원 삭제
	@Delete("DELETE FROM users WHERE userCode = #{userCode}")
	void deleteUser(@Param("userCode") int userCode);
}
