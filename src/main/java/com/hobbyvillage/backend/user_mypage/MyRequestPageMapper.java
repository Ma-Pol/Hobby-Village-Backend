package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MyRequestPageMapper {

	// 판매/위탁 신청 개수 조회
	@Select("SELECT COUNT(*) FROM requests WHERE reqEmail = #{email} AND ${filter};")
	int getRequestCount(@Param("email") String email, @Param("filter") String filter);

	// 판매/위탁 신청 목록 조회
	@Select("SELECT * FROM requests WHERE reqEmail = #{email} AND ${filter} ORDER BY -reqDate LIMIT #{pageNum}, 5;")
	List<MyRequestPageDTO> getRequestList(@Param("email") String email, @Param("filter") String filter,
			@Param("pageNum") int pageNum);

	// 판매/위탁 신청 물품 이미지 파일명 조회
	@Select("SELECT reqFile FROM requestFiles WHERE reqCode = #{reqCode} ORDER BY reqFile;")
	List<String> getRequestPictures(@Param("reqCode") int reqCode);

	// 위탁 철회
	@Update("UPDATE requests SET reqProgress = '위탁 철회 요청' WHERE reqCode = #{reqCode};")
	void withdrawRequest(@Param("reqCode") int reqCode);

	// 계좌정보 수정
	@Update("UPDATE requests SET reqBank = #{reqBank}, reqAccountNum = #{reqAccountNum} WHERE reqCode = #{reqCode};")
	void updateAccount(@Param("reqBank") String reqBank, @Param("reqAccountNum") String reqAccountNum,
			@Param("reqCode") int reqCode);

}
