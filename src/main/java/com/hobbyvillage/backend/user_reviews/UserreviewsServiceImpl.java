package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserreviewsServiceImpl implements UserreviewsService {
	
	private UserreviewsMapper mapper;
	
	public UserreviewsServiceImpl(UserreviewsMapper mapper) {
		this.mapper = mapper;
	}

	// 리뷰 전체 조회
	@Override
	public List<UserreviewsDTO> reviewslists() {
		return mapper.reviewslists();
	}
	
	// 리뷰 상세 조회
	@Override
	public UserreviewsDTO getreviewsdetails(String revwCode) {
		return mapper.getreviewsdetails(revwCode);
	}
	
	// 리뷰 수정
	@Override
	public int reviewsmodify(UserreviewsDTO userreviews) {
		return mapper.reviewsmodify(userreviews);
	}
	
	// 리뷰 작성 상품명 조회
	@Override
	public UserreviewsDTO getreviewsproducts(String prodCode) {
		return mapper.getreviewsproducts(prodCode);
	}
		
	// 리뷰 작성
	@Override
	public int reviewscreate(UserreviewsDTO userreviews) {
		return mapper.reviewscreate(userreviews);
	}

}
