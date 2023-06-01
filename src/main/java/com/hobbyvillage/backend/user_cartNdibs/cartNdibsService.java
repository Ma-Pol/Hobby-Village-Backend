package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface cartNdibsService {
	
	
	// 장바구니 리스트 조회
	public List<cartNdibsDTO> getcartlists(String email, String category);
	    
	// 상품 사진 조회
	public String getcartitems(@Param("prodCode") String prodCode);
	 
	// 대여기간 변경
	int modifyperiod(int cartCode);
}
