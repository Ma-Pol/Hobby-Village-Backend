package com.hobbyvillage.backend.admin_users;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminUsersMapper {
	@Select("SELECT COUNT(*) FROM users WHERE userCode NOT IN('1');")
	int getUserCount();

	@Select("SELECT COUNT(*) FROM users WHERE userCode NOT IN('1') AND ${condition} LIKE '%${keyword}%';")
	int getSearchUserCount(@Param("condition") String condition, @Param("keyword") String keyword);

	@Select("SELECT userCode, email, name FROM users WHERE userCode NOT IN('1') ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminUsersDTO> getUserList(@Param("sort") String sort, @Param("pageNum") int pageNum);

	@Select("SELECT userCode, email, name FROM users WHERE userCode NOT IN('1') AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminUsersDTO> getSearchUserList(@Param("condition") String condition, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	@Delete("DELETE FROM users WHERE userCode = #{userCode}")
	void deleteUser(@Param("userCode") int userCode);
}
