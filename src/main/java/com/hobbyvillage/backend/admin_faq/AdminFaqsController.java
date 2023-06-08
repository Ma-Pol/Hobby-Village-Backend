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

		// 검색 여부 확인
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

		// 검색 여부 확인
		if (keyword == null) {
			faqList = adminFaqsServiceImpl.getFaqList(filter, sort, pageNum);
		} else {
			faqList = adminFaqsServiceImpl.getSearchFaqList(filter, keyword, sort, pageNum);
		}

		return faqList;
	}

	// 실재 FAQ인지 확인
	@GetMapping("/check/{faqCode}")
	public int checkFaq(@PathVariable(value = "faqCode", required = true) String faqCode) {
		int result = 0;

		if (faqCode.matches("-?\\d+")) {
			int faqCodeInt = Integer.parseInt(faqCode);
			result = adminFaqsServiceImpl.checkFaq(faqCodeInt);
		}

		return result;
	}

	// FAQ 상세 조회
	@GetMapping("/faqDetails/{faqCode}")
	public AdminFaqsDTO getFaqDetail(@PathVariable(value = "faqCode", required = true) int faqCode) {
		return adminFaqsServiceImpl.getFaqDetail(faqCode);
	}

	// FAQ 삭제
	@DeleteMapping("/delete/{faqCode}")
	public int deleteFaq(@PathVariable(value = "faqCode", required = true) int faqCode) {
		return adminFaqsServiceImpl.deleteFaq(faqCode);
	}

	// FAQ 등록
	@PostMapping("/create")
	public int createFaq(@RequestBody AdminFaqsDTO faq) {
		return adminFaqsServiceImpl.createFaq(faq);
	}

	// FAQ 수정
	@PatchMapping("/modify")
	public int modifyFaq(@RequestBody AdminFaqsDTO faq) {
		return adminFaqsServiceImpl.modifyFaq(faq);
	}
}
