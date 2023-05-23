package com.hobbyvillage.backend.user_cs;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FaqServiceImpl implements FaqService {

	private FaqMapper mapper;

	public FaqServiceImpl(FaqMapper mapper) {
		this.mapper = mapper;
	}

	// 필터 조건에 따른 쿼리문 설정 메서드
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

	@Override // 미검색 상태에서 FAQ 개수 조회
	public int getFaqCount(String filter) {
		filter = filtering(filter);

		return mapper.getFaqCount(filter);
	}

	@Override // 검색 상태에서 FAQ 개수 조회
	public int getSearchFaqCount(String filter, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchFaqCount(filter, keyword);
	}

	@Override // 미검색 상태에서 FAQ 목록 조회
	public List<FaqDTO> getFaqList(String filter, int pageNum) {
		filter = filtering(filter);

		return mapper.getFaqList(filter, pageNum);
	}

	@Override // 검색 상태에서 FAQ 목록 조회
	public List<FaqDTO> getSearchFaqList(String filter, String keyword, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchFaqList(filter, keyword, pageNum);
	}

	@Override
	public int checkFaq(int faqCode) {
		return mapper.checkFaq(faqCode);
	}

	@Override
	public FaqDTO getFaqDetail(Integer faqCode) {
		return mapper.getFaqDetail(faqCode);
	}
}