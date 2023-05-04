package com.hobbyvillage.backend.admin_reviews;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/reviews")
public class AdminReviewsController {

	private AdminReviewsServiceImpl adminReviewsServiceImpl;

	public AdminReviewsController(AdminReviewsServiceImpl adminReviewsServiceImpl) {
		this.adminReviewsServiceImpl = adminReviewsServiceImpl;
	}

	@GetMapping("/count")
	public int getProductCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int reviewCount;

		// 검색 여부 확인
		if (keyword == null) {
			reviewCount = adminReviewsServiceImpl.getReviewCount(filter);
		} else {
			reviewCount = adminReviewsServiceImpl.getSearchReviewCount(filter, condition, keyword);
		}

		return reviewCount;
	}

	@GetMapping("")
	public List<AdminReviewsDTO> getReviewList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminReviewsDTO> reviewList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			reviewList = adminReviewsServiceImpl.getReviewList(filter, sort, pageNum);
		} else {
			reviewList = adminReviewsServiceImpl.getSearchReviewList(filter, condition, keyword, sort, pageNum);
		}

		return reviewList;
	}
}
