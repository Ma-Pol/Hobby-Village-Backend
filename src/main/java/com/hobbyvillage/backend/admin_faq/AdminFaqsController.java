package com.hobbyvillage.backend.admin_faq;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/faqs")
public class AdminFaqsController {

	private AdminFaqsServiceImpl adminFaqsServiceImpl;

	public AdminFaqsController(AdminFaqsServiceImpl adminFaqsServiceImpl) {
		this.adminFaqsServiceImpl = adminFaqsServiceImpl;
	}

	@GetMapping("/count")
	public int getFaqCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int faqCount;

		if (keyword == null) {
			faqCount = adminFaqsServiceImpl.getFaqCount(filter);
		} else {
			faqCount = adminFaqsServiceImpl.getSearchFaqCount(filter, keyword);
		}

		return faqCount;
	}

	@GetMapping("")
	public List<AdminFaqsDTO> getFaqList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminFaqsDTO> faqList;
		int pageNum = (pages - 1) * 10;

		if (keyword == null) {
			faqList = adminFaqsServiceImpl.getFaqList(filter, sort, pageNum);
		} else {
			faqList = adminFaqsServiceImpl.getSearchFaqList(filter, keyword, sort, pageNum);
		}

		return faqList;
	}

}
