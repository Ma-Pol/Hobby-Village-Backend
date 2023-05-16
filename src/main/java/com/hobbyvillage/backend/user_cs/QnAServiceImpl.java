package com.hobbyvillage.backend.user_cs;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QnAServiceImpl implements QnAService {

	private QnAMapper mapper;

	public QnAServiceImpl(QnAMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "q.qstCategory IS NOT NULL";

		} else if (filter.equals("product")) {
			filter = "q.qstCategory = '상품 문의'";

		} else if (filter.equals("login-about")) {
			filter = "q.qstCategory = '로그인/정보'";

		} else if (filter.equals("sell-consign")) {
			filter = "q.qstCategory = '판매/위탁'";

		} else if (filter.equals("payment")) {
			filter = "q.qstCategory = '결제'";

		} else if (filter.equals("shipping")) {
			filter = "q.qstCategory = '배송 문의'";

		} else {
			filter = "q.qstCategory = '기타'";
		}

		return filter;
	}

	@Override
	public int getQuestionCount(String filter, String email) {
		filter = filtering(filter);

		return mapper.getQuestionCount(filter, email);
	}

	@Override
	public int getSearchQuestionCount(String filter, String email, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchQuestionCount(filter, email, keyword);
	}

	@Override
	public List<QuestionDTO> getQuestionList(String filter, String email, int pageNum) {
		filter = filtering(filter);

		return mapper.getQuestionList(filter, email, pageNum);
	}

	@Override
	public List<QuestionDTO> getSearchQuestionList(String filter, String email, String keyword, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchQuestionList(filter, email, keyword, pageNum);
	}

	@Override
	public int writerCheck(String email, int qstCode) {
		return mapper.writerCheck(email, qstCode);
	}

	@Override
	public QuestionDTO getQuestionDetail(int qstCode) {
		return mapper.getQuestionDetail(qstCode);
	}

	@Override
	public String getAnswerDetail(int qstCode) {
		return mapper.getAnswerDetail(qstCode);
	}

	@Override
	public int insertQuestion(QuestionDTO question) {
		String nickname = mapper.getNickname(question.getEmail());
		question.setQstWriter(nickname);

		return mapper.insertQuestion(question);
	}

}
