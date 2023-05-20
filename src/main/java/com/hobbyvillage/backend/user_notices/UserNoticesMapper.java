package com.hobbyvillage.backend.user_notices;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserNoticesMapper {
	// 미검색 상태에서 공지사항 개수 조회
	@Select("SELECT COUNT(*) FROM notices WHERE ${filter};")
	int getNoticeCount(@Param("filter") String filter);

	// 검색 상태에서 공지사항 개수 조회
	@Select("SELECT COUNT(*) FROM notices WHERE ${filter} AND (notTitle LIKE '%${keyword}%' OR notContent LIKE '%${keyword}%');")
	int getSearchNoticeCount(@Param("filter") String filter, @Param("keyword") String keyword);

	// 미검색 상태에서 공지사항 목록 조회
	@Select("SELECT notCode, notCategory, notTitle, notDate, notView FROM notices WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<UserNoticesDTO> getNoticeList(@Param("filter") String filter, @Param("sort") String sort,
									   @Param("pageNum") int pageNum);

	// 검색 상태에서 공지사항 목록 조회
	@Select("SELECT notCode, notCategory, notTitle, notDate, notView FROM notices WHERE ${filter} AND "
			+ "(notTitle LIKE '%${keyword}%' OR notContent LIKE '%${keyword}%') ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<UserNoticesDTO> getSearchNoticeList(@Param("filter") String filter, @Param("keyword") String keyword,
											 @Param("sort") String sort, @Param("pageNum") int pageNum);

	// 공지사항 상세 조회
	@Select("SELECT * FROM notices WHERE notCode = #{notCode};")
	UserNoticesDTO getNoticeDetail(@Param("notCode") int notCode);

	// 공지사항 조회수 증가
	@Update("UPDATE notices SET notView = notView + 1 WHERE notCode = #{notCode} ")
	int updateNoticeView(@Param("notCode") int notCode);
}
