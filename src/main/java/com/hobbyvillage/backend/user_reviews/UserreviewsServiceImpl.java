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

}
