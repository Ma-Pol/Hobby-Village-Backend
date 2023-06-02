package com.hobbyvillage.backend.user_main;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserMainServiceImpl implements UserMainService {

	private UserMainMapper mapper;

	public UserMainServiceImpl(UserMainMapper mapper) {
		this.mapper = mapper;
	}

	@Override // 인기순 정렬 상품 리스트 조회
	public List<UserMainProductDTO> getPopularProductList() {
		return mapper.getPopularProductList();
	}

	@Override // 인기순 정렬 상품 리스트 조회(브랜드관)
	public List<UserMainProductDTO> getPopularBrandProductList() {
		return mapper.getPopularBrandProductList();
	}

	@Override // 상품 별 최상단 이미지 조회
	public String getProductPicture(String prodCode) {
		return mapper.getProductPicture(prodCode);
	}

}
