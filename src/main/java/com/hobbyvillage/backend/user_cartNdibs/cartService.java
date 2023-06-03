package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface cartService {
	
	// 장바구니 리스트 조회
	public List<cartDTO> getcartlists(String email, String category);
	
	// 장바구니 리스트 조회(카테고리 수량체크)
	public List<cartDTO> getcategorylist(String email);
	    
	// 상품 사진 조회
	public String getcartitems(@Param("prodCode") String prodCode);
	 
	// 대여기간 변경
	int modifyperiod(int cartCode, int period);
	
	 // 장바구니 삭제
	 int deletecart( int cartCode);
	 
	 // 장바구니 선택 품목 삭제
	 public List<cartDTO> deletecartlist(int cartCode);
}
