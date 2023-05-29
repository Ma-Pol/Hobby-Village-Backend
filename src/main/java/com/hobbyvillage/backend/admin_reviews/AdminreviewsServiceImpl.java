package com.hobbyvillage.backend.admin_reviews;

import org.springframework.stereotype.Service;

@Service
public class AdminreviewsServiceImpl implements AdminreviewsService {
	
	private AdminreviewsMapper mapper;
	
	public AdminreviewsServiceImpl(AdminreviewsMapper mapper) {
		this.mapper = mapper;
	}

	// 리뷰 상세 조회
	@Override
	public AdminreviewsDTO getreviewsdetails(String revwCode) {
		return mapper.getreviewsdetails(revwCode);
	}

	// 리뷰 삭제
	@Override
	public int deletereivews(AdminreviewsDTO adminreviews) {
		int res = mapper.deletereivews(adminreviews);
		return res;
	}

}
