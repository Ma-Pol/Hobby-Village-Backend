package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserCartMapper {
	// 전체 장바구니 상품 개수 조회
	@Select("SELECT COUNT(*) FROM carts WHERE email = #{email};")
	int getAllCartCount(@Param("email") String email);

	// 장바구니 브랜드관 상품 개수 조회
	@Select("SELECT COUNT(*) FROM carts c INNER JOIN products p ON c.prodCode = p.prodCode "
			+ "WHERE email = #{email} AND prodBrand IS NOT NULL;")
	int getBrandCartCount(@Param("email") String email);

	// 전체 장바구니 상품 목록 조회
	@Select("SELECT c.cartCode, c.prodCode, c.period, p.prodCategory, p.prodBrand, p.prodHost, p.prodName, p.prodPrice, "
			+ "p.prodShipping, p.prodIsRental, pp.prodPicture FROM carts c INNER JOIN products p ON c.prodCode = p.prodCode "
			+ "INNER JOIN (SELECT prodCode, prodPicture FROM productPictures WHERE prodPicCode IN ("
			+ "SELECT MIN(prodPicCode) FROM productPictures GROUP BY prodCode)) pp ON c.prodCode = pp.prodCode "
			+ "WHERE c.email = #{email} ${filter};")
	List<UserCartDTO> getCartList(@Param("email") String email, @Param("filter") String filter);

	// 장바구니 상품 대여기간 변경
	@Update("UPDATE carts SET period = #{period} WHERE cartCode = #{cartCode};")
	void modifyPeriod(UserCartPeriodDTO periodData);

	// 장바구니 상품 삭제
	@Delete("DELETE FROM carts WHERE cartCode = #{cartCode};")
	int deleteCart(@Param("cartCode") int cartCode);
}
