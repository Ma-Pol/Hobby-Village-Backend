package com.hobbyvillage.backend.admin_notices;

import java.util.List;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

@Service
public class AdminNoticesServiceImpl implements AdminNoticesService {

	private AdminNoticesMapper mapper;

	public AdminNoticesServiceImpl(AdminNoticesMapper mapper) {
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
	public List<AdminNoticesDTO> getNoticeList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getNoticeList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 공지사항 목록 조회
	public List<AdminNoticesDTO> getSearchNoticeList(String filter, String keyword, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchNoticeList(filter, keyword, sort, pageNum);
	}

	// 공지사항 상세 조회
	@Override
	public AdminNoticesDTO getNoticeDetail(int notCode) {
		return mapper.getNoticeDetail(notCode);
	}

	// 공지사항 등록
	@Override
	public int createNotice(AdminNoticesDTO notice) {
		int Check = mapper.createNotice(notice);
		int Result = 0;
		if(Check == 1){
			Result = mapper.getNotCode(notice);
		}
		return Result;
	}

	// 공지사항 첨부파일 업로드
	@Override
	public void createNoticeFile(int notCode, String originalName, String savedName){
		mapper.createNoticeFile(notCode, originalName, savedName);
	}

	// 공지사항 삭제
	@Override
	public int deleteNotice(int notCode){ return mapper.deleteNotice(notCode); }

	// 공지사항 수정
	@Override
	public int modifyNotice(int notCode, AdminNoticesDTO notice){
		notice.setNotCode(notCode);
		return mapper.modifyNotice(notice);
	}
}
