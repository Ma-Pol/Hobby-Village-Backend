package com.hobbyvillage.backend.admin_qna;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminQuestionsServiceImpl implements AdminQuestionsService {

	private AdminQuestionsMapper mapper;

	public AdminQuestionsServiceImpl(AdminQuestionsMapper mapper) {
		this.mapper = mapper;
	}

	// 필터 조건에 따른 쿼리문 설정 메서드
	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "qstCategory IS NOT NULL";

		} else if (filter.equals("product")) {
			filter = "qstCategory = '상품 문의'";

		} else if (filter.equals("login-about")) {
			filter = "qstCategory = '로그인/정보'";

		} else if (filter.equals("sell-consign")) {
			filter = "qstCategory = '판매/위탁'";

		} else if (filter.equals("payment")) {
			filter = "qstCategory = '결제'";

		} else if (filter.equals("shipping")) {
			filter = "qstCategory = '배송 문의'";

		} else {
			filter = "qstCategory = '기타'";
		}

		return filter;
	}

	@Override // 미검색 상태에서 질문 개수 조회
	public int getQuestionCount(String filter) {
		filter = filtering(filter);

		return mapper.getQuestionCount(filter);
	}

	@Override // 검색 상태에서 질문 개수 조회
	public int getSearchQuestionCount(String filter, String condition, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchQuestionCount(filter, condition, keyword);
	}

	@Override // 미검색 상태에서 질문 목록 조회
	public List<AdminQustionsDTO> getQuestionList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getQuestionList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 질문 목록 조회
	public List<AdminQustionsDTO> getSearchquestionList(String filter, String condition, String keyword, String sort,
			int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchQuestionList(filter, condition, keyword, sort, pageNum);
	}

	@Override
	public int checkQuestion(int qstCode) {
		return mapper.checkQuestion(qstCode);
	}

	@Override
	public int checkModifyQuestion(int qstCode) {
		return mapper.checkModifyQuestion(qstCode);
	}

	@Override
	public AdminQustionsDTO getQuestionDetail(int qstCode) {
		return mapper.getQuestionDetail(qstCode);
	}

	@Override
	public String getAnswer(int qstCode) {
		return mapper.getAnswer(qstCode);
	}

	@Override
	public int deleteQuestion(int qstCode) {
		return mapper.deleteQuestion(qstCode);
	}

	@Override
	public int createAnswer(int qstCode, String aswContent) {
		int result = mapper.createAnswer(qstCode, aswContent);

		if (result == 1) {
			result = mapper.changeQuestionState(qstCode);
		}

		return result;
	}

	@Override
	public int modifyAnswer(int qstCode, String aswContent) {
		return mapper.modifyAnswer(qstCode, aswContent);
	}

}
