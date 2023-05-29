package com.hobbyvillage.backend.user_reviews;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@Service
public class UserReviewsServiceImpl implements UserReviewsService {

	private UserReviewsMapper mapper;

	public UserReviewsServiceImpl(UserReviewsMapper mapper) {
		this.mapper = mapper;
	}

	private String revwUploadPath = Common.uploadDir + "\\Uploaded\\ReviewsImage\\";

	// 리뷰 코드로 이미지 이름을 조회한 뒤 삭제하는 메서드
	private void deleteReviewPicture(String revwCode) {
		List<String> reviewPictures = mapper.getReviewImageName(revwCode);

		if (reviewPictures != null) {
			for (String reviewImage : reviewPictures) {
				File filePath = new File(revwUploadPath + reviewImage);

				filePath.delete();
			}
		}

		mapper.deleteReviewPictures(revwCode);
	}

	// 리뷰 개수 조회
	@Override
	public int getReviewCount(String email) {
		return mapper.getReviewCount(email);
	}

	// 리뷰 목록 조회
	@Override
	public List<UserReviewsListsDTO> getReviewList(String email, String sort, int pageNum) {
		return mapper.getReviewList(email, sort, pageNum);
	}

	// 실재 리뷰 체크
	@Override
	public int checkReviews(String revwCode, String email) {
		return mapper.checkReviews(revwCode, email);
	}

	// 리뷰 상세 조회
	@Override
	public UserReviewsListsDTO getReviewsDetails(String revwCode) {
		return mapper.getReviewsDetails(revwCode);
	}

	// 리뷰 이미지 조회
	@Override
	public List<String> getReviewImageName(String revwCode) {
		return mapper.getReviewImageName(revwCode);
	}

	// 이미지 출력
	@Override
	public ResponseEntity<byte[]> getReviewImage(String imageName) throws IOException {
		File file = new File(revwUploadPath, imageName);
		ResponseEntity<byte[]> result = null;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

		return result;
	}

	// 리뷰 수정
	@Override
	public int modifyReview(UserReviewsListsDTO reviewData) {
		return mapper.modifyReview(reviewData);
	}

	// 리뷰 이미지 수정
	@Override
	public int modifyReviewPicture(String revwCode, MultipartFile[] uploadImg) throws IOException {
		deleteReviewPicture(revwCode);

		int result = 0;

		// 이미지 개수만큼 반복
		for (MultipartFile image : uploadImg) {
			// 값이 없는 이미지가 아닌 경우(정상적인 이미지인 경우)
			if (!image.isEmpty()) {
				// 저장할 파일 명 설정(UUID를 사용해 파일명 중복을 피함)
				String revwPicture = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

				// 파일 저장 위치
				File file = new File(revwUploadPath, revwPicture);

				// 여기서 실제 업로드가 이뤄집니다.
				image.transferTo(file);

				// DB에 파일명을 저장할 경우 이곳에서 처리하기
				mapper.insertReviewPictures(revwCode, revwPicture);
				result++;
			}
		}

		return result;
	}

//
//	// 리뷰 작성 상품명 조회
//	@Override
//	public UserReviewsDTO getreviewsproducts(String prodCode) {
//		return mapper.getreviewsproducts(prodCode);
//	}
//
//	// 리뷰 작성
//	@Override
//	public int reviewscreate(UserReviewsDTO userreviews) {
//		return mapper.reviewscreate(userreviews);
//	}

}
