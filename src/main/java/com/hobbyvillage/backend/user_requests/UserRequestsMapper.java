package com.hobbyvillage.backend.user_requests;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRequestsMapper {
	// 카테고리 불러오기
	@Select("SELECT category FROM categories ORDER BY categoryCode;")
	List<String> getCategories();

	// 신청 내용 저장
	@Insert("INSERT INTO requests(reqSort, reqEmail, reqBank, reqAccountNum, reqCategory, reqTitle, "
			+ "reqContent, reqProgress) VALUES(#{reqSort}, #{reqEmail}, #{reqBank}, #{reqAccountNum}, "
			+ "#{reqCategory}, #{reqTitle}, #{reqContent}, '1차 심사 중');")
	int createRequest(UserRequestsDTO request);

	// 저장한 신청 reqCode 조회 (판매 시)
	@Select("SELECT reqCode FROM requests WHERE reqSort = #{reqSort} AND reqEmail = #{reqEmail} AND "
			+ " reqCategory = #{reqCategory} AND reqTitle = #{reqTitle} AND reqContent = #{reqContent} "
			+ "AND reqProgress = '1차 심사 중' ORDER BY -reqCode LIMIT 1;")
	int getReqCodeSell(UserRequestsDTO request);
	
	// 저장한 신청 reqCode 조회 (위탁 시)
	@Select("SELECT reqCode FROM requests WHERE reqSort = #{reqSort} AND reqEmail = #{reqEmail} AND "
			+ "reqBank = #{reqBank} AND reqAccountNum = #{reqAccountNum} AND reqCategory = #{reqCategory} "
			+ "AND reqTitle = #{reqTitle} AND reqContent = #{reqContent} AND reqProgress = '1차 심사 중' "
			+ "ORDER BY -reqCode LIMIT 1;")
	int getReqCodeConsign(UserRequestsDTO request);

	// 신청 이미지 파일명 저장
	@Insert("INSERT INTO requestfiles(reqCode, reqFile) VALUES(#{reqCode}, #{storedFileName});")
	void insertFileName(@Param("reqCode") int reqCode, @Param("storedFileName") String storedFileName);
}
