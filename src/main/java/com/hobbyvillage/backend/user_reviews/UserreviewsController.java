package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


}
