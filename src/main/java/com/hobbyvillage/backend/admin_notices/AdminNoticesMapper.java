package com.hobbyvillage.backend.admin_notices;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

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

	// 공지사항 상세 조회
	@Select("SELECT * FROM notices WHERE notCode = #{notCode};")
	AdminNoticesDTO getNoticeDetail(@Param("notCode") int notCode);

	// 공지사항 등록
	@Insert("INSERT INTO notices (notTitle, notCategory, notContent) " +
			"VALUES (#{notTitle}, #{notCategory}, #{notContent});")
	int createNotice(AdminNoticesDTO notice);

	// 공지사항 코드 받아오기
	@Select("SELECT notCode FROM notices WHERE notTitle = #{notTitle} AND notCategory = #{notCategory} AND notContent = #{notContent}")
	int getNotCode(AdminNoticesDTO notice);

	// 공지사항 첨부파일 업로드
	@Insert("INSERT INTO noticefiles (notCode, notFileOriName, notFileSavName) " +
	"VALUES (#{notCode}, #{originalName}, #{savedName})")
	void createNoticeFile(@Param("notCode") int notCode, @Param("originalName") String originalName, @Param("savedName") String savedName);

	// 공지사항 삭제
	@Delete("DELETE FROM notices WHERE notCode=#{notCode} ")
	int deleteNotice(@Param("notCode") int notCode);

	// 공지사항 수정
	@Update("UPDATE notices " +
			"SET notTitle = #{notTitle}, notCategory = #{notCategory}, notContent = #{notContent} " +
			"WHERE notCode = #{notCode} ")
	int modifyNotice(AdminNoticesDTO notice);

}
