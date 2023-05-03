package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminRequestsServiceImpl implements AdminRequestsService {

	private AdminRequestsMapper mapper;

	public AdminRequestsServiceImpl(AdminRequestsMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "reqProgress IS NOT NULL";
		} else if (filter.equals("1st-assessing")) {
			filter = "reqProgress = '1차 심사 중'";
		} else if (filter.equals("waiting-2nd-assess")) {
			filter = "reqProgress = '2차 심사 대기'";
		} else if (filter.equals("2nd-assessing")) {
			filter = "reqProgress = '2차 심사 중'";
		} else if (filter.equals("completed")) {
			filter = "reqProgress = '완료'";
		} else if (filter.equals("failed")) {
			filter = "reqProgress = '심사 탈락'";
		} else if (filter.equals("cancel-request")) {
			filter = "reqProgress = '위탁 철회 요청'";
		} else if (filter.equals("canceling")) {
			filter = "reqProgress = '철회 진행 중'";
		} else {
			filter = "reqProgress = '철회 완료'";
		}

		return filter;
	}

	private String categorizing(String category) {
		if (category.equals("sell")) {
			category = "reqSort = '판매'";
		} else {
			category = "reqSort = '위탁'";
		}

		return category;
	}

	@Override
	public int getRequestCount(String filter, String category) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getRequestCount(filter, category);
	}

	@Override
	public int getSearchRequestCount(String filter, String category, String condition, String keyword) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getSearchRequestCount(filter, category, condition, keyword);
	}

	@Override
	public List<AdminRequestsDTO> getRequestList(String filter, String category, String sort, int pageNum) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getRequestList(filter, category, sort, pageNum);
	}

	@Override
	public List<AdminRequestsDTO> getSearchRequestList(String filter, String category, String condition, String keyword,
			String sort, int pageNum) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getSearchRequestList(filter, category, condition, keyword, sort, pageNum);
	}

}
