package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface cartMapper {
	
	// 장바구니 리스트 조회	
    @Select("SELECT c.cartCode, c.prodCode, c.period, c.email, p.prodBrand, p.prodName, p.prodContent, p.prodPrice, p.prodShipping, p.prodIsRental FROM carts c "
    		+ "INNER JOIN products p ON c.prodCode = p.prodCode "
    		+ "${category} c.email = #{email} "
    		+ "ORDER BY c.cartCode;")
    public List<cartDTO> getcartlists(@Param("email") String email, @Param("category") String category);
    
    // 장바구니 리스트 조회(카테고리 수량체크)
    @Select("SELECT c.cartCode, c.prodCode, c.period, c.email, p.prodBrand, p.prodName, p.prodContent, p.prodPrice, p.prodShipping, p.prodIsRental FROM carts c "
    		+ "INNER JOIN products p ON c.prodCode = p.prodCode "
    		+ "WHERE c.email = #{email} "
    		+ "ORDER BY c.cartCode;")
    public List<cartDTO> getcategorylist(@Param("email") String email);
    
    // 상품 사진 조회
    @Select("SELECT prodPicture FROM productPictures "
    		+ "WHERE prodCode = #{prodCode} "
    		+ "ORDER BY prodPicCode LIMIT 1;")
    public String getcartitems(@Param("prodCode") String prodCode);
    
    // 대여기간 변경
    @Update("UPDATE carts SET period = #{period} WHERE cartCode = #{cartCode};")
    int modifyperiod(@Param("cartCode") int cartCode, @Param("period") int period);
    
    // 장바구니 삭제
    @Delete("DELETE FROM carts WHERE cartCode = #{cartCode};")
    int deletecart(@Param("cartCode") int cartCode);
    
    // 장바구니 선택 품목 삭제
    @Delete("DELETE FROM carts WHERE cartCode = #{cartCode};")
    public List<cartDTO> deletecartlist(@Param("cartCode") int cartCode);
	

}
