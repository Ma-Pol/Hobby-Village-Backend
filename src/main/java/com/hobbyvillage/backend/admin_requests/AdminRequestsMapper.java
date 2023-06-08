package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminRequestsMapper {
	// 미검색 상태에서 신청 수 조회
	@Select("SELECT COUNT(*) FROM requests WHERE ${filter} AND ${category};")
	int getRequestCount(@Param("filter") String filter, @Param("category") String category);

	// 검색 상태에서 신청 수 조회
	@Select("SELECT COUNT(*) FROM requests r INNER JOIN users u ON r.reqEmail = u.email "
			+ "WHERE ${filter} AND ${category} AND ${condition} LIKE '%${keyword}%';")
	int getSearchRequestCount(@Param("filter") String filter, @Param("category") String category,
			@Param("condition") String condition, @Param("keyword") String keyword);

	// 미검색 상태에서 신청 목록 조회
	@Select("SELECT r.reqCode, r.reqSort, r.reqCategory, r.reqTitle, r.reqProgress, u.userCode, u.nickname "
			+ "FROM requests r INNER JOIN users u ON r.reqEmail = u.email "
			+ "WHERE ${filter} AND ${category} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminRequestsDTO> getRequestList(@Param("filter") String filter, @Param("category") String category,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 검색 상태에서 신청 목록 조회
	@Select("SELECT r.reqCode, r.reqSort, r.reqCategory, r.reqTitle, r.reqProgress, u.userCode, u.nickname "
			+ "FROM requests r INNER JOIN users u ON r.reqEmail = u.email "
			+ "WHERE ${filter} AND ${category} AND ${condition} LIKE '%${keyword}%' "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminRequestsDTO> getSearchRequestList(@Param("filter") String filter, @Param("category") String category,
			@Param("condition") String condition, @Param("keyword") String keyword, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	@Select("SELECT COUNT(*) FROM requests WHERE reqCode = #{reqCode};")
	int checkReqeust(@Param("reqCode") int reqCode);

	// 신청 상세 정보 조회
	@Select("SELECT rq.reqCode, rq.reqSort, rq.reqEmail, rq.reqCategory, rq.reqTitle, rq.reqContent, rq.reqProgress, rq.rejectReason, "
			+ "u.nickname, u.phone FROM requests rq INNER JOIN users u ON rq.reqEmail = u.email WHERE rq.reqCode = #{reqCode};")
	AdminRequestsDetailsDTO getRequestDetail(@Param("reqCode") int reqCode);

	// 신청 파일명 조회
	@Select("SELECT reqFile FROM requestFiles WHERE reqCode = #{reqCode};")
	List<String> getRequestFileList(@Param("reqCode") int reqCode);

	// 신청 진행 상황 업데이트
	@Update("UPDATE requests SET reqProgress = #{reqProgress} WHERE reqCode = #{reqCode};")
	int updateRequestProgress(@Param("reqCode") int reqCode, @Param("reqProgress") String reqProgress);

	// 물품의 대여 여부 확인
	@Select("SELECT p.prodCode FROM requests r INNER JOIN products p ON r.reqCode = p.reqCode WHERE r.reqCode = #{reqCode} AND p.prodIsRental = 0;")
	String checkIsRental(@Param("reqCode") int reqCode);

	// 미대여 상태일 때 상품 삭제 처리
	// 상품 삭제 1: 상품의 상태를 삭제된 상태로 변경
	@Update("UPDATE products SET prodDeleted = 1 WHERE prodCode = #{prodCode};")
	void deleteProduct(@Param("prodCode") String prodCode);

	// 상품 삭제 2: 장바구니에서 상품 삭제
	@Delete("DELETE FROM carts WHERE prodCode = #{prodCode};")
	void deleteCart(@Param("prodCode") String prodCode);

	// 상품 삭제 3: 찜 목록에서 상품 삭제
	@Delete("DELETE FROM dibs WHERE prodCode = #{prodCode};")
	void deleteDib(@Param("prodCode") String prodCode);

	// 상품 삭제 4: 리뷰 이미지 파일 조회(삭제용)
	@Select("SELECT rp.revwPicture FROM reviewPictures rp INNER JOIN reviews r "
			+ "ON rp.revwCode = r.revwCode WHERE r.prodCode = #{prodCode};")
	List<String> getReviewImage(@Param("prodCode") String prodCode);

	// 상품 삭제 5: 리뷰 목록에서 상품 삭제
	@Delete("DELETE FROM reviews WHERE prodCode = #{prodCode};")
	void deleteReviews(@Param("prodCode") String prodCode);

	// 심사 탈락 처리
	@Update("UPDATE requests SET reqProgress = '심사 탈락', rejectReason = #{rejectReason} WHERE reqCode = #{reqCode};")
	int rejectRequestProgress(AdminRequestsRejectDTO rejectData);

	// 위탁 철회 요청 거부 처리
	@Update("UPDATE requests SET reqProgress = '완료' WHERE reqCode = #{reqCode};")
	int rejectCancelRequestProgress(@Param("reqCode") int reqCode);
}
