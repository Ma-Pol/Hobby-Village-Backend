package com.hobbyvillage.backend.admin_qna;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/qnas")
public class AdminQuestionsController {

	private AdminQuestionsServiceImpl adminQuestionsServiceImpl;

	public AdminQuestionsController(AdminQuestionsServiceImpl adminQuestionsServiceImpl) {
		this.adminQuestionsServiceImpl = adminQuestionsServiceImpl;
	}

	@GetMapping("/count")
	public int getQuestionCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int questionCount;

		// 검색 여부 확인
		if (keyword == null) {
			questionCount = adminQuestionsServiceImpl.getQuestionCount(filter);
		} else {
			questionCount = adminQuestionsServiceImpl.getSearchQuestionCount(filter, condition, keyword);
		}

		return questionCount;
	}

	@GetMapping("")
	public List<AdminQustionsDTO> getQuestionList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminQustionsDTO> questionList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			questionList = adminQuestionsServiceImpl.getQuestionList(filter, sort, pageNum);
		} else {
			questionList = adminQuestionsServiceImpl.getSearchquestionList(filter, condition, keyword, sort, pageNum);
		}

		return questionList;
	}

}
