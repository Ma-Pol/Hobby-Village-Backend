package com.hobbyvillage.backend.user_cs;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FaqMapper {
	// 미검색 상태에서 FAQ 개수 조회
	@Select("SELECT COUNT(*) FROM faqs WHERE ${filter};")
	int getFaqCount(@Param("filter") String filter);

	// 검색 상태에서 FAQ 개수 조회
	@Select("SELECT COUNT(*) FROM faqs WHERE ${filter} AND (faqTitle LIKE '%${keyword}%' OR faqContent LIKE '%${keyword}%');")
	int getSearchFaqCount(@Param("filter") String filter, @Param("keyword") String keyword);

	// 미검색 상태에서 FAQ 목록 조회
	@Select("SELECT faqCode, faqCategory, faqTitle, faqDate FROM faqs "
			+ "WHERE ${filter} ORDER BY -faqCode LIMIT #{pageNum}, 10;")
	List<FaqDTO> getFaqList(@Param("filter") String fitler, @Param("pageNum") int pageNum);

	// 검색 상태에서 FAQ 목록 조회
	@Select("SELECT faqCode, faqCategory, faqTitle, faqDate FROM faqs "
			+ "WHERE ${filter} AND (faqTitle LIKE '%${keyword}%' OR faqContent LIKE '%${keyword}%') "
			+ "ORDER BY -faqCode LIMIT #{pageNum}, 10;")
	List<FaqDTO> getSearchFaqList(@Param("filter") String fitler, @Param("keyword") String keyword,
			@Param("pageNum") int pageNum);

	@Select("SELECT faqTitle, faqContent, faqCategory FROM faqs WHERE faqCode = #{faqCode}")
	FaqDTO getFaqDetail(@Param("faqCode") Integer faqCode);
}