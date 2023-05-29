package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserReviewsServiceImpl implements UserReviewsService {

	private UserReviewsMapper mapper;

	public UserReviewsServiceImpl(UserReviewsMapper mapper) {
		this.mapper = mapper;
	}

	// 리뷰 개수 조회
	public int getReviewCount(String revwCode) {
		return mapper.getReviewCount(revwCode);
	}

	// 리뷰 목록 조회
	public List<UserReviewsListsDTO> getReviewList(String revwWriter, String sort, int pageNum) {
		return mapper.getReviewList(revwWriter, sort, pageNum);
	}

//	// 리뷰 상세 조회
//	@Override
//	public UserReviewsDTO getreviewsdetails(String revwCode) {
//		return mapper.getreviewsdetails(revwCode);
//	}
//
//	// 리뷰 수정
//	@Override
//	public int reviewsmodify(UserReviewsDTO userreviews) {
//		return mapper.reviewsmodify(userreviews);
//	}
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
