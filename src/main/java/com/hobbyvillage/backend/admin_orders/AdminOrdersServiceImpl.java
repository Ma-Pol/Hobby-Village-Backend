package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminOrdersServiceImpl implements AdminOrdersService {

	private AdminOrdersMapper mapper;

	public AdminOrdersServiceImpl(AdminOrdersMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public int getOrderCount(String sort) {
		int orderCount;

		if (sort.equals("-deadline")) {
			orderCount = mapper.getDeliveriedOrderCount();
		} else {
			orderCount = mapper.getAllOrderCount();
		}

		return orderCount;
	}

	@Override
	public int getSearchOrderCount(String sort, String condition, String keyword) {
		int orderCount;

		if (sort.equals("-deadline")) {
			orderCount = mapper.getSearchDeliveriedOrderCount(condition, keyword);
		} else {
			orderCount = mapper.getSearchAllOrderCount(condition, keyword);
		}

		return orderCount;
	}

	@Override
	public List<AdminOrdersDTO> getOrderList(String sort, int pageNum) {
		List<AdminOrdersDTO> orderList;

		if (sort.equals("-deadline")) {
			orderList = mapper.getDeliveriedOrderList(sort, pageNum);
		} else {
			orderList = mapper.getAllOrderList(sort, pageNum);
		}

		return orderList;
	}

	@Override
	public List<AdminOrdersDTO> getSearchOrderList(String condition, String keyword, String sort, int pageNum) {
		List<AdminOrdersDTO> orderList;

		if (sort.equals("-deadline")) {
			orderList = mapper.getSearchDeliveriedOrderList(condition, keyword, sort, pageNum);
		} else {
			orderList = mapper.getSearchAllOrderList(condition, keyword, sort, pageNum);
		}

		return orderList;
	}

}
