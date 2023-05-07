package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminRequestsMapper {
	// 신청 상세 정보 조회
	@Select("SELECT rq.reqCode, rq.reqSort, rq.reqEmail, rq.reqCategory, rq.reqTitle, rq.reqContent, rq.reqProgress, u.nickname "
			+ "FROM requests rq INNER JOIN users u ON rq.reqEmail = u.email WHERE rq.reqCode = #{reqCode};")
	AdminRequestsDetailsDTO getRequestDetail(@Param("reqCode") int reqCode);

	// 신청 파일명 조회
	@Select("SELECT reqFile FROM requestFiles WHERE reqCode = #{reqCode};")
	List<String> getRequestFileList(@Param("reqCode") int reqCode);

	// 신청 진행 상황 업데이트
	@Update("UPDATE requests SET reqProgress = #{reqProgress} WHERE reqCode = #{reqCode};")
	int updateRequestProgress(@Param("reqCode") int reqCode, @Param("reqProgress") String reqProgress);

	// 심사 탈락 처리
	@Update("UPDATE requests SET reqProgress = '심사 탈락' WHERE reqCode = #{reqCode};")
	int rejectRequestProgress(@Param("reqCode") int reqCode);

	// 위탁 철회 요청 승인 처리
	@Update("UPDATE requests SET reqProgress = '철회 진행 중' WHERE reqCode = #{reqCode};")
	int cancelRequestProgress(@Param("reqCode") int reqCode);

	// 위탁 철회 요청 거부 처리
	@Update("UPDATE requests SET reqProgress = '완료' WHERE reqCode = #{reqCode};")
	int rejectCancelRequestProgress(@Param("reqCode") int reqCode);
}
