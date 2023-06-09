package com.hobbyvillage.backend.user_reviews;

import java.io.IOException;
import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users/reviews")
public class UserReviewsController {

	private UserReviewsServiceImpl userReviewsServiceImpl;

	public UserReviewsController(UserReviewsServiceImpl userReviewsServiceImpl) {
		this.userReviewsServiceImpl = userReviewsServiceImpl;
	}

	// 리뷰 개수 조회
	@GetMapping("/count")
	public int getReviewCount(@RequestParam(value = "email", required = true) String email) {
		return userReviewsServiceImpl.getReviewCount(email);
	}

	// 리뷰 목록 조회
	@GetMapping("/lists")
	public List<UserReviewsListsDTO> getReviewList(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages) {
		int pageNum = (pages - 1) * 10;

		return userReviewsServiceImpl.getReviewList(email, sort, pageNum);
	}

	// 리뷰 실재 여부 체크
	@GetMapping("/check")
	public int checkReviews(@RequestParam(value = "revwCode", required = true) String revwCode,
			@RequestParam(value = "email", required = true) String email) {
		return userReviewsServiceImpl.checkReviews(revwCode, email);
	}

	// 리뷰 상세 조회
	@GetMapping("/reviewDetails/{revwCode}")
	public UserReviewsListsDTO getReviewsDetails(@PathVariable(value = "revwCode", required = true) String revwCode) {
		return userReviewsServiceImpl.getReviewsDetails(revwCode);
	}

	// 리뷰 이미지 조회
	@GetMapping("/reviewImage/{revwCode}")
	public List<String> getReviewImageName(@PathVariable(value = "revwCode", required = true) String revwCode) {
		return userReviewsServiceImpl.getReviewImageName(revwCode);
	}

	// 이미지 불러오기
	@GetMapping("/images/{imageName}")
	public ResponseEntity<byte[]> getReviewImage(@PathVariable(value = "imageName", required = true) String imageName)
			throws IOException {
		return userReviewsServiceImpl.getReviewImage(imageName);
	}

	// 리뷰 수정
	@PatchMapping("/modify")
	public int modifyReview(@RequestBody UserReviewsListsDTO reviewData) {
		return userReviewsServiceImpl.modifyReview(reviewData);
	}

	// 리뷰 이미지 수정
	@PatchMapping("/upload/{revwCode}")
	public int modifyReviewPicture(@PathVariable(value = "revwCode", required = true) String revwCode,
			@RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg) throws IOException {
		return userReviewsServiceImpl.modifyReviewPicture(revwCode, uploadImg);
	}

	// 리뷰 작성 전 주문 여부 확인
	@GetMapping("/checkOrders")
	public int checkOrders(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "prodCode", required = true) String prodCode) {
		return userReviewsServiceImpl.checkOrders(email, prodCode);
	}
	
	// 리뷰 작성 전 리뷰 작성 여부 확인
	@GetMapping("/checkReviewed")
	public int checkReviewed(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "prodCode", required = true) String prodCode) {
		return userReviewsServiceImpl.checkReviewed(email, prodCode);
	}

	// 상품명 조회
	@GetMapping("/getProdName")
	public String getProdName(@RequestParam(value = "prodCode", required = true) String prodCode) {
		return userReviewsServiceImpl.getProdName(prodCode);
	}

	// 리뷰 작성 1: 리뷰 정보 저장
	@PostMapping("/create")
	public int createReview(@RequestBody UserReviewsListsDTO reviewData) {
		return userReviewsServiceImpl.createReview(reviewData);
	}

	// 리뷰 작성 2: 리뷰 이미지 저장
	@PostMapping("/upload/{revwCode}")
	public int createReviewImages(@PathVariable(value = "revwCode", required = true) String revwCode,
			@RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg) throws IOException {
		return userReviewsServiceImpl.createReviewImages(revwCode, uploadImg);
	}

}
