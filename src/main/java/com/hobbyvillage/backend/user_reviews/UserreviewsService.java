package com.hobbyvillage.backend.user_reviews;

import java.util.List;

public interface UserreviewsService {
	
	// 리뷰 전체 조회
	public List<UserreviewsDTO> reviewslists();
	
	// 리뷰 상세 조회
	public UserreviewsDTO getreviewsdetails(String revwCode);

}
