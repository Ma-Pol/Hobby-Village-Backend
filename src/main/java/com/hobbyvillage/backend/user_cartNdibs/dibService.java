package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface dibService {
	
		// 찜 리스트 조회
		public List<dibDTO> getdiblists(String email, String category);
		
		// 찜 리스트 조회(카테고리 수량체크)
		public List<dibDTO> getdibcategorylist(String email);
		    
		// 상품 사진 조회
		public String getdibitems(@Param("prodCode") String prodCode);
		 		
		// 찜 삭제
		 int deletedib(int dibCode);

}
