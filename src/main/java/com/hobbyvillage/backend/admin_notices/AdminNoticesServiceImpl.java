package com.hobbyvillage.backend.admin_notices;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminNoticesServiceImpl implements AdminNoticesService {

	private AdminNoticesMapper mapper;

	public AdminNoticesServiceImpl(AdminNoticesMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "notCategory IS NOT NULL";
		} else if (filter.equals("info")) {
			filter = "notCategory = '안내'";
		} else {
			filter = "notCategory = '이벤트'";
		}

		return filter;
	}

	@Override
	public int getNoticeCount(String filter) {
		filter = filtering(filter);

		return mapper.getNoticeCount(filter);
	}

	@Override
	public int getSearchNoticeCount(String filter, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchNoticeCount(filter, keyword);
	}

	@Override
	public List<AdminNoticesDTO> getNoticeList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getNoticeList(filter, sort, pageNum);
	}

	@Override
	public List<AdminNoticesDTO> getSearchNoticeList(String filter, String keyword, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchNoticeList(filter, keyword, sort, pageNum);
	}

}
