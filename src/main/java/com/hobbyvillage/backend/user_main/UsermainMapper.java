package com.hobbyvillage.backend.user_main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsermainMapper {
	
	// 인기 상품 리스트 출력
	@Select("SELECT pd.prodcode, pd.prodName, pd.prodPrice, pd.prodDibs, pd.rentalCount, pdP.prodCode, pdP.prodPicture FROM products pd "
			+ "inner join "
			+ "productPictures pdP on pd.prodCode = pdP.prodCode")
		public List<UsermainDTO> mostpopularproducts();	
	
	// 인기 브랜드관 상품 리스트 출력
	@Select("SELECT pd.prodcode, pd.prodBrand, pd.prodName, pd.prodContent, pd.prodPrice, pd.prodDibs, pdP.prodCode, pdP.prodPicture FROM products pd "
			+ "inner join "
			+ "productPictures pdP on pd.prodCode = pdP.prodCode")
		public List<UsermainDTO> mostpopularbrand();	
	}
	
