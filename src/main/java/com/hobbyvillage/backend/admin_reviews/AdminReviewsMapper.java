package com.hobbyvillage.backend.admin_reviews;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminReviewsMapper {
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
}
