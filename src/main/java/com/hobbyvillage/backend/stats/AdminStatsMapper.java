package com.hobbyvillage.backend.stats;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminStatsMapper {
	// 오늘 총 주문 금액 조회
	@Select("SELECT SUM(odr.exactPrice) AS todayOrderPrice FROM (SELECT exactPrice, odrDate FROM orders UNION ALL "
			+ "SELECT exactPrice, odrDate FROM exOrders) odr WHERE DATE_FORMAT(odr.odrDate, '%Y-%m-%d') = CURDATE();")
	Integer getTodayOrderPrice();

	// 오늘 총 주문 건수 조회
	@Select("SELECT COUNT(*) AS todayOrderCount FROM (SELECT exactPrice, odrDate FROM orders UNION ALL "
			+ "SELECT exactPrice, odrDate FROM exOrders) odr WHERE DATE_FORMAT(odr.odrDate, '%Y-%m-%d') = CURDATE();")
	Integer getTodayOrderCount();

	// 월별 일반 + 추가 주문 현황 조회
	@Select("SELECT o.month, IFNULL(o.orderCount, 0) AS orderCount, IFNULL(eo.exOrderCount, 0) AS exOrderCount "
			+ "FROM "
			+ "(SELECT DATE_FORMAT(odrDate, '%Y-%m') AS month, COUNT(*) AS orderCount FROM orders GROUP BY month) o "
			+ "LEFT JOIN "
			+ "(SELECT DATE_FORMAT(odrDate, '%Y-%m') AS month, COUNT(*) AS exOrderCount FROM exOrders GROUP BY month) eo "
			+ "ON o.month = eo.month "
			+ "UNION "
			+ "SELECT o.month, IFNULL(o.orderCount, 0) AS orderCount, IFNULL(eo.exOrderCount, 0) AS exOrderCount "
			+ "FROM "
			+ "(SELECT DATE_FORMAT(odrDate, '%Y-%m') AS month, COUNT(*) AS orderCount FROM orders GROUP BY month) o "
			+ "LEFT JOIN "
			+ "(SELECT DATE_FORMAT(odrDate, '%Y-%m') AS month, COUNT(*) AS exOrderCount FROM exOrders GROUP BY month) eo "
			+ "ON o.month = eo.month "
			+ "ORDER BY month ASC LIMIT 6;")
	List<MonthlyOrderDTO> getMonthlyOrders();

	// 월별 판매/위탁 신청 현황 조회
	@Select("SELECT s.month, IFNULL(s.sellRequestCount, 0) AS sellRequestCount, IFNULL(c.consignRequestCount, 0) AS consignRequestCount "
			+ "FROM "
			+ "(SELECT DATE_FORMAT(reqDate, '%Y-%m') AS month, COUNT(*) AS sellRequestCount FROM requests WHERE reqSort = '판매' GROUP BY month) s "
			+ "LEFT JOIN "
			+ "(SELECT DATE_FORMAT(reqDate, '%Y-%m') AS month, COUNT(*) AS consignRequestCount FROM requests WHERE reqSort = '위탁' GROUP BY month) c "
			+ "ON s.month = c.month "
			+ "UNION "
			+ "SELECT s.month, IFNULL(s.sellRequestCount, 0) AS sellRequestCount, IFNULL(c.consignRequestCount, 0) AS consignRequestCount "
			+ "FROM "
			+ "(SELECT DATE_FORMAT(reqDate, '%Y-%m') AS month, COUNT(*) AS sellRequestCount FROM requests WHERE reqSort = '판매' GROUP BY month) s "
			+ "RIGHT JOIN "
			+ "(SELECT DATE_FORMAT(reqDate, '%Y-%m') AS month, COUNT(*) AS consignRequestCount FROM requests WHERE reqSort = '위탁' GROUP BY month) c "
			+ "ON s.month = c.month ORDER BY month ASC LIMIT 6;")
	List<MonthlyRequestDTO> getMonthlyRequests();

	// 월별 리뷰 현황 조회
	@Select("SELECT DATE_FORMAT(revwRegiDate, '%Y-%m') AS month, COUNT(*) AS reviewCount FROM reviews GROUP BY month ORDER BY month ASC LIMIT 6;")
	List<MonthlyReviewDTO> getMonthlyReviews();

	// 월별 가입/탈퇴 회원 수 현황 조회
	@Select("SELECT j.month, IFNULL(j.joinCount, 0) AS joinCount, IFNULL(l.leaveCount, 0) AS leaveCount "
			+ "FROM "
			+ "(SELECT DATE_FORMAT(signUpDate, '%Y-%m') AS month, COUNT(*) AS joinCount FROM users WHERE userCode != 1 GROUP BY month) j "
			+ "LEFT JOIN "
			+ "(SELECT DATE_FORMAT(duDate, '%Y-%m') AS month, COUNT(*) AS leaveCount FROM deletedUserCount GROUP BY month) l "
			+ "ON j.month = l.month "
			+ "UNION "
			+ "SELECT j.month, IFNULL(j.joinCount, 0) AS joinCount, IFNULL(l.leaveCount, 0) AS leaveCount "
			+ "FROM "
			+ "(SELECT DATE_FORMAT(signUpDate, '%Y-%m') AS month, COUNT(*) AS joinCount FROM users WHERE userCode != 1 GROUP BY month) j "
			+ "RIGHT JOIN "
			+ "(SELECT DATE_FORMAT(duDate, '%Y-%m') AS month, COUNT(*) AS leaveCount FROM deletedUserCount GROUP BY month) l "
			+ "ON j.month = l.month ORDER BY month ASC LIMIT 6;")
	List<MonthlyUserDTO> getMonthlyUsers();
}
