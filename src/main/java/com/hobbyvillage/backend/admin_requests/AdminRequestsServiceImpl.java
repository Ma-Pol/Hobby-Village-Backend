package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminRequestsServiceImpl implements AdminRequestsService {

	private AdminRequestsMapper mapper;

	public AdminRequestsServiceImpl(AdminRequestsMapper mapper) {
		this.mapper = mapper;
	}

	// 필터 조건에 따른 쿼리문 설정 메서드
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

	// 카테고리에 따른 쿼리문 설정 메서드
	private String categorizing(String category) {
		if (category.equals("sell")) {
			category = "reqSort = '판매'";

		} else {
			category = "reqSort = '위탁'";
		}

		return category;
	}

	private String setReqProgress(String reqProgress) {
		if (reqProgress.equals("1차 심사 중")) {
			reqProgress = "2차 심사 대기";
		} else if (reqProgress.equals("2차 심사 대기")) {
			reqProgress = "2차 심사 중";
		} else if (reqProgress.equals("2차 심사 중")) {
			reqProgress = "완료";
		} else if (reqProgress.equals("위탁 철회 요청")) {
			reqProgress = "철회 처리 중";
		} else if (reqProgress.equals("철회 처리 중")) {
			reqProgress = "철회 완료";
		} else {
			reqProgress = "해당 사항 없음";
		}

		return reqProgress;
	}

	@Override // 미검색 상태에서 신청 수 조회
	public int getRequestCount(String filter, String category) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getRequestCount(filter, category);
	}

	@Override // 검색 상태에서 신청 수 조회
	public int getSearchRequestCount(String filter, String category, String condition, String keyword) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getSearchRequestCount(filter, category, condition, keyword);
	}

	@Override // 미검색 상태에서 신청 목록 조회
	public List<AdminRequestsDTO> getRequestList(String filter, String category, String sort, int pageNum) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getRequestList(filter, category, sort, pageNum);
	}

	@Override // 검색 상태에서 신청 목록 조회
	public List<AdminRequestsDTO> getSearchRequestList(String filter, String category, String condition, String keyword,
			String sort, int pageNum) {
		filter = filtering(filter);
		category = categorizing(category);

		return mapper.getSearchRequestList(filter, category, condition, keyword, sort, pageNum);
	}

	@Override // 신청 상세 정보 조회
	public AdminRequestsDetailsDTO getRequestDetail(int reqCode) {
		return mapper.getRequestDetail(reqCode);
	}

	@Override // 신청 파일명 조회
	public List<String> getRequestFileList(int reqCode) {
		return mapper.getRequestFileList(reqCode);
	}

	@Override // 신청 진행 상황 업데이트
	public int updateRequestProgress(int reqCode, String reqProgress) {
		int result;
		reqProgress = setReqProgress(reqProgress);

		if (reqProgress.equals("완료")) {
			mapper.updateRequestProgress(reqCode, reqProgress);
			result = 100;
		} else if (reqProgress.equals("해당 사항 없음")) {
			result = 0;
		} else {
			result = mapper.updateRequestProgress(reqCode, reqProgress);
		}

		return result;
	}

	@Override // 심사 탈락 처리
	public int rejectRequestProgress(int reqCode) {
		return mapper.rejectRequestProgress(reqCode);
	}

	@Override // 위탁 철회 요청 승인 처리
	public int cancelRequestProgress(int reqCode) {
		return mapper.cancelRequestProgress(reqCode);
	}

	@Override // 위탁 철회 요청 거부 처리
	public int rejectCancelRequestProgress(int reqCode) {
		return mapper.rejectCancelRequestProgress(reqCode);
	}

}
