package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserreviewsMapper {
	
	// 리뷰 전체 조회
	@Select("SELECT rv.revwCode, rv.revwRate, rv.revwTitle, rv.revwRegiDate, rv.revwReport, u.email, u.nickname, pd.prodCode, pd.prodName FROM reviews rv "
			+ "inner join "
			+ "users u on rv.revwWriter = u.nickname "
			+ "inner join "
			+ "products pd on rv.prodCode = pd.prodCode")
	public List<UserreviewsDTO> reviewslists();
	
	// 리뷰 상세 조회
	@Select("SELECT rv.revwCode, rv.revwRate, rv.revwTitle, rv.revwContent, rv.revwReport, pd.prodCode, pd.prodName, rvP.revwCode, rvP.revwPicture FROM reviews rv "
			+ "inner join "
			+ "products pd on rv.prodCode = pd.prodCode "
			+ "inner join "
			+ "reviewPictures rvP on rv.revwCode = rvP.revwCode "
			+ "where rv.revwCode = #{revwCode};")
	public UserreviewsDTO getreviewsdetails(String revwCode);
	
	// 리뷰 수정
	@Update("UPDATE reviews "
			+ "SET revwTitle = #{revwTitle}, revwRate = #{revwRate}, revwContent = #{revwContent} "
			+ "where revwCode = #{revwCode}; ")
	int reviewsmodify(UserreviewsDTO userreviews);
	
	// 리뷰 작성 상품명 조회
	@Select("SELECT prodCode, prodName FROM products "
			+ "where prodCode = #{prodCode}")
	public UserreviewsDTO getreviewsproducts(String prodCode);
	
	// 리뷰 작성
	@Insert("INSERT INTO reviews(prodCode, revwCode, revwTitle, revwRate, revwContent, revwWriter) "
			+ "VALUES(#{prodCode}, #{revwCode}, #{revwTitle}, #{revwRate}, #{revwContent}, #{revwWriter});")
	int reviewscreate(UserreviewsDTO userreviews);

}
