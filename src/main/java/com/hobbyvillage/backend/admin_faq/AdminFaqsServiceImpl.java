package com.hobbyvillage.backend.admin_faq;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminFaqsServiceImpl implements AdminFaqsService {

	private AdminFaqsMapper mapper;

	public AdminFaqsServiceImpl(AdminFaqsMapper mapper) {
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
	public List<AdminFaqsDTO> getFaqList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getFaqList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 FAQ 목록 조회
	public List<AdminFaqsDTO> getSearchFaqList(String filter, String keyword, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchFaqList(filter, keyword, sort, pageNum);
	}

	@Override // 실재 FAQ 인지 확인
	public int checkFaq(int faqCode) {
		return mapper.checkFaq(faqCode);
	}

	@Override // FAQ 상세 조회
	public AdminFaqsDTO getFaqDetail(int faqCode) {
		return mapper.getFaqDetail(faqCode);
	}

	@Override // FAQ 삭제
	public int deleteFaq(int faqCode) {
		return mapper.deleteFaq(faqCode);
	}

	@Override // FAQ 등록
	public int createFaq(AdminFaqsDTO faq) {
		int result = -1;
		int check = mapper.checkDuplication(faq);

		if (check == 0) {
			result = mapper.createFaq(faq);
			if (result == 1) {
				result = mapper.getFaqCode(faq);
			}
		}

		return result;
	}

	@Override // FAQ 수정
	public int modifyFaq(AdminFaqsDTO faq) {
		return mapper.modifyFaq(faq);
	}

}
