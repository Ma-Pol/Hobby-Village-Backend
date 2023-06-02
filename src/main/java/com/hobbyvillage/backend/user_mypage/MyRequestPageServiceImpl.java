package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MyRequestPageServiceImpl implements MyRequestPageService {

	private MyRequestPageMapper mapper;

	public MyRequestPageServiceImpl(MyRequestPageMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "reqProgress IS NOT NULL";

		} else if (filter.equals("1st-assessing")) {
			filter = "reqProgress = '1차 심사 중'";

		} else if (filter.equals("waiting-2nd-assess")) {
			filter = "reqProgress = '2차 심사 대기'";

		} else if (filter.equals("2nd-assessing")) {
			filter = "reqProgress = '2차 심사 중'";

		} else if (filter.equals("completed")) {
			filter = "reqProgress = '완료'";

		} else if (filter.equals("failed")) {
			filter = "reqProgress = '심사 탈락'";

		} else if (filter.equals("cancel-request")) {
			filter = "reqProgress = '위탁 철회 요청'";

		} else if (filter.equals("canceling")) {
			filter = "reqProgress = '철회 진행 중'";

		} else {
			filter = "reqProgress = '철회 완료'";
		}

		return filter;
	}

	@Override
	public int getRequestCount(String email, String filter) {
		filter = filtering(filter);

		return mapper.getRequestCount(email, filter);
	}

	@Override
	public List<MyRequestPageDTO> getRequestList(String email, String filter, int pageNum) {
		filter = filtering(filter);

		return mapper.getRequestList(email, filter, pageNum);
	}

	@Override
	public List<String> getRequestPictures(int reqCode) {
		return mapper.getRequestPictures(reqCode);
	}

	@Override
	public void withdrawRequest(int reqCode) {
		mapper.withdrawRequest(reqCode);
	}

	@Override
	public void updateAccount(String reqBank, String reqAccountNum, int reqCode) {
		mapper.updateAccount(reqBank, reqAccountNum, reqCode);
	}

}
