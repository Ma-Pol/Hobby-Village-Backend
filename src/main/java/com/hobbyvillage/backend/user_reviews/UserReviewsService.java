package com.hobbyvillage.backend.user_reviews;

import java.util.List;

public interface UserReviewsService {
	
	int getProdRevwCount(String prodCode);
	List<UserReviewsDTO> getProdRevwList(String prodCode);
	void reportReview(String email, String revwCode);
	List<String> getProdRevwPics(String revwCode);
}
