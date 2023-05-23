package com.hobbyvillage.backend.user_main;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsermainServiceImpl implements UsermainService {
	
	private UsermainMapper mapper;
	
	public UsermainServiceImpl(UsermainMapper mapper) {
		this.mapper = mapper;
	}
 
	// 인기 상품 조회
	@Override
	public List<UsermainDTO> mostpopularproducts() {
		return mapper.mostpopularproducts();
	}
	// 인기 브랜드 조회
	@Override
	public List<UsermainDTO> mostpopularbrand() {
		return mapper.mostpopularbrand();
	}

}
