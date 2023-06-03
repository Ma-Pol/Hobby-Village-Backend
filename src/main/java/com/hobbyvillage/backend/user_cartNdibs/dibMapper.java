package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface dibMapper {

	// 찜 리스트 조회	
    @Select("SELECT d.dibCode, d.prodCode, d.email, p.prodBrand, p.prodName, p.prodContent, p.prodPrice, p.prodShipping, p.prodDibs FROM dibs d "
    		+ "INNER JOIN products p ON d.prodCode = p.prodCode "
    		+ "${category} d.email = #{email} "
    		+ "ORDER BY d.dibCode;")
    public List<dibDTO> getdiblists(@Param("email") String email, @Param("category") String category);
    
 // 찜 리스트 조회(카테고리 수량체크)
    @Select("SELECT d.dibCode, d.prodCode, d.email, p.prodBrand, p.prodName, p.prodContent, p.prodPrice, p.prodShipping, p.prodIsRental FROM dibs d "
    		+ "INNER JOIN products p ON d.prodCode = p.prodCode "
    		+ "WHERE d.email = #{email} "
    		+ "ORDER BY d.dibCode;")
    public List<dibDTO> getdibcategorylist(@Param("email") String email);
    
    // 상품 사진 조회
    @Select("SELECT prodPicture FROM productPictures "
    		+ "WHERE prodCode = #{prodCode} "
    		+ "ORDER BY prodPicCode LIMIT 1;")
    public String getdibitems(@Param("prodCode") String prodCode);
    
    // 찜 삭제
    @Delete("DELETE FROM dibs WHERE dibCode = #{dibCode};")
    int deletedib(@Param("dibCode") int dibCode);
}
