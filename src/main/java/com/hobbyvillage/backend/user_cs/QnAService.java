package com.hobbyvillage.backend.user_cs;

import java.util.List;

public interface QnAService {
	int getQuestionCount(String filter, String email);
	int getSearchQuestionCount(String filter, String email, String keyword);
	List<QuestionDTO> getQuestionList(String filter, String email, int pageNum);
	List<QuestionDTO> getSearchQuestionList(String filter, String email, String keyword, int pageNum);
	int writerCheck(String email, int qstCode);
	QuestionDTO getQuestionDetail(int qstCode);
	String getAnswerDetail(int qstCode);
}
