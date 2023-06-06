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
	@Select("SELECT userCode, email, name, nickname, profPicture FROM users WHERE userCode NOT IN('1') "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminUsersDTO> getUserList(@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 검색 상태에서 회원 목록 조회(검색 대상 Like '%검색 키워드%', sort 기준 정렬)
	@Select("SELECT userCode, email, name, nickname, profPicture FROM users WHERE userCode NOT IN('1') AND "
			+ "${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminUsersDTO> getSearchUserList(@Param("condition") String condition, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 회원 존재 여부 확인
	@Select("SELECT COUNT(*) FROM users WHERE userCode = #{userCode};")
	int checkUser(@Param("userCode") int userCode);

	// 회원 상세 정보 조회 1: 회원 정보 조회
	@Select("SELECT * FROM users WHERE userCode = #{userCode};")
	AdminUsersDTO getUserDetail(@Param("userCode") int userCode);

	// 회원 상세 정보 조회 2: 회원 기본 배송지 조회
	@Select("SELECT zipCode, address1, address2 from userAddress WHERE email = #{email} AND isDefault = 1;")
	AdminUsersDTO getUserAddress(@Param("email") String email);

	// 회원 삭제 준비 과정 1: 판매/위탁 중인 물품 중 완료, 심사 탈락, 철회 완료 상태가 아닌 물품 검색
	@Select("SELECT COUNT(*) FROM requests WHERE reqEmail = #{email} AND reqProgress IN('완료', '심사 탈락', '철회 완료');")
	int checkRequest(@Param("email") String email);

	// 회원 삭제 준비 과정 2: 반납 완료되지 않은 주문 물품 검색
	@Select("SELECT COUNT(*) FROM orders o INNER JOIN orderProducts op "
			+ "ON o.odrNumber = op.odrNumber WHERE o.odrEmail = #{email} AND op.odrState != '반납 완료';")
	int checkOrderProduct(@Param("email") String email);

	// 회원 삭제 과정 1: 판매/위탁 중인 물품의 상태 변경
	// (reqEmail = '취미빌리지', reqSort = 판매, reqBank/reqAccountNum null)
	@Update("UPDATE requests SET reqEmail = '취미빌리지', reqSort = '판매', reqBank = NULL, reqAccountNum = NULL "
			+ "WHERE reqEmail = #{email};")
	void updateRequest(@Param("email") String email);

	// 회원 삭제 과정 2: 상품의 상태 변경(prodHost = '취미빌리지', reqCode = 0)
	@Update("UPDATE products SET prodHost = '취미빌리지', reqCode = 0 WHERE prodHost = #{nickname};")
	void updateProduct(@Param("nickname") String nickname);

	// 회원 삭제 과정 3: 리뷰 이미지 명 조회(리뷰 이미지 삭제용)
	@Select("SELECT rp.revwPicture FROM reviews r INNER JOIN reviewPictures rp "
			+ "ON r.revwCode = rp.revwCode WHERE revwWriter = #{nickname};")
	List<String> getReviewPictures(@Param("nickname") String nickname);

	// 회원 삭제 과정 4: 회원 삭제
	@Delete("DELETE FROM users WHERE email = #{email};")
	void deleteUser(@Param("email") String email);
	
	// 회원 삭제 과정 5: 회원 삭제 카운트 증가
	@Insert("INSERT INTO deletedUserCount VALUE();")
	int insertDeleteUserCount();
}
