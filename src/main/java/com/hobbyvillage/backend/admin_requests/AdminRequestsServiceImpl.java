package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminRequestsServiceImpl implements AdminRequestsService {

	private AdminRequestsMapper mapper;

	public AdminRequestsServiceImpl(AdminRequestsMapper mapper) {
		this.mapper = mapper;
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
