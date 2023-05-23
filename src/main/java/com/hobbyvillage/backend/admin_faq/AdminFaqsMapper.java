package com.hobbyvillage.backend.admin_faq;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminFaqsMapper {
	// 미검색 상태에서 FAQ 개수 조회
	@Select("SELECT COUNT(*) FROM faqs WHERE ${filter};")
	int getFaqCount(@Param("filter") String filter);

	// 검색 상태에서 FAQ 개수 조회
	@Select("SELECT COUNT(*) FROM faqs WHERE ${filter} AND (faqTitle LIKE '%${keyword}%' OR faqContent LIKE '%${keyword}%');")
	int getSearchFaqCount(@Param("filter") String filter, @Param("keyword") String keyword);

	// 미검색 상태에서 FAQ 목록 조회
	@Select("SELECT faqCode, faqCategory, faqTitle, faqDate FROM faqs "
			+ "WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminFaqsDTO> getFaqList(@Param("filter") String fitler, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 FAQ 목록 조회
	@Select("SELECT faqCode, faqCategory, faqTitle, faqDate FROM faqs "
			+ "WHERE ${filter} AND (faqTitle LIKE '%${keyword}%' OR faqContent LIKE '%${keyword}%') "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminFaqsDTO> getSearchFaqList(@Param("filter") String fitler, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	// FAQ 체크
	@Select("SELECT COUNT(*) FROM faqs WHERE faqCode = #{faqCode};")
	int checkFaq(@Param("faqCode") int faqCode);

	// FAQ 상세 조회
	@Select("SELECT * FROM faqs WHERE faqCode = #{faqCode};")
	AdminFaqsDTO getFaqDetail(@Param("faqCode") int faqCode);

	// FAQ 삭제
	@Delete("DELETE FROM faqs WHERE faqCode = #{faqCode};")
	int deleteFaq(@Param("faqCode") int faqCode);

	// FAQ 등록 1 - 중복 체크
	@Select("SELECT COUNT(*) FROM faqs WHERE faqCategory = #{faqCategory} AND faqTitle = #{faqTitle} AND faqContent = #{faqContent};")
	int checkDuplication(AdminFaqsDTO faq);

	// FAQ 등록 2 - 등록
	@Insert("INSERT INTO faqs(faqCategory, faqTitle, faqContent) VALUES(#{faqCategory}, #{faqTitle}, #{faqContent});")
	int createFaq(AdminFaqsDTO faq);
	
	// FAQ 등록 3 - faqCode 조회
	@Select("SELECT faqCode FROM faqs WHERE faqCategory = #{faqCategory} AND faqTitle = #{faqTitle} AND faqContent = #{faqContent};")
	int getFaqCode(AdminFaqsDTO faq);

	// FAQ 수정
	@Update("UPDATE faqs SET faqCategory = #{faqCategory}, faqTitle = #{faqTitle}, faqContent = #{faqContent} WHERE faqCode = #{faqCode};")
	int modifyFaq(AdminFaqsDTO faq);
}
