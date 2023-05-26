package com.hobbyvillage.backend.user_users;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {
	// 로그인 정보 확인
	@Select("SELECT COUNT(*) FROM users WHERE email = #{email} AND password = #{password};")
	int loginCheck(LoginDTO users);

	// 로그인 후 유저 정보 조회
	@Select("SELECT nickname, profPicture FROM users WHERE email = #{email} AND password = #{password};")
	LoginDTO getUserData(LoginDTO users);

	// 이메일 중복 체크
	@Select("SELECT COUNT(*) FROM users WHERE email = #{email};")
	int emailCheck(@Param("email") String email);

	// 닉네임 중복 체크
	@Select("SELECT COUNT(*) FROM users WHERE nickname = #{nickname};")
	int nicknameCheck(@Param("nickname") String nickname);

	// 회원가입 1: 유저 정보 저장(users)
	@Insert("INSERT INTO users(email, name, password, birthday, nickname, phone) VALUES"
			+ "(#{email}, #{name}, #{password}, #{birthday}, #{nickname}, #{phone});")
	int signup(MemberDTO users);

	// 회원가입 2: 유저 정보 저장(userAddresss)
	@Insert("INSERT INTO userAddress(zipCode, address1, address2, isDefault, email, receiver, phone) "
			+ "VALUES (#{zipCode}, #{address1}, #{address2}, 1, #{email}, #{name}, #{phone});")
	int signupAddr(MemberDTO users);

	// 회원가입 3: 유저 프로필 사진 저장
	@Update("UPDATE users SET profPicture = #{profPicture} WHERE email = #{email};")
	int addPicture(@Param("email") String email, @Param("profPicture") String profPicture);

	// 회원 정보 조회
	@Select("SELECT email, name, nickname, birthday, phone, profPicture FROM users WHERE email = #{email};")
	MemberDTO getUserDetail(@Param("email") String email);

	// 회원 정보 수정
	@Update("UPDATE users SET nickname= #{nickname}, email= #{email} , password=#{password}, name= #{name}, "
			+ "birthday= #{birthday}, phone= #{phone} WHERE email = #{email}; ")
	int modifyMember(MemberDTO users);

	// 회원 탈퇴 준비 과정 1: 판매/위탁 중인 물품 중 완료, 심사 탈락, 철회 완료 상태가 아닌 물품 검색
	@Select("SELECT COUNT(*) FROM requests WHERE reqEmail = #{email} AND reqProgress IN('완료', '심사 탈락', '철회 완료');")
	int checkRequest(@Param("email") String email);

	// 회원 탈퇴 준비 과정 2: 반납 완료되지 않은 주문 물품 검색
	@Select("SELECT COUNT(*) FROM orders o INNER JOIN orderProducts op "
			+ "ON o.odrNumber = op.odrNumber WHERE o.odrEmail = #{email} AND op.odrState != '반납 완료';")
	int checkOrderProduct(@Param("email") String email);

	// 회원 탈퇴 과정 1: 판매/위탁 중인 물품의 상태 변경
	// (reqEmail = '취미빌리지', reqSort = 판매, reqBank/reqAccountNum null)
	@Update("UPDATE requests SET reqEmail = '취미빌리지', reqSort = '판매', reqBank = NULL, reqAccountNum = NULL "
			+ "WHERE reqEmail = #{email};")
	void updateRequest(@Param("email") String email);

	// 회원 탈퇴 과정 2: 상품의 상태 변경(prodHost = '취미빌리지', reqCode = 0)
	@Update("UPDATE products SET prodHost = '취미빌리지', reqCode = 0 WHERE prodHost = #{nickname};")
	void updateProduct(@Param("nickname") String nickname);

	// 회원 탈퇴 과정 3: 리뷰 이미지 명 조회(리뷰 이미지 삭제용)
	@Select("SELECT rp.revwPicture FROM reviews r INNER JOIN reviewPictures rp "
			+ "ON r.revwCode = rp.revwCode WHERE revwWriter = #{nickname};")
	List<String> getReviewPictures(@Param("nickname") String nickname);

	// 회원 탈퇴 과정 4: 회원 삭제
	@Delete("DELETE FROM users WHERE email = #{email};")
	int deleteUser(@Param("email") String email);
}
