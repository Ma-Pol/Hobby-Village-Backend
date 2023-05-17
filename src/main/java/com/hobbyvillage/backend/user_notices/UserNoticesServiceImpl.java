package com.hobbyvillage.backend.user_notices;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNoticesServiceImpl implements UserNoticesService {

	private UserNoticesMapper mapper;

	public UserNoticesServiceImpl(UserNoticesMapper mapper) {
		this.mapper = mapper;
	}

	// 필터 조건에 따른 쿼리문 설정 메서드
	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "notCategory IS NOT NULL";

		} else if (filter.equals("info")) {
			filter = "notCategory = '안내'";

		} else {
			filter = "notCategory = '이벤트'";
		}

		return filter;
	}

	@Override // 미검색 상태에서 공지사항 개수 조회
	public int getNoticeCount(String filter) {
		filter = filtering(filter);

		return mapper.getNoticeCount(filter);
	}

	@Override // 검색 상태에서 공지사항 개수 조회
	public int getSearchNoticeCount(String filter, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchNoticeCount(filter, keyword);
	}

	@Override // 미검색 상태에서 공지사항 목록 조회
	public List<UserNoticesDTO> getNoticeList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getNoticeList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 공지사항 목록 조회
	public List<UserNoticesDTO> getSearchNoticeList(String filter, String keyword, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchNoticeList(filter, keyword, sort, pageNum);
	}

	// 공지사항 상세 조회
	@Override
	public UserNoticesDTO getNoticeDetail(int notCode) {
		return mapper.getNoticeDetail(notCode);
	}
}
