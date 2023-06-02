package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class cartServiceImpl implements cartService {
	
    private cartMapper mapper;
    
    public cartServiceImpl(cartMapper mapper) {
        this.mapper = mapper;
    }

    // 장바구니 리스트 조회
	@Override
	public List<cartDTO> getcartlists(String email, String category) {
		category = category(category);
		return mapper.getcartlists(email, category);
	}
	
	// 장바구니 리스트 조회(카테고리 수량체크)
	@Override
	public List<cartDTO> getcategorylist(String email) {
		return mapper.getcategorylist(email);
	}
	
	// 상품 사진 조회
	@Override
	public String getcartitems(String prodCode) {
		return mapper.getcartitems(prodCode);
	}
	
	// 대여기간 변경
	@Override
	public int modifyperiod(int cartCode, int period) {
		return mapper.modifyperiod(cartCode, period);
	}
	
	// 장바구니 삭제
	@Override
	public int deletecart(int cartCode) {
		return mapper.deletecart(cartCode);
	}
	
	// 장바구니 선택 품목 삭제
	@Override
	public List<cartDTO> deletecartlist(int cartCode) {
		return mapper.deletecartlist(cartCode);
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
