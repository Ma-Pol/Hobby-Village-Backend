package com.hobbyvillage.backend.admin_login;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminLoginMapper {
	// 로그인 정보 확인
	@Select("SELECT COUNT(*) FROM admins WHERE id = #{id} AND password = #{password};")
	int loginCheck(AdminLoginDTO admin);

	@Select("SELECT nickname FROM admins WHERE id = #{id} AND password = #{password};")
	String getNickname(AdminLoginDTO admin);
}
