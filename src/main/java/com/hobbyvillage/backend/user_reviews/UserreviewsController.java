package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserreviewsController {
	
	private UserreviewsServiceImpl userreviewsServiceImpl;

	public UserreviewsController(UserreviewsServiceImpl userreviewsServiceImpl) {
		this.userreviewsServiceImpl = userreviewsServiceImpl;
	}
	
	// 리뷰 전체 조회
	@GetMapping("/reviews/lists")
	public List<UserreviewsDTO> getreviewslists() {
		return userreviewsServiceImpl.reviewslists();
	}
	
	// 리뷰 상세 조회
	@GetMapping("/reviews/details/{revwCode}")
	public UserreviewsDTO getreviewsdetails(@PathVariable(value = "revwCode", required = true) String revwCode) {
		return userreviewsServiceImpl.getreviewsdetails(revwCode);
	}
	
	// 리뷰 수정
	@PatchMapping("/reviews/modify") 
	public int reviewsmodify(@RequestBody UserreviewsDTO userreviews) throws Exception {
		return userreviewsServiceImpl.reviewsmodify(userreviews);
	}
	
	// 리뷰 작성 상품명 조회
	@GetMapping("/reviews/products/{prodCode}")
	public UserreviewsDTO getreviewsproducts(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return userreviewsServiceImpl.getreviewsproducts(prodCode);
	}
	
	// 리뷰 작성
	@PostMapping("/reviews/create/{prodCode}")
	public int reviewscreate(@RequestBody UserreviewsDTO userreviews) {
		return userreviewsServiceImpl.reviewscreate(userreviews);
	}


}
