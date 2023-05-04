package com.hobbyvillage.backend.admin_faq;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminFaqsServiceImpl implements AdminFaqsService {

	private AdminFaqsMapper mapper;

	public AdminFaqsServiceImpl(AdminFaqsMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "faqCategory IS NOT NULL";
		} else if (filter.equals("product")) {
			filter = "faqCategory = '상품 문의'";
		} else if (filter.equals("login-about")) {
			filter = "faqCategory = '로그인/정보'";
		} else if (filter.equals("sell-consign")) {
			filter = "faqCategory = '판매/위탁'";
		} else if (filter.equals("payment")) {
			filter = "faqCategory = '결제'";
		} else if (filter.equals("shipping")) {
			filter = "faqCategory = '배송 문의'";
		} else {
			filter = "faqCategory = '기타'";
		}

		return filter;
	}

	@Override
	public int getFaqCount(String filter) {
		filter = filtering(filter);

		return mapper.getFaqCount(filter);
	}

	@Override
	public int getSearchFaqCount(String filter, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchFaqCount(filter, keyword);
	}

	@Override
	public List<AdminFaqsDTO> getFaqList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getFaqList(filter, sort, pageNum);
	}

	@Override
	public List<AdminFaqsDTO> getSearchFaqList(String filter, String keyword, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchFaqList(filter, keyword, sort, pageNum);
	}

}
