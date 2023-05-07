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
}
