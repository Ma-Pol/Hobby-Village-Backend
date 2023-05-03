package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminRequestsMapper {
	@Select("SELECT COUNT(*) FROM requests WHERE ${filter} AND ${category};")
	int getRequestCount(@Param("filter") String filter, @Param("category") String category);

	@Select("SELECT COUNT(*) FROM requests r INNER JOIN users u ON r.reqEmail = u.email "
			+ "WHERE ${filter} AND ${category} AND ${condition} LIKE '%${keyword}%';")
	int getSearchRequestCount(@Param("filter") String filter, @Param("category") String category,
			@Param("condition") String condition, @Param("keyword") String keyword);

	@Select("SELECT r.reqCode, r.reqSort, r.reqCategory, r.reqTitle, r.reqProgress, u.userCode, u.nickname "
			+ "FROM requests r INNER JOIN users u ON r.reqEmail = u.email "
			+ "WHERE ${filter} AND ${category} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminRequestsDTO> getRequestList(@Param("filter") String filter, @Param("category") String category,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	@Select("SELECT r.reqCode, r.reqSort, r.reqCategory, r.reqTitle, r.reqProgress, u.userCode, u.nickname "
			+ "FROM requests r INNER JOIN users u ON r.reqEmail = u.email "
			+ "WHERE ${filter} AND ${category} AND ${condition} LIKE '%${keyword}%' "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminRequestsDTO> getSearchRequestList(@Param("filter") String filter, @Param("category") String category,
			@Param("condition") String condition, @Param("keyword") String keyword, @Param("sort") String sort,
			@Param("pageNum") int pageNum);
}
