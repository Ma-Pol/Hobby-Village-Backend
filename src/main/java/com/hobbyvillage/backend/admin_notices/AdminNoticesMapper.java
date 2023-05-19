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

	// 공지사항 상세 조회 1: 실재하는 공지사항인지 확인
	@Select("SELECT COUNT(*) FROM notices WHERE notCode = #{notCode};")
	int checkNotice(@Param("notCode") int notCode);

	// 공지사항 상세 조회 2: 공지사항 상세 조회
	@Select("SELECT * FROM notices WHERE notCode = #{notCode};")
	AdminNoticesDTO getNoticeDetail(@Param("notCode") int notCode);

	// 공지사항 상세 조회 3: 공지사항 첨부파일 조회
	@Select("SELECT * FROM noticeFiles WHERE notCode = #{notCode} ORDER BY notFileCode;")
	List<AdminNoticesFileDTO> getFileData(@Param("notCode") int notCode);

	// 공지사항 등록 1: 공지사항 등록
	@Insert("INSERT INTO notices (notTitle, notCategory, notContent) "
			+ "VALUES(#{notTitle}, #{notCategory}, #{notContent});")
	int createNotice(AdminNoticesDTO notice);

	// 공지사항 등록 2: 등록한 공지사항 notCode 조회
	@Select("SELECT notCode FROM notices WHERE notCategory = #{notCategory} AND notTitle = #{notTitle} "
			+ "AND notContent = #{notContent} ORDER BY -notCode LIMIT 1;")
	int getNotCode(AdminNoticesDTO notice);

	// 공지사항 등록 3: 첨부파일 이름 등록
	@Insert("INSERT INTO noticeFiles(notCode, notFileOriName, notFileSavName) VALUES"
			+ "(#{notCode}, #{originalFileName}, #{storedFileName});")
	void createFileName(@Param("notCode") int notCode, @Param("originalFileName") String originalFileName,
			@Param("storedFileName") String storedFileName);

	// 공지사항 삭제 1: 파일 개수 파악 (+ 수정)
	@Select("SELECT COUNT(*) FROM noticeFiles WHERE notCode = #{notCode};")
	int getNoticeFileCount(@Param("notCode") int notCode);

	// 공지사항 삭제 2: 파일명 조회 (+ 수정)
	@Select("SELECT notFileSavName FROM noticeFiles WHERE notCode = #{notCode};")
	List<String> getNoticeFileName(@Param("notCode") int notCode);

	// 공지사항 삭제 3: 공지사항 삭제
	@Delete("DELETE FROM notices WHERE notCode = #{notCode};")
	int deleteNotice(@Param("notCode") int notCode);

	// 공지사항 수정 3: 공지사항 파일명 삭제
	@Delete("DELETE FROM noticeFiles WHERE notCode = #{notCode};")
	void deleteNoticeFileName(@Param("notCode") int notCode);

	// 공지사항 수정 4: 공지사항 수정
	@Update("UPDATE notices SET notTitle = #{notTitle}, notCategory = #{notCategory}, "
			+ "notContent = #{notContent} WHERE notCode = #{notCode};")
	int modifyNotice(AdminNoticesDTO notice);

}
