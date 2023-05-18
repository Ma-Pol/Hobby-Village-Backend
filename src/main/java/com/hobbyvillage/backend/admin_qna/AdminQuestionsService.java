package com.hobbyvillage.backend.admin_qna;

import java.util.List;

public interface AdminQuestionsService {
	int getQuestionCount(String filter);
	int getSearchQuestionCount(String filter, String condition, String keyword);
	List<AdminQustionsDTO> getQuestionList(String filter, String sort, int pageNum);
	List<AdminQustionsDTO> getSearchquestionList(String filter, String condition, String keyword, String sort, int pageNum);
	int checkQuestion(int qstCode);
	AdminQustionsDTO getQuestionDetail(int qstCode);
	String getAnswer(int qstCode);
	int deleteQuestion(int qstCode);
	int createAnswer(int qstCode, String aswContent);
	int modifyAnswer(int qstCode, String aswContent);
}
