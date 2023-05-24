package com.hobbyvillage.backend.user_users;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {
	// 로그인 정보 확인
	@Select("SELECT COUNT(*) FROM users WHERE email = #{email} AND password = #{password};")
	int loginCheck(LoginDTO users);

	@Select("SELECT nickname, profPicture FROM users WHERE email = #{email} AND password = #{password};")
	LoginDTO getUserData(LoginDTO users);

	@Select("SELECT COUNT(*) FROM users WHERE nickname = #{nickname};")
	int nicknameCheck(LoginDTO users);

	@Select("SELECT COUNT(*) FROM users WHERE email = #{email};")
	int emailCheck(LoginDTO users);

	@Insert("INSERT INTO users(email,name,password, birthday, nickname, profPicture, phone) VALUES"
			+ " (#{email}, #{name}, #{password}, #{birthday}, #{nickname}, #{profPicture}, #{phone});")
	int signup(LoginDTO users);

	@Insert("INSERT INTO userAddress(zipCode, address1, address2, isDefault, email, receiver, phone)"
			+ " VALUES (#{zipCode},#{address1},#{address2},#{isDefault},#{email},#{name},#{phone});")
	int signupAddr(LoginDTO users);

	@Select("SELECT email, nickname, password, name, birthday, phone FROM users WHERE email = #{email};")
	LoginDTO getUserDetail(@Param("email") String email);

	@Update("UPDATE users SET nickname= #{nickname}, email= #{email} , password=#{password}, name= #{name}, birthday= #{birthday}, phone= #{phone}"
			+ "WHERE email = #{email}; ")
	int modifyMember(LoginDTO users);

	@Delete("DELETE FROM users WHERE email = #{email}; ")
	int deleteMember(LoginDTO users);

}
