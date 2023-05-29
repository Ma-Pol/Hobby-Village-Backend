package com.hobbyvillage.backend.admin_reviews;

import java.util.List;

public interface AdminReviewsService {
	int getReviewCount(String filter);
	int getSearchReviewCount(String filter, String condition, String keyword);
	List<AdminReviewsDTO> getReviewList(String filter, String sort, int pageNum);
	List<AdminReviewsDTO> getSearchReviewList(String filter, String condition, String keyword, String sort,
			int pageNum);
	int checkReviews(String revwCode);
	AdminReviewsDTO getReviewsDetails(String revwCode);
	List<String> getReviewImage(String revwCode);
	int deleteReivew(String revwCode);
}
