package com.hobbyvillage.backend.user_products;

import java.util.List;

public interface UserProductsService {
	List<String> getCategories();
	// ----------------------------
	int getProductCount(String category, String sort);
	int getSearchProductCount(String category, String sort, String keyword);
	// ----------------------------
	// 상품 목록 조회 - 검색x & 평점순
	List<UserProductsDTO> getProductListRR(String category, String sort, int pageNum);
	// 상품 목록 조회 - 검색x & not 평점순
	List<UserProductsDTO> getProductList(String category, String sort, String array, int pageNum);
	// 상품 목록 조회 - 검색o & 평점순
	List<UserProductsDTO> getProductListSRR(String category, String sort, String keyword, int pageNum);
	// 상품 목록 조회 - 검색o & not 평점순
	List<UserProductsDTO> getProductListS(String category, String sort, String array, String keyword, int pageNum);
	// ----------------------------
	List<String> getProdPictures(String prodCode);
}
