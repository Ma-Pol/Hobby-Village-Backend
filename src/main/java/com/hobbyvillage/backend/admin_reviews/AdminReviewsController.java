package com.hobbyvillage.backend.admin_reviews;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/m/reviews")
public class AdminReviewsController {

	private AdminReviewsServiceImpl adminReviewsServiceImpl;

	public AdminReviewsController(AdminReviewsServiceImpl adminReviewsServiceImpl) {
		this.adminReviewsServiceImpl = adminReviewsServiceImpl;
	}

	@GetMapping("/count")
	public int getProductCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int reviewCount;

		// 검색 여부 확인
		if (keyword == null) {
			reviewCount = adminReviewsServiceImpl.getReviewCount(filter);
		} else {
			reviewCount = adminReviewsServiceImpl.getSearchReviewCount(filter, condition, keyword);
		}

		return reviewCount;
	}

	@GetMapping("")
	public List<AdminReviewsDTO> getReviewList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminReviewsDTO> reviewList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			reviewList = adminReviewsServiceImpl.getReviewList(filter, sort, pageNum);
		} else {
			reviewList = adminReviewsServiceImpl.getSearchReviewList(filter, condition, keyword, sort, pageNum);
		}

		return reviewList;
	}

	// 리뷰 실재 여부 체크
	@GetMapping("/check/{revwCode}")
	public int checkReviews(@PathVariable(value = "revwCode", required = true) String revwCode) {
		return adminReviewsServiceImpl.checkReviews(revwCode);
	}

	// 리뷰 상세 조회
	@GetMapping("/reviewDetails/{revwCode}")
	public AdminReviewsDTO getReviewsDetails(@PathVariable(value = "revwCode", required = true) String revwCode) {
		return adminReviewsServiceImpl.getReviewsDetails(revwCode);
	}

	// 리뷰 이미지 조회
	@GetMapping("/reviewImage/{revwCode}")
	public List<String> getReviewImage(@PathVariable(value = "revwCode", required = true) String revwCode) {
		return adminReviewsServiceImpl.getReviewImage(revwCode);
	}
	
	// 이미지 불러오기
	@GetMapping("/images/{imageName}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "imageName", required = true) String imageName) {
		File file = new File(Common.uploadDir + "//Uploaded//ReviewsImage", imageName);
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 리뷰 삭제
	@DeleteMapping("/delete/{revwCode}")
	public int deletereivews(@PathVariable(value = "revwCode", required = true) String revwCode) {
		return adminReviewsServiceImpl.deleteReivew(revwCode);
	}
}
