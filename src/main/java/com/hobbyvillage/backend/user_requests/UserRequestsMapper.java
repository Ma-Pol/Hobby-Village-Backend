package com.hobbyvillage.backend.user_requests;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRequestsMapper {
	@Insert("INSERT INTO requests(reqSort, reqBank, reqAccountNum, reqCategory, reqTitle, reqContent, reqProgress) VALUES"
			+ "(#{reqSort}, #{reqBank}, #{reqAccountNum}, #{reqCategory}, #{reqTitle}, #{reqContent}, '1차 심사 중');")
	int createRequest(UserRequestsDTO request);

	@Select("SELECT reqCode FROM requests WHERE reqSort = #{reqSort} AND reqBank = #{reqBank} AND reqAccountNum = #{reqAccountNum} "
			+ "AND reqCategory = #{reqCategory} AND reqTitle = #{reqTitle} AND reqContent = #{reqContent} "
			+ "AND reqProgress = '1차 심사 중';")
	int getReqCode(UserRequestsDTO request);

	@Insert("INSERT INTO requestfiles(reqCode, reqFile) VALUES(#{reqCode}, #{storedFileName});")
	void insertFileName(@Param("reqCode") int reqCode, @Param("storedFileName") String storedFileName);
}
