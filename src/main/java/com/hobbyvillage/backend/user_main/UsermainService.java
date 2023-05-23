package com.hobbyvillage.backend.user_main;

import java.util.List;

public interface UsermainService {
	
	// 인기 상품 조회
	public List<UsermainDTO> mostpopularproducts();
	// 인기 브랜드 조회
	public List<UsermainDTO> mostpopularbrand();
}
