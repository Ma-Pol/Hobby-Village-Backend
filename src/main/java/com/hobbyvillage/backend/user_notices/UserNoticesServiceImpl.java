package com.hobbyvillage.backend.user_notices;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.hobbyvillage.backend.Common;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserNoticesServiceImpl implements UserNoticesService {

	private UserNoticesMapper mapper;

	public UserNoticesServiceImpl(UserNoticesMapper mapper) {
		this.mapper = mapper;
	}

	private String uploadPath = Common.uploadDir + "\\Uploaded\\NoticesFile\\";

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
	public List<UserNoticesDTO> getNoticeList(String filter, int pageNum) {
		filter = filtering(filter);

		return mapper.getNoticeList(filter, pageNum);
	}

	@Override // 검색 상태에서 공지사항 목록 조회
	public List<UserNoticesDTO> getSearchNoticeList(String filter, String keyword, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchNoticeList(filter, keyword, pageNum);
	}

	// 공지사항 상세 조회 1: 실재하는 공지사항인지 확인
	public int checkNotice(int notCode) {
		return mapper.checkNotice(notCode);
	}

	// 공지사항 상세 조회 2: 공지사항 상세 조회
	@Override
	public UserNoticesDTO getNoticeDetail(int notCode) {
		return mapper.getNoticeDetail(notCode);
	}

	// 공지사항 상세 조회 3: 공지사항 파일 조회
	public List<UserNoticesFileDTO> getFileData(int notCode) {
		List<UserNoticesFileDTO> result = null;
		int count = mapper.getNoticeFileCount(notCode);

		if (count > 0) {
			result = mapper.getFileData(notCode);
		}

		return result;
	}

	// 공지사항 조회수 증가
	@Override
	public int updateNoticeView(int notCode) {
		return mapper.updateNoticeView(notCode);
	}

	// 파일 다운로드
	@Override
	public ResponseEntity<UrlResource> fileDownload(String originalFileName, String storedFileName)
			throws MalformedURLException, Exception {
		UrlResource resource = new UrlResource("file:" + uploadPath + "/" + storedFileName);

		String firstEncodedFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
		String secondEncodedFileName = URLEncoder.encode(firstEncodedFileName, "UTF-8");
		String contentDisposition = "attachment; filename=\"" + secondEncodedFileName + "\"";

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);

	}
}
