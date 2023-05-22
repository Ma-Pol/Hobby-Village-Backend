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
	

	@Override // 상품 상세 조회 - 리뷰 있음 
	public AdminProductsDTO getProductDetailWR(String prodCode) {
		AdminProductsDTO product = mapper.getProductDetailWR(prodCode);
		return product;
	}

	@Override // 상품 상세 조회 - 리뷰 없음 
	public AdminProductsDTO getProductDetail(String prodCode) {
		AdminProductsDTO product = new AdminProductsDTO();
		product = mapper.getProductDetail(prodCode);
		return product;
	}

	@Override // 상품 상세 조회 - 이미지
	public List<String> getProdPictures(String prodCode) {
		return mapper.getProdPictures(prodCode);
	}

	@Override // 상품 상세 조회 - 연관검색어
	public List<String> getProdTag(String prodCode) {
		List<String> tags = mapper.getProdTag(prodCode);
		return tags;
	}

	@Override // 브랜드 목록 조회 
	public List<String> getBrandList() {
		List<String> brands = mapper.getBrandList();
		return brands;
	}

	@Override // 카테고리 목록 조회 
	public List<String> getCategoryList() {
		List<String> categories = mapper.getCategoryList();
		return categories;
	}

	@Override // 상품 등록 
	public boolean addProduct(AdminProductsDTO products) {
		return mapper.addProduct(products);
	}
	
	@Override // 상품 등록 - 이미지 파일명 
	public void addPicture(String prodCode, String prodPicture) {
		mapper.addPicture(prodCode, prodPicture);
	}

	@Override // 상품 등록 - 연관검색어 
	public void addProductTags(AdminProductsDTO prodTags) {
		List<String> tags = prodTags.getProdTag();
		String prodCode = prodTags.getProdCode();
		for (String tag : tags) {			
			mapper.addProductTags(prodCode, tag);
		}
	}

	@Override // 상품 수정 
	public boolean modifyProduct(AdminProductsDTO products) {
		return mapper.modifyProduct(products);
	}

	@Override
	public boolean deleteProduct(String prodCode) {
		return mapper.deleteProduct(prodCode);
	}
	
	

}
