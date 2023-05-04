package com.hobbyvillage.backend.admin_faq;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminFaqsMapper {
	@Select("SELECT COUNT(*) FROM faqs WHERE ${filter};")
	int getFaqCount(@Param("filter") String filter);

	@Select("SELECT COUNT(*) FROM faqs WHERE ${filter} AND (faqTitle LIKE '%${keyword}%' OR faqContent LIKE '%${keyword}%');")
	int getSearchFaqCount(@Param("filter") String filter, @Param("keyword") String keyword);

	@Select("SELECT faqCode, faqCategory, faqTitle, faqDate FROM faqs "
			+ "WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminFaqsDTO> getFaqList(@Param("filter") String fitler, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	@Select("SELECT faqCode, faqCategory, faqTitle, faqDate FROM faqs "
			+ "WHERE ${filter} AND (faqTitle LIKE '%${keyword}%' OR faqContent LIKE '%${keyword}%') "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminFaqsDTO> getSearchFaqList(@Param("filter") String fitler, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("pageNum") int pageNum);
}
