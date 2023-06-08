package com.hobbyvillage.backend.user_main;

import java.util.List;

public interface UserMainService {
	// 인기순 정렬 상품 리스트 조회
	List<UserMainProductDTO> getPopularProductList();
	// 인기순 정렬 상품 리스트 조회(브랜드관)
	List<UserMainProductDTO> getPopularBrandProductList();
	// 상품 별 최상단 이미지 조회
	String getProductPicture(String prodCode);
}
