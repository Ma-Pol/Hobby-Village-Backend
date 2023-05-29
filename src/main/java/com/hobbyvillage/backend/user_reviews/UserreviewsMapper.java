package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserReviewsMapper {

	// 리뷰 개수 조회
	@Select("SELECT COUNT(*) FROM reviews WHERE revwWriter = #{revwWriter}")
	int getReviewCount(@Param("revwWriter") String revwWriter);

	// 리뷰 목록 조회
	@Select("SELECT r.revwCode, r.revwRate, r.revwTitle, r.revwRegiDate, r.revwReport, p.prodCode, p.prodName "
			+ "FROM reviews r INNER JOIN products p ON r.prodCode = p.prodCode WHERE revwWriter = #{revwWriter} "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<UserReviewsListsDTO> getReviewList(@Param("revwWriter") String revwWriter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 실재 리뷰 확인
	@Select("SELECT COUNT(*) FROM reviews WHERE revwCode = #{revwCode};")
	int checkReviews(@Param("revwCode") String revwCode);

//	
//	
//	// 리뷰 상세 조회
//	@Select("SELECT rv.revwCode, rv.revwRate, rv.revwTitle, rv.revwContent, rv.revwReport, pd.prodCode, pd.prodName, rvP.revwCode, rvP.revwPicture FROM reviews rv "
//			+ "inner join " + "products pd on rv.prodCode = pd.prodCode " + "inner join "
//			+ "reviewPictures rvP on rv.revwCode = rvP.revwCode " + "where rv.revwCode = #{revwCode};")
//	public UserReviewsDTO getreviewsdetails(String revwCode);
//
//	// 리뷰 수정
//	@Update("UPDATE reviews " + "SET revwTitle = #{revwTitle}, revwRate = #{revwRate}, revwContent = #{revwContent} "
//			+ "where revwCode = #{revwCode}; ")
//	int reviewsmodify(UserReviewsDTO userreviews);
//
//	// 리뷰 작성 상품명 조회
//	@Select("SELECT prodCode, prodName FROM products " + "where prodCode = #{prodCode}")
//	public UserReviewsDTO getreviewsproducts(String prodCode);
//
//	// 리뷰 작성
//	@Insert("INSERT INTO reviews(prodCode, revwCode, revwTitle, revwRate, revwContent, revwWriter) "
//			+ "VALUES(#{prodCode}, #{revwCode}, #{revwTitle}, #{revwRate}, #{revwContent}, #{revwWriter});")
//	int reviewscreate(UserReviewsDTO userreviews);

}
