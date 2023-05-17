package com.hobbyvillage.backend.admin_qna;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminQuestionsMapper {
	// 미검색 상태에서 질문 개수 조회
	@Select("SELECT COUNT(*) FROM questions WHERE ${filter};")
	int getQuestionCount(@Param("filter") String filter);

	// 검색 상태에서 질문 개수 조회(검색 대상 Like '%검색 키워드%')
	@Select("SELECT COUNT(*) FROM questions WHERE ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchQuestionCount(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword);

	// 미검색 상태에서 질문 목록 조회(sort 기준 정렬)
	@Select("SELECT q.qstCode, q.qstCategory, q.qstTitle, q.qstWriter, q.qstDate, q.qstState, u.userCode "
			+ "FROM questions q INNER JOIN users u ON q.qstWriter = u.nickname "
			+ "WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminQustionsDTO> getQuestionList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 질문 목록 조회(검색 대상 Like '%검색 키워드%', sort 기준 정렬)
	@Select("SELECT q.qstCode, q.qstCategory, q.qstTitle, q.qstWriter, q.qstDate, q.qstState, u.userCode "
			+ "FROM questions q INNER JOIN users u ON q.qstWriter = u.nickname "
			+ "WHERE ${filter} AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminQustionsDTO> getSearchQuestionList(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);
}
