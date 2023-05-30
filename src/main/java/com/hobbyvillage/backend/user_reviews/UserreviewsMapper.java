package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserReviewsMapper {

	// 리뷰 개수 조회
	@Select("SELECT COUNT(*) FROM reviews r INNER JOIN users u ON r.revwWriter = u.nickname WHERE u.email = #{email};")
	int getReviewCount(@Param("email") String email);

	// 리뷰 목록 조회
	@Select("SELECT r.revwCode, r.revwRate, r.revwTitle, r.revwRegiDate, r.revwReport, p.prodCode, p.prodName "
			+ "FROM reviews r INNER JOIN products p ON r.prodCode = p.prodCode INNER JOIN users u ON r.revwWriter = u.nickname "
			+ "WHERE u.email = #{email} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<UserReviewsListsDTO> getReviewList(@Param("email") String email, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 실재 리뷰 확인
	@Select("SELECT COUNT(*) FROM reviews r INNER JOIN users u ON r.revwWriter = u.nickname "
			+ "WHERE r.revwCode = #{revwCode} AND u.email = #{email};")
	int checkReviews(@Param("revwCode") String revwCode, @Param("email") String email);

	// 리뷰 상세 조회
	@Select("SELECT r.revwCode, r.revwWriter, r.revwTitle, r.revwContent, r.revwReport, r.revwRate, p.prodCode, p.prodName "
			+ "FROM reviews r INNER JOIN products p ON r.prodCode = p.prodCode WHERE r.revwCode = #{revwCode};")
	UserReviewsListsDTO getReviewsDetails(@Param("revwCode") String revwCode);

	// 리뷰 이미지 조회
	@Select("SELECT revwPicture FROM reviewPictures WHERE revwCode = #{revwCode};")
	List<String> getReviewImageName(@Param("revwCode") String revwCode);

	// 리뷰 수정 1: 리뷰 정보 수정
	@Update("UPDATE reviews SET revwTitle = #{revwTitle}, revwContent = #{revwContent}, revwRate = #{revwRate} WHERE revwCode = #{revwCode};")
	int modifyReview(UserReviewsListsDTO reviewData);

	// 리뷰 수정 2: 리뷰 이미지 정보 삭제
	@Delete("DELETE FROM reviewPictures WHERE revwCode = #{revwCode};")
	void deleteReviewPictures(@Param("revwCode") String revwCode);

	// 리뷰 이미지 정보 추가
	@Insert("INSERT INTO reviewPictures(revwCode, revwPicture) VALUES(#{revwCode}, #{revwPicture});")
	int insertReviewPictures(@Param("revwCode") String revwCode, @Param("revwPicture") String revwPicture);

	// 리뷰 작성 전 주문 여부 확인
	@Select("SELECT COUNT(*) FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "WHERE o.odrEmail = #{email} AND op.prodCode = #{prodCode} AND "
			+ "op.odrState NOT IN('결제 완료', '배송 준비 중', '배송 중');")
	int checkOrders(@Param("email") String email, @Param("prodCode") String prodCode);

	// 리뷰 작성 전 주문 여부 확인
	@Select("SELECT COUNT(*) FROM reviews r INNER JOIN users u ON r.revwWriter = u.nickname "
			+ "WHERE u.email = #{email} AND r.prodCode = #{prodCode};")
	int checkReviewed(@Param("email") String email, @Param("prodCode") String prodCode);

	// 리뷰 작성할 상품명 조회
	@Select("SELECT prodName FROM products WHERE prodCode = #{prodCode};")
	String getProdName(@Param("prodCode") String prodCode);

	// 리뷰 작성 1: 리뷰 정보 저장
	@Insert("INSERT INTO reviews(revwCode, prodCode, revwRate, revwTitle, revwContent, revwWriter) VALUES "
			+ "(#{revwCode}, #{prodCode}, #{revwRate}, #{revwTitle}, #{revwContent}, #{revwWriter});")
	int createReview(UserReviewsListsDTO reviewData);

}
