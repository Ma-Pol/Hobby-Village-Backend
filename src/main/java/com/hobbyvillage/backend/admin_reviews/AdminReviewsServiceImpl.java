package com.hobbyvillage.backend.admin_reviews;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.hobbyvillage.backend.Common;

@Service
public class AdminReviewsServiceImpl implements AdminReviewsService {

	private AdminreviewsMapper mapper;

	public AdminReviewsServiceImpl(AdminreviewsMapper mapper) {
		this.mapper = mapper;
	}

	private String revwUploadPath = Common.uploadDir + "\\Uploaded\\ReviewsImage\\";

	// 필터 조건에 따른 쿼리문 설정 메서드
	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "revwReport >= 0";
		} else {
			filter = "revwReport >= 5";
		}

		return filter;
	}

	@Override // 미검색 상태에서 리뷰 개수 조회
	public int getReviewCount(String filter) {
		filter = filtering(filter);

		return mapper.getReviewCount(filter);
	}

	@Override // 검색 상태에서 리뷰 개수 조회
	public int getSearchReviewCount(String filter, String condition, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchReviewCount(filter, condition, keyword);
	}

	@Override // 미검색 상태에서 리뷰 목록 조회
	public List<AdminReviewsDTO> getReviewList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getReviewList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 리뷰 목록 조회
	public List<AdminReviewsDTO> getSearchReviewList(String filter, String condition, String keyword, String sort,
			int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchReviewList(filter, condition, keyword, sort, pageNum);
	}

	// 리뷰 조회 전 실재 확인
	@Override
	public int checkReviews(String revwCode) {
		return mapper.checkReviews(revwCode);
	}

	// 리뷰 상세 조회
	@Override
	public AdminReviewsDTO getReviewsDetails(String revwCode) {
		return mapper.getReviewsDetails(revwCode);
	}

	// 리뷰 이미지 조회
	@Override
	public List<String> getReviewImage(String revwCode) {
		return mapper.getReviewImage(revwCode);
	}

	// 이미지 불러오기
	@Override
	public ResponseEntity<byte[]> getReqeustFileData(String imageName) throws IOException {
		File file = new File(revwUploadPath, imageName);
		ResponseEntity<byte[]> result = null;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

		return result;
	}

	// 리뷰 삭제
	@Override
	public int deleteReivew(String revwCode) {
		// 리뷰 이미지 목록 조회
		List<String> reviewImages = mapper.getReviewImage(revwCode);

		// 리뷰 이미지 삭제
		if (reviewImages != null) {
			for (String reviewImage : reviewImages) {
				File filePath = new File(revwUploadPath + reviewImage);

				filePath.delete();
			}
		}

		int res = mapper.deleteReivew(revwCode);
		return res;
	}

}
