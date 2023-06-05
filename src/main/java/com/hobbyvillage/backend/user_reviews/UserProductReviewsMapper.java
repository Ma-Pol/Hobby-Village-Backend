package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserProductReviewsMapper {

	// 상품별 리뷰 개수 조회
	@Select("SELECT count(*) FROM reviews WHERE prodCode=#{prodCode};")
	int getProdRevwCount(@Param("prodCode") String prodCode);

	// 리뷰 목록+상세 조회
	@Select("SELECT revwCode, revwRate, revwTitle, revwContent, revwWriter, revwRegiDate, profPicture "
			+ "FROM reviews r INNER JOIN users u ON r.revwWriter = u.nickname "
			+ "WHERE prodCode = #{prodCode} ORDER BY revwRegiDate DESC;")
	List<UserProductReviewsDTO> getProdRevwList(@Param("prodCode") String prodCode);

	// 리뷰 신고 여부 확인
	@Select("SELECT COUNT(*) FROM reviewReports WHERE email=#{email} AND revwCode=#{revwCode};")
	int checkIsReported(@Param("email") String email, @Param("revwCode") String revwCode);

	// 리뷰 신고
	@Insert("INSERT INTO reviewReports (email, revwCode) VALUES (#{email}, #{revwCode});")
	void reportReview(@Param("email") String email, @Param("revwCode") String revwCode);

	// 리뷰 신고 - 신고 횟수 누적
	@Update("UPDATE reviews SET revwReport=revwReport+1 WHERE revwCode=#{revwCode};")
	void plusRevwReport(@Param("revwCode") String revwCode);

	// 리뷰별 이미지 파일명 조회
	@Select("SELECT revwPicture FROM reviewPictures WHERE revwCode=#{revwCode} ORDER BY revwPicture ASC;")
	List<String> getProdRevwPics(@Param("revwCode") String revwCode);

}
