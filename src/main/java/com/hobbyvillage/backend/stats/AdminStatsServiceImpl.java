package com.hobbyvillage.backend.stats;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminStatsServiceImpl implements AdminStatsService {

	private AdminStatsMapper mapper;

	public AdminStatsServiceImpl(AdminStatsMapper mapper) {
		this.mapper = mapper;
	}

	// 오늘 총 주문 금액 조회
	@Override
	public int getTodayOrderPrice() {
		Integer priceInteger = mapper.getTodayOrderPrice();

		if (priceInteger == null) {
			priceInteger = 0;
		}

		return priceInteger;
	}

	// 오늘 총 주문 건수 조회
	@Override
	public int getTodayOrderCount() {
		Integer countInteger = mapper.getTodayOrderCount();

		if (countInteger == null) {
			countInteger = 0;
		}

		return countInteger;
	}

	// 월별 일반+추가 주문 현황 조회
	@Override
	public List<MonthlyOrderDTO> getMonthlyOrders() {
		return mapper.getMonthlyOrders();
	}

	// 월별 판매/위탁 신청 현황 조회
	@Override
	public List<MonthlyRequestDTO> getMonthlyRequests() {
		return mapper.getMonthlyRequests();
	}

	// 월별 리뷰 현황 조회
	@Override
	public List<MonthlyReviewDTO> getMonthlyReviews() {
		return mapper.getMonthlyReviews();
	}

}
