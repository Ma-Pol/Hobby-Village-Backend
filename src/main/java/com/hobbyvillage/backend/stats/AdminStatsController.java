package com.hobbyvillage.backend.stats;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/stats")
public class AdminStatsController {

	private AdminStatsServiceImpl adminStatsServiceImpl;

	public AdminStatsController(AdminStatsServiceImpl adminStatsServiceImpl) {
		this.adminStatsServiceImpl = adminStatsServiceImpl;
	}

	// 오늘 총 주문 금액 조회
	@GetMapping("/today-order-price")
	public int getTodayOrderPrice() {
		return adminStatsServiceImpl.getTodayOrderPrice();
	}

	// 오늘 총 주문 건수 조회
	@GetMapping("/today-order-count")
	public int getTodayOrderCount() {
		return adminStatsServiceImpl.getTodayOrderCount();
	}

	// 월별 일반+추가 주문 현황
	@GetMapping("/monthly-order")
	public List<MonthlyOrderDTO> getMonthlyOrders() {
		return adminStatsServiceImpl.getMonthlyOrders();
	}

	// 월별 판매/위탁 신청 현황
	@GetMapping("/monthly-request")
	public List<MonthlyRequestDTO> getMonthlyRequests() {
		return adminStatsServiceImpl.getMonthlyRequests();
	}

	// 월별 리뷰 현황 조회
	@GetMapping("/monthly-review")
	public List<MonthlyReviewDTO> getMonthlyReviews() {
		return adminStatsServiceImpl.getMonthlyReviews();
	}

	// 월별 가입/탈퇴 회원 수 현황 조회
	@GetMapping("/monthly-user")
	public List<MonthlyUserDTO> getMonthlyUsers() {
		return adminStatsServiceImpl.getMonthlyUsers();
	}
}
