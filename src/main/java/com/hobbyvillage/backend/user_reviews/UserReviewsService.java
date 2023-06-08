package com.hobbyvillage.backend.user_reviews;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserReviewsService {
	int getReviewCount(String email);
	List<UserReviewsListsDTO> getReviewList(String email, String sort, int pageNum);
	int checkReviews(String revwCode, String email);
	UserReviewsListsDTO getReviewsDetails(String revwCode);
	List<String> getReviewImageName(String revwCode);
	ResponseEntity<byte[]> getReviewImage(String imageName) throws IOException;
	int modifyReview(UserReviewsListsDTO reviewData);
	int modifyReviewPicture(String revwCode, MultipartFile[] uploadImg) throws IOException;
	int checkOrders(String email, String prodCode);
	int checkReviewed(String email, String prodCode);
	String getProdName(String prodCode);
	int createReview(UserReviewsListsDTO reviewData);
	int createReviewImages(String revwCode, MultipartFile[] uploadImg) throws IOException;
}
