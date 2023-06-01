package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class cartNdibsServiceImpl implements cartNdibsService {
	
    private cartNdibsMapper mapper;
    
    public cartNdibsServiceImpl(cartNdibsMapper mapper) {
        this.mapper = mapper;
    }

    // 장바구니 리스트 조회
	@Override
	public List<cartNdibsDTO> getcartlists(String email, String category) {
		category = category(category);
		return mapper.getcartlists(email, category);
	}
	
	// 상품 사진 조회
	@Override
	public String getcartitems(String prodCode) {
		return mapper.getcartitems(prodCode);
	}
	
	// 대여기간 변경
	@Override
	public int modifyperiod(int cartCode) {
		return mapper.modifyperiod(cartCode);
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
