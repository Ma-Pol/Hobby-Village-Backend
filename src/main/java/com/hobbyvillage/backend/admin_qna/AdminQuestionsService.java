package com.hobbyvillage.backend.admin_qna;

import java.util.List;

public interface AdminQuestionsService {
	int getQuestionCount(String filter);
	int getSearchQuestionCount(String filter, String condition, String keyword);
	List<AdminQustionsDTO> getQuestionList(String filter, String sort, int pageNum);
	List<AdminQustionsDTO> getSearchquestionList(String filter, String condition, String keyword, String sort, int pageNum);
}
