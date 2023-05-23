package com.hobbyvillage.backend.user_cs;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface QnAMapper {
	// 미검색 상태에서 질문 개수 조회
	@Select("SELECT COUNT(*) FROM questions q INNER JOIN users u "
			+ "ON q.qstWriter = u.nickname WHERE ${filter} AND u.email = #{email};")
	int getQuestionCount(@Param("filter") String filter, @Param("email") String email);

	// 검색 상태에서 질문 개수 조회
	@Select("SELECT COUNT(*) FROM questions q INNER JOIN users u "
			+ "ON q.qstWriter = u.nickname WHERE ${filter} AND u.email = #{email} AND q.qstTitle LIKE '%${keyword}%';")
	int getSearchQuestionCount(@Param("filter") String filter, @Param("email") String email,
			@Param("keyword") String keyword);

	// 미검색 상태에서 질문 목록 조회
	@Select("SELECT q.qstCode, q.qstTitle, q.qstContent, q.qstCategory, q.qstDate, q.qstState FROM questions q "
			+ "INNER JOIN users u ON q.qstWriter = u.nickname WHERE ${filter} AND u.email = #{email} "
			+ "ORDER BY -q.qstCode LIMIT #{pageNum}, 10;")
	List<QuestionDTO> getQuestionList(@Param("filter") String fitler, @Param("email") String email,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 질문 목록 조회
	@Select("SELECT q.qstCode, q.qstTitle, q.qstContent, q.qstCategory, q.qstDate, q.qstState FROM questions q "
			+ "INNER JOIN users u ON q.qstWriter = u.nickname WHERE ${filter} AND u.email = #{email} "
			+ "AND q.qstTitle LIKE '%${keyword}%' ORDER BY -q.qstCode LIMIT #{pageNum}, 10;")
	List<QuestionDTO> getSearchQuestionList(@Param("filter") String fitler, @Param("email") String email,
			@Param("keyword") String keyword, @Param("pageNum") int pageNum);

	@Select("SELECT COUNT(*) FROM questions WHERE qstCode = #{qstCode};")
	int checkQst(@Param("qstCode") int qstCode);

	// 질문 작성자 확인
	@Select("SELECT COUNT(*) FROM questions q INNER JOIN users u ON q.qstWriter = u.nickname "
			+ "WHERE u.email = #{email} AND qstCode = #{qstCode};")
	int writerCheck(@Param("email") String email, @Param("qstCode") int qstCode);

	// 질문 상세 조회
	@Select("SELECT qstTitle, qstContent, qstCategory, qstState FROM questions WHERE qstCode = #{qstCode};")
	QuestionDTO getQuestionDetail(@Param("qstCode") int qstCode);

	// 답변 상세 조회
	@Select("SELECT aswContent FROM answers WHERE qstCode = #{qstCode};")
	String getAnswerDetail(@Param("qstCode") int qstCode);

	// 작성자 닉네임 확인
	@Select("SELECT nickname FROM users WHERE email = #{email};")
	String getNickname(@Param("email") String email);

	// 질문 등록
	@Insert("INSERT INTO questions(qstCategory, qstTitle, qstContent, qstWriter, qstState) VALUES"
			+ "(#{qstCategory}, #{qstTitle}, #{qstContent}, #{qstWriter}, 0);")
	int insertQuestion(QuestionDTO question);
}
