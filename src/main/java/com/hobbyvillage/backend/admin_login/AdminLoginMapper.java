package com.hobbyvillage.backend.admin_login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AdminLoginMapper {

	@Select("SELECT COUNT(*) FROM admins WHERE id = #{id} AND password = #{password};")
	int login(AdminLoginDTO admins);
}
