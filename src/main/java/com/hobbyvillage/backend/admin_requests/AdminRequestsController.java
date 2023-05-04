package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/requests")
public class AdminRequestsController {

	private AdminRequestsServiceImpl adminRequestsServiceImpl;

	public AdminRequestsController(AdminRequestsServiceImpl adminRequestsServiceImpl) {
		this.adminRequestsServiceImpl = adminRequestsServiceImpl;
	}

	@GetMapping("/{category}/count")
	public int getProductCount(@RequestParam(value = "filter", required = true) String filter,
			@PathVariable("category") String category,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int requestCount;

		// 검색 여부 확인
		if (keyword == null) {
			requestCount = adminRequestsServiceImpl.getRequestCount(filter, category);
		} else {
			requestCount = adminRequestsServiceImpl.getSearchRequestCount(filter, category, condition, keyword);
		}

		return requestCount;
	}

	@GetMapping("/{category}")
	public List<AdminRequestsDTO> getProductList(@RequestParam(value = "filter", required = true) String filter,
			@PathVariable("category") String category, @RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminRequestsDTO> requestList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			requestList = adminRequestsServiceImpl.getRequestList(filter, category, sort, pageNum);
		} else {
			requestList = adminRequestsServiceImpl.getSearchRequestList(filter, category, condition, keyword, sort,
					pageNum);
		}

		return requestList;
	}

}
