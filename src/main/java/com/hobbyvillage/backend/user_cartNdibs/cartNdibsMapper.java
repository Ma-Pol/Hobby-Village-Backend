package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface cartNdibsMapper {
	
	// 장바구니 리스트 조회	
    @Select("SELECT c.cartCode, c.prodCode, c.period, c.email, p.prodBrand, p.prodName, p.prodContent, p.prodPrice, p.prodShipping, p.prodIsRental FROM carts c "
    		+ "INNER JOIN products p ON c.prodCode = p.prodCode "
    		+ "${category} c.email = #{email} "
    		+ "ORDER BY c.cartCode;")
    public List<cartNdibsDTO> getcartlists(@Param("email") String email, @Param("category") String category);
    
    // 상품 사진 조회
    @Select("SELECT prodPicture FROM productPictures "
    		+ "WHERE prodCode = #{prodCode} "
    		+ "ORDER BY prodPicCode LIMIT 1;")
    public String getcartitems(@Param("prodCode") String prodCode);
    
    // 대여기간 변경
    @Update("UPDATE carts SET period = #{period}, WHERE cartCode = #{cardCode};")
    int modifyperiod(@Param("cartCode") int cartCode);
	

}
