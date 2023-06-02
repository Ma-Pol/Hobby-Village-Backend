package com.hobbyvillage.backend.stats;

import java.util.List;

public interface AdminStatsService {
	int getTodayOrderPrice();
	int getTodayOrderCount();
	List<MonthlyOrderDTO> getMonthlyOrders();
	List<MonthlyRequestDTO> getMonthlyRequests();
	List<MonthlyReviewDTO> getMonthlyReviews();
}
