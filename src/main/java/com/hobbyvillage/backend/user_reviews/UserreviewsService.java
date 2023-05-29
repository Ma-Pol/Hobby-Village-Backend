package com.hobbyvillage.backend.user_reviews;

import java.util.List;

public interface UserReviewsService {
	int getReviewCount(String revwCode);
	List<UserReviewsListsDTO> getReviewList(String revwWriter, String sort, int pageNum);
//	UserReviewsDTO getreviewsdetails(String revwCode);
//	int reviewsmodify(UserReviewsDTO userreviews);
//	UserReviewsDTO getreviewsproducts(String prodCode);
//	int reviewscreate(UserReviewsDTO userreviews);
}
