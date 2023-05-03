package com.hobbyvillage.backend.admin_reviews;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminReviewsServiceImpl implements AdminReviewsService {

	private AdminReviewsMapper mapper;

	public AdminReviewsServiceImpl(AdminReviewsMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "revwReport >= 0";
		} else {
			filter = "revwReport >= 5";
		}

		return filter;
	}

	@Override
	public int getReviewCount(String filter) {
		filter = filtering(filter);

		return mapper.getReviewCount(filter);
	}

	@Override
	public int getSearchReviewCount(String filter, String condition, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchReviewCount(filter, condition, keyword);
	}

	@Override
	public List<AdminReviewsDTO> getReviewList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getReviewList(filter, sort, pageNum);
	}

	@Override
	public List<AdminReviewsDTO> getSearchReviewList(String filter, String condition, String keyword, String sort,
			int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchReviewList(filter, condition, keyword, sort, pageNum);
	}

}
