package com.hobbyvillage.backend.user_reviews;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserProductReviewsServiceImpl implements UserProductReviewsService {

	private UserProductReviewsMapper mapper;

	public UserProductReviewsServiceImpl(UserProductReviewsMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public int getProdRevwCount(String prodCode) {
		return mapper.getProdRevwCount(prodCode);
	}

	@Override
	public List<UserProductReviewsDTO> getProdRevwList(String prodCode) {
		return mapper.getProdRevwList(prodCode);
	}

	@Override
	public int checkIsReported(String email, String revwCode) {
		return mapper.checkIsReported(email, revwCode);
	}

	@Override
	public void reportReview(String email, String revwCode) {
		mapper.reportReview(email, revwCode);
	}

	@Override
	public void plusRevwReport(String revwCode) {
		mapper.plusRevwReport(revwCode);
	}

	@Override
	public List<String> getProdRevwPics(String revwCode) {
		return mapper.getProdRevwPics(revwCode);
	}

	@Override
	public String getProfPicture(String revwWriter) {
		return mapper.getProfPicture(revwWriter);
	}

}
