package com.hobbyvillage.backend.admin_reviews;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminreviewsMapper {

	// 미검색 상태에서 리뷰 개수 조회
	@Select("SELECT COUNT(*) FROM reviews WHERE ${filter};")
	int getReviewCount(@Param("filter") String filter);

	// 검색 상태에서 리뷰 개수 조회
	@Select("SELECT COUNT(*) FROM reviews WHERE ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchReviewCount(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword);

	// 미검색 상태에서 리뷰 목록 조회
	@Select("SELECT revwCode, prodCode, revwRate, revwTitle, revwRegiDate, revwReport "
			+ "FROM reviews WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminReviewsDTO> getReviewList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 리뷰 목록 조회
	@Select("SELECT revwCode, prodCode, revwRate, revwTitle, revwRegiDate, revwReport "
			+ "FROM reviews WHERE ${filter} AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminReviewsDTO> getSearchReviewList(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);

	// 실재 리뷰 확인
	@Select("SELECT COUNT(*) FROM reviews WHERE revwCode = #{revwCode};")
	int checkReviews(@Param("revwCode") String revwCode);

	// 리뷰 상세 조회
	@Select("SELECT r.revwCode, r.revwRegiDate, p.prodCode, p.prodName, r.revwRate, r.revwWriter, r.revwTitle, r.revwReport, r.revwContent "
			+ "FROM reviews r INNER JOIN products p ON r.prodCode = p.prodCode WHERE r.revwCode = #{revwCode};")
	AdminReviewsDTO getReviewsDetails(@Param("revwCode") String revwCode);

	// 리뷰 이미지 조회
	@Select("SELECT revwPicture FROM reviewPictures WHERE revwCode = #{revwCode};")
	List<String> getReviewImage(@Param("revwCode") String revwCode);

	// 리뷰 삭제
	@Delete("DELETE FROM reviews WHERE revwCode = #{revwCode};")
	int deleteReivew(@Param("revwCode") String revwCode);

}
