package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/orders")
public class AdminOrdersController {

	private AdminOrdersServiceImpl adminOrdersServiceImpl;

	public AdminOrdersController(AdminOrdersServiceImpl adminOrdersServiceImpl) {
		this.adminOrdersServiceImpl = adminOrdersServiceImpl;
	}

	@GetMapping("/count")
	public int getOrderCount(@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int orderCount;

		// 검색 여부 확인
		if (keyword == null) {
			orderCount = adminOrdersServiceImpl.getOrderCount(sort);
		} else {
			orderCount = adminOrdersServiceImpl.getSearchOrderCount(sort, condition, keyword);
		}

		return orderCount;
	}

	@GetMapping("")
	public List<AdminOrdersDTO> getOrderList(@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminOrdersDTO> orderList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			orderList = adminOrdersServiceImpl.getOrderList(sort, pageNum);
		} else {
			orderList = adminOrdersServiceImpl.getSearchOrderList(condition, keyword, sort, pageNum);
		}

		return orderList;
	}
}
