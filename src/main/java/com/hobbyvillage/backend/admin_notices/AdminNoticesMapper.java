package com.hobbyvillage.backend.admin_notices;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminNoticesMapper {
	// 미검색 상태에서 공지사항 개수 조회
	@Select("SELECT COUNT(*) FROM notices WHERE ${filter};")
	int getNoticeCount(@Param("filter") String filter);

	// 검색 상태에서 공지사항 개수 조회
	@Select("SELECT COUNT(*) FROM notices WHERE ${filter} AND (notTitle LIKE '%${keyword}%' OR notContent LIKE '%${keyword}%');")
	int getSearchNoticeCount(@Param("filter") String filter, @Param("keyword") String keyword);

	// 미검색 상태에서 공지사항 목록 조회
	@Select("SELECT notCode, notCategory, notTitle, notDate FROM notices WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminNoticesDTO> getNoticeList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 공지사항 목록 조회
	@Select("SELECT notCode, notCategory, notTitle, notDate FROM notices WHERE ${filter} AND "
			+ "(notTitle LIKE '%${keyword}%' OR notContent LIKE '%${keyword}%') ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminNoticesDTO> getSearchNoticeList(@Param("filter") String filter, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	@Select("SELECT * FROM notices WHERE notCode = #{notCode};")
	AdminNoticesDTO getNoticeDetail(@Param("notCode") int notCode);
}