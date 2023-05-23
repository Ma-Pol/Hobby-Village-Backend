package com.hobbyvillage.backend.user_users;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hobbyvillage.backend.admin_faq.AdminFAQDTO;



@Mapper
public interface MemberMapper {
		
		@Select("SELECT COUNT(*) FROM users WHERE email = #{email} AND password = #{password};")
		int login(MemberDTO users);
		
		@Select("SELECT COUNT(*) FROM users WHERE nickname = #{nickname};")
		int nicknameCheck(MemberDTO users);
		
		@Select("SELECT COUNT(*) FROM users WHERE email = #{email};")
		int emailCheck(MemberDTO users);
		
		@Insert("INSERT INTO users(email,name,password, birthday, nickname, profPicture, phone) VALUES"
				+ " (#{email}, #{name}, #{password}, #{birthday}, #{nickname}, #{profPicture}, #{phone});")
		int signup(MemberDTO users);
		
		@Insert("INSERT INTO userAddress(zipCode, address1, address2, isDefault, email, receiver, phone)"
		 	 + " VALUES (#{zipCode},#{address1},#{address2},#{isDefault},#{email},#{name},#{phone});")
		int signupAddr(MemberDTO users);
		
		@Select("SELECT email, nickname, password, name, birthday, phone FROM users WHERE email = #{email};")
		MemberDTO getUserDetail(@Param("email") String email);
		
		
		@Update("UPDATE users SET nickname= #{nickname}, email= #{email} , password=#{password}, name= #{name}, birthday= #{birthday}, phone= #{phone}"
				+ "WHERE email = #{email}; ")
		int modifyMember(MemberDTO users);
		
		@Delete("DELETE FROM users WHERE email = #{email}; ")
		int deleteMember(MemberDTO users);

		
}
