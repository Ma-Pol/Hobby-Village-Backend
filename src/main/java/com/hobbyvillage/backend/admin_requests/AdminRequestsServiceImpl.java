package com.hobbyvillage.backend.admin_requests;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hobbyvillage.backend.Common;

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

	private String setReqProgress(String reqProgress, String reqTitle, String reqPhone) {

		if (reqTitle.length() > 10) {
			reqTitle = reqTitle.substring(0, 10) + "...";
		}
		String message = "안녕하세요. 취미빌리지입니다.\n\n고객님께서 판매/위탁 신청하신 물품 [" + reqTitle + "] ";

		if (reqProgress.equals("1차 심사 중")) {
			reqProgress = "2차 심사 대기";
			message += "이(가) 1차 심사에 통과했습니다.\n\n2차 심사를 위해서 해당 물품을 아래 주소로 배송해주시기 바랍니다." + 
			"\n\n[04524 서울특별시 중구 세종대로 110]";

		} else if (reqProgress.equals("2차 심사 대기")) {
			reqProgress = "2차 심사 중";
			message += "의 2차 심사가 시작되었습니다.";

		} else if (reqProgress.equals("2차 심사 중")) {
			reqProgress = "완료";
			message += "이(가) 2차 심사에 통과해 상품으로 등록될 예정입니다.\n\n고객님의 소중한 물품을 판매/위탁해주신 점 "
					+ "진심으로 감사드리며, 다시 한 번 2차 심사 통과에 축하드립니다.";

		} else if (reqProgress.equals("위탁 철회 요청")) {
			// 위탁 철회 요청 > 철회 처리 중 : 철회 요청을 수락하고, 물품을 배송보내기 전 단계
			reqProgress = "철회 처리 중";
			message += "의 위탁 철회 요청이 수락되어 고객님의 기본 배송지로 배송될 예정입니다. " + 
			"\n\n지금까지 저희 취미빌리지에 소중한 물품을 맡겨주신 점 진심으로 감사드립니다.";

		} else if (reqProgress.equals("철회 처리 중")) {
			// 철회 처리 중 > 철회 완료 : 물품을 배송보낸 단계
			reqProgress = "철회 완료";
			message = "안녕하세요. 취미빌리지입니다.\n\n고객님께서 철회 신청하신 물품 [" + reqTitle + "] 의 배송이 "
					+ "시작되었습니다.\n\n앞으로도 저희 취미빌리지 서비스를 애용해주시면 감사하겠습니다.";

		} else {
			reqProgress = "해당 사항 없음";
		}

		if (!reqProgress.equals("해당 사항 없음")) {
			Common.sendMessage(message, reqPhone);
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

	@Override
	public int checkReqeust(int reqCode) {
		return mapper.checkReqeust(reqCode);
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
	public int updateRequestProgress(int reqCode, String reqProgress, String reqTitle, String reqPhone) {
		int result;
		reqProgress = setReqProgress(reqProgress, reqTitle, reqPhone);

		if (reqProgress.equals("완료")) {
			mapper.updateRequestProgress(reqCode, reqProgress);
			result = 100;
		} else if (reqProgress.equals("해당 사항 없음")) {
			// 이전 진행 상황이 정상적으로 받아와지지 않은 경우
			result = 0;
		} else {
			result = mapper.updateRequestProgress(reqCode, reqProgress);
		}

		return result;
	}

	@Override // 심사 탈락 처리
	public int rejectRequestProgress(AdminRequestsRejectDTO rejectData) {
		// 메세지 전송 내용
		String reqTitle = "";

		if (rejectData.getReqTitle().length() > 10) {
			reqTitle = rejectData.getReqTitle().substring(0, 10) + "...";
		} else {
			reqTitle = rejectData.getReqTitle();
		}

		String message = "안녕하세요, 취미빌리지입니다.\n\n고객님께서 판매/위탁 신청하신 물품 [" + reqTitle + "] 이(가) 심사 탈락되었음을 알립니다."
				+ "\n\n탈락 사유: " + rejectData.getRejectReason();
		// 탈락 메세지 전송
		Common.sendMessage(message, rejectData.getReqPhone());

		return mapper.rejectRequestProgress(rejectData);
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
