package com.hobbyvillage.backend.user_cs;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cs/qna")
public class QnAController {

	private QnAServiceImpl qnAServiceImpl;

	public QnAController(QnAServiceImpl qnAServiceImpl) {
		this.qnAServiceImpl = qnAServiceImpl;
	}

	@GetMapping("/{email}/count")
	public int getQuestionCount(@PathVariable(value = "email", required = true) String email,
			@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int questionCount;

		// 검색 여부 확인
		if (keyword == null) {
			questionCount = qnAServiceImpl.getQuestionCount(filter, email);
		} else {
			questionCount = qnAServiceImpl.getSearchQuestionCount(filter, email, keyword);
		}

		return questionCount;
	}

	@GetMapping("/{email}")
	public List<QuestionDTO> getQuestionList(@PathVariable(value = "email", required = true) String email,
			@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<QuestionDTO> questionList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			questionList = qnAServiceImpl.getQuestionList(filter, email, pageNum);
		} else {
			questionList = qnAServiceImpl.getSearchQuestionList(filter, email, keyword, pageNum);
		}

		return questionList;
	}

	@GetMapping("/{email}/{qstCode}/writerCheck")
	public int writerCheck(@PathVariable(value = "email", required = true) String email,
			@PathVariable(value = "qstCode", required = true) int qstCode) {
		return qnAServiceImpl.writerCheck(email, qstCode);
	}

	@GetMapping("/{email}/{qstCode}")
	public QuestionDTO getQuestionDetail(@PathVariable int qstCode) {
		return qnAServiceImpl.getQuestionDetail(qstCode);
	}

	@GetMapping("/answer/{qstCode}")
	public String getAnswerDetail(@PathVariable int qstCode) {
		return qnAServiceImpl.getAnswerDetail(qstCode);
	}
}
