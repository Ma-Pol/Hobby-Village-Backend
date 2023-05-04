package com.hobbyvillage.backend.admin_products;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminProductsServiceImpl implements AdminProductsService {

	private AdminProductsMapper mapper;

	public AdminProductsServiceImpl(AdminProductsMapper mapper) {
		this.mapper = mapper;
	}

	// 필터 조건에 따른 쿼리문 설정 메서드
	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "prodIsRental IS NOT NULL";
		} else if (filter.equals("rented")) {
			filter = "prodIsRental = 1";
		} else {
			filter = "prodIsRental = 0";
		}

		return filter;
	}

	@Override // 미검색 상태에서 상품 개수 조회
	public int getProductCount(String filter) {
		filter = filtering(filter);

		return mapper.getProductCount(filter);
	}

	@Override // 검색 상태에서 상품 개수 조회
	public int getSearchProductCount(String filter, String condition, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchProductCount(filter, condition, keyword);
	}

	@Override // 미검색 상태에서 상품 목록 조회
	public List<AdminProductsDTO> getProductList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getProductList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 상품 목록 조회
	public List<AdminProductsDTO> getSearchProductList(String filter, String condition, String keyword, String sort,
			int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchProductList(filter, condition, keyword, sort, pageNum);
	}

}
