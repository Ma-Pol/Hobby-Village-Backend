package com.hobbyvillage.backend.admin_reviews;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminreviewsMapper {
	
	// 리뷰 상세 조회
	@Select("SELECT rv.revwRegiDate, rv.prodCode, rv.revwRate, rv.revwWriter, rv.revwTitle, rv.revwReport, rv.revwContent, pd.prodCode, pd.prodName, rvP.revwCode, rvP.revwPicture FROM reviews rv "
			+ "inner join "
			+ "products pd on rv.prodCode = pd.prodCode "
			+ "inner join "
			+ "reviewPictures rvP on rv.revwCode = rvP.revwCode "
			+ "where rv.revwCode = #{revwCode};")
	public AdminreviewsDTO getreviewsdetails(String revwCode);
	
	// 리뷰 삭제
	@Delete("DELETE FROM reviews where revwCode = #{revwCode}; ")
	int deletereivews(AdminreviewsDTO adminreviews);

}
