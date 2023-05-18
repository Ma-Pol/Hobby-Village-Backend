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

	// 질문 상세 조회 1: 해당 질문코드의 존재 여부 확인
	@Select("SELECT COUNT(*) FROM questions WHERE qstCode = #{qstCode};")
	int checkQuestion(@Param("qstCode") int qstCode);

	// 질문 상세 조회 2: 질문 내용 조회
	@Select("SELECT qstCategory, qstTitle, qstContent, qstState FROM questions WHERE qstCode = #{qstCode};")
	AdminQustionsDTO getQuestionDetail(@Param("qstCode") int qstCode);

	// 답변 상세 조회
	@Select("SELECT aswContent FROM answers WHERE qstCode = #{qstCode};")
	String getAnswer(@Param("qstCode") int qstCode);

	// 질문 삭제
	@Delete("DELETE FROM questions WHERE qstCode = #{qstCode};")
	int deleteQuestion(@Param("qstCode") int qstCode);

	// 답변 등록 1: 답변 테이블에 등록
	@Insert("INSERT INTO answers(qstCode, aswContent) VALUES(#{qstCode}, #{aswContent});")
	int createAnswer(@Param("qstCode") int qstCode, @Param("aswContent") String aswContent);

	// 답변 등록 2: 질문의 답변 상태 변경
	@Update("UPDATE questions SET qstState = 1 WHERE qstCode = #{qstCode};")
	int changeQuestionState(@Param("qstCode") int qstCode);

	// 답변 수정
	@Update("UPDATE answers SET aswContent = #{aswContent} WHERE qstCode = #{qstCode};")
	int modifyAnswer(@Param("qstCode") int qstCode, @Param("aswContent") String aswContent);
}
