package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class dibServiceImpl implements dibService {
	
	private dibMapper mapper;
	    
	    public dibServiceImpl(dibMapper mapper) {
	        this.mapper = mapper;
	    }

	// 찜 리스트 조회
	@Override
	public List<dibDTO> getdiblists(String email, String category) {
		category = category(category);
		return mapper.getdiblists(email, category);
	}
	
	// 찜 리스트 조회(카테고리 수량체크)
		@Override
		public List<dibDTO> getdibcategorylist(String email) {
			return mapper.getdibcategorylist(email);
		}

	// 상품 사진 조회
	@Override
	public String getdibitems(String prodCode) {
		return mapper.getdibitems(prodCode);
	}

	// 찜 삭제
	@Override
	public int deletedib(int dibCode) {
		return mapper.deletedib(dibCode);
	}
		
	// 카테고리 설정
	private String category(String category) {
		if (category.equals("none")) {
			category = "WHERE";
		} else if (category.equals("product")) {
			category = "WHERE prodBrand IS NULL AND";
		} else {
			category = "WHERE prodBrand IS NOT NULL AND";
		}
	return category;
	}
		
}
