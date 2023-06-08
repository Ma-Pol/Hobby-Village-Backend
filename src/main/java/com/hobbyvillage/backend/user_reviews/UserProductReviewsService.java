package com.hobbyvillage.backend.user_reviews;

import java.util.List;

public interface UserProductReviewsService {
	int getProdRevwCount(String prodCode);
	List<UserProductReviewsDTO> getProdRevwList(String prodCode);
	int checkIsReported(String email, String revwCode);
	void reportReview(String email, String revwCode);
	List<String> getProdRevwPics(String revwCode);
	void plusRevwReport(String revwCode);
}
