package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/reviews")
public class UserReviewsController {

	private UserReviewsServiceImpl userReviewsServiceImpl;

	public UserReviewsController(UserReviewsServiceImpl userReviewsServiceImpl) {
		this.userReviewsServiceImpl = userReviewsServiceImpl;
	}

	// 리뷰 개수 조회
	@GetMapping("/count")
	public int getReviewCount(@RequestParam(value = "revwWriter", required = true) String revwWriter) {
		return userReviewsServiceImpl.getReviewCount(revwWriter);
	}

	// 리뷰 목록 조회
	@GetMapping("/lists")
	public List<UserReviewsListsDTO> getReviewList(
			@RequestParam(value = "revwWriter", required = true) String revwWriter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages) {
		int pageNum = (pages - 1) * 10;

		return userReviewsServiceImpl.getReviewList(revwWriter, sort, pageNum);
	}

//	// 리뷰 상세 조회
//	@GetMapping("/reviews/details/{revwCode}")
//	public UserReviewsDTO getreviewsdetails(@PathVariable(value = "revwCode", required = true) String revwCode) {
//		return userreviewsServiceImpl.getreviewsdetails(revwCode);
//	}
//
//	// 리뷰 수정
//	@PatchMapping("/reviews/modify")
//	public int reviewsmodify(@RequestBody UserReviewsDTO userreviews) throws Exception {
//		return userreviewsServiceImpl.reviewsmodify(userreviews);
//	}
//
//	// 리뷰 작성 상품명 조회
//	@GetMapping("/reviews/products/{prodCode}")
//	public UserReviewsDTO getreviewsproducts(@PathVariable(value = "prodCode", required = true) String prodCode) {
//		return userreviewsServiceImpl.getreviewsproducts(prodCode);
//	}
//
//	// 리뷰 작성
//	@PostMapping("/reviews/create/{prodCode}")
//	public int reviewscreate(@RequestBody UserReviewsDTO userreviews) {
//		return userreviewsServiceImpl.reviewscreate(userreviews);
//	}

}
