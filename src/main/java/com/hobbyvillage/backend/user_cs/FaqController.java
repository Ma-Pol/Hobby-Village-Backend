package com.hobbyvillage.backend.user_cs;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cs/faq")
public class FaqController {

	private FaqServiceImpl FaqServiceImpl;

	public FaqController(FaqServiceImpl FaqServiceImpl) {
		this.FaqServiceImpl = FaqServiceImpl;
	}

	@GetMapping("/count")
	public int getFaqCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int faqCount;

		// 검색 여부 확인
		if (keyword == null) {
			faqCount = FaqServiceImpl.getFaqCount(filter);
		} else {
			faqCount = FaqServiceImpl.getSearchFaqCount(filter, keyword);
		}

		return faqCount;
	}

	@GetMapping("")
	public List<FaqDTO> getFaqList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<FaqDTO> faqList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			faqList = FaqServiceImpl.getFaqList(filter, pageNum);
		} else {
			faqList = FaqServiceImpl.getSearchFaqList(filter, keyword, pageNum);
		}

		return faqList;
	}

	@GetMapping("/{faqCode}")
	public FaqDTO getFaqDetail(@PathVariable Integer faqCode) {
		return FaqServiceImpl.getFaqDetail(faqCode);
	}
}