package com.hobbyvillage.backend.user_reviews;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/prodReview")
public class UserProductReviewsController {

	private UserProductReviewsServiceImpl userProductReviewsServiceImpl;

	public UserProductReviewsController(UserProductReviewsServiceImpl userProductReviewsServiceImpl) {
		this.userProductReviewsServiceImpl = userProductReviewsServiceImpl;
	}

	// 상품 리뷰 개수
	@GetMapping("/count")
	public int getProdRevwCount(@RequestParam(value = "prodCode") String prodCode) {
		return userProductReviewsServiceImpl.getProdRevwCount(prodCode);
	}

	// 상품 리뷰 목록+상세 조회
	@GetMapping("/list")
	public List<UserProductReviewsDTO> getProdRevwList(@RequestParam(value = "prodCode") String prodCode) {
		return userProductReviewsServiceImpl.getProdRevwList(prodCode);
	}

	// 리뷰 신고 여부 확인
	@GetMapping("/checkReport")
	public int checkIsReported(@RequestParam(value = "email") String email,
			@RequestParam(value = "revwCode") String revwCode) {
		return userProductReviewsServiceImpl.checkIsReported(email, revwCode);
	}

	// 리뷰 신고
	@GetMapping("/report")
	public void reportReview(@RequestParam(value = "email") String email,
			@RequestParam(value = "revwCode") String revwCode) {
		userProductReviewsServiceImpl.reportReview(email, revwCode);
		userProductReviewsServiceImpl.plusRevwReport(revwCode);
	}

	// 리뷰 이미지 파일명 조회
	@GetMapping("/imgName")
	public List<String> getProdRevwPics(@RequestParam(value = "revwCode") String revwCode) {
		return userProductReviewsServiceImpl.getProdRevwPics(revwCode);
	}

	// macOS 경로: //Uploaded//ReviewsImage
	// 윈도우 경로: \\Uploaded\\ReviewsImage
	@GetMapping("/upload/{fileName}") // 리뷰 이미지 불러오기
	public ResponseEntity<byte[]> getRequestFileData(
			@PathVariable(value = "fileName", required = true) String fileName) {
		File file = new File(Common.uploadDir + "\\Uploaded\\ReviewsImage", fileName);
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

	// macOS 경로: //Uploaded//UserProfileImage
	// 윈도우 경로: \\Uploaded\\UserProfileImage
	@GetMapping("/upload/profPic/{fileName}") // 프로필 이미지 불러오기
	public ResponseEntity<byte[]> getProfPics(@PathVariable(value = "fileName", required = true) String fileName) {
		File file = new File(Common.uploadDir + "\\Uploaded\\UserProfileImage", fileName);
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
}
