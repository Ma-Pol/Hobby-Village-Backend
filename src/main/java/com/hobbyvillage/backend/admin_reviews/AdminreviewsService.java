package com.hobbyvillage.backend.admin_reviews;

public interface AdminreviewsService {

	// 리뷰 상세 조회
	public AdminreviewsDTO getreviewsdetails(String revwCode);
	
	// 리뷰 삭제
	int deletereivews(AdminreviewsDTO adminreviews);
}
