package com.hobbyvillage.backend.user_products;

import java.util.List;

public interface UserProductsService {
	List<String> getCategories();
	List<String> getBrands();
	// ----------------------------
	int getProductCount(String category, String sort);
	int getSearchProductCount(String category, String sort, String keyword);
	int getBrandProductCount(String brand, String sort);
	// ----------------------------
	// 상품 목록 조회 - 검색x & 평점순
	List<UserProductsDTO> getProductListRR(String category, String sort, int pageNum);
	// 상품 목록 조회 - 검색x & not 평점순
	List<UserProductsDTO> getProductList(String category, String sort, String array, int pageNum);
	// 상품 목록 조회 - 검색x & 평점순 (브랜드관)
	List<UserProductsDTO> getBrandProductListRR(String brand, String sort, int pageNum);
	// 상품 목록 조회 - 검색x & not 평점순 (브랜드관)
	List<UserProductsDTO> getBrandProductList(String brand, String sort, String array, int pageNum);
	// 상품 목록 조회 - 검색o & 평점순
	List<UserProductsDTO> getProductListSRR(String category, String sort, String keyword, int pageNum);
	// 상품 목록 조회 - 검색o & not 평점순
	List<UserProductsDTO> getProductListS(String category, String sort, String array, String keyword, int pageNum);
	// ----------------------------
	// 상품 상세 조회 
	UserProductsDTO getProductDetail(String prodCode);
	int checkDibs(String email, String prodCode);
	void updateDibs(String email, String prodCode);
	void updateDibCount(String prodCode);
	int checkCarts(String email, String prodCode);
	void addCart(String email, String prodCode, String period);
	// ----------------------------
	List<String> getProdPictures(String prodCode);
	String getBrandImgName(String brand);
}
