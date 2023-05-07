package com.hobbyvillage.backend.admin_orders;

import java.util.List;

public interface AdminOrdersService {
	int getOrderCount(String sort);
	int getSearchOrderCount(String sort, String condition, String keyword);
	List<AdminOrdersDTO> getOrderList(String sort, int pageNum);
	List<AdminOrdersDTO> getSearchOrderList(String condition, String keyword, String sort, int pageNum);
}
