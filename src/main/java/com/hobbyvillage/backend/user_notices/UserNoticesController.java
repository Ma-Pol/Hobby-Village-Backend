package com.hobbyvillage.backend.user_notices;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/notices")
public class UserNoticesController {

	private UserNoticesServiceImpl userNoticesServiceImpl;

	public UserNoticesController(UserNoticesServiceImpl userNoticesServiceImpl) {
		this.userNoticesServiceImpl = userNoticesServiceImpl;
	}

	@GetMapping("/count")
	public int getNoticeCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int noticeCount;

		// 검색 여부 확인
		if (keyword == null) {
			noticeCount = userNoticesServiceImpl.getNoticeCount(filter);
		} else {
			noticeCount = userNoticesServiceImpl.getSearchNoticeCount(filter, keyword);
		}

		return noticeCount;
	}

	@GetMapping("")
	public List<UserNoticesDTO> getNoticeList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<UserNoticesDTO> noticeList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			noticeList = userNoticesServiceImpl.getNoticeList(filter, pageNum);
		} else {
			noticeList = userNoticesServiceImpl.getSearchNoticeList(filter, keyword, pageNum);
		}

		return noticeList;
	}

	// 공지사항 상세 조회 1: 실재하는 공지사항인지 확인
	@GetMapping("/check/{notCode}")
	public int checkNotice(@PathVariable(value = "notCode", required = true) String notCode) {
		int result = 0;

		// notCode가 숫자인지 확인
		if (notCode.matches("-?\\d+")) {
			int notCodeInt = Integer.parseInt(notCode);
			result = userNoticesServiceImpl.checkNotice(notCodeInt);
		}

		return result;
	}

	// 공지사항 상세 조회 2: 공지사항 상세 조회
	@GetMapping("/noticeDetails/{notCode}")
	public UserNoticesDTO getNoticeDetail(@PathVariable(value = "notCode", required = true) int notCode) {
		return userNoticesServiceImpl.getNoticeDetail(notCode);
	}

	// 공지사항 상세 조회 3: 공지사항 파일 조회
	@GetMapping("/noticeFiles/{notCode}")
	public List<UserNoticesFileDTO> getFileData(@PathVariable(value = "notCode", required = true) int notCode) {
		return userNoticesServiceImpl.getFileData(notCode);
	}

	// 공지사항 조회수 증가
	@PostMapping("/noticeUpdateView/{notCode}")
	public int read(@PathVariable int notCode) {
		return userNoticesServiceImpl.updateNoticeView(notCode);
	}

	// 공지사항 파일 다운로드
	@PostMapping("/download/file")
	public ResponseEntity<UrlResource> fileDownload(
			@RequestParam(value = "originalFileName", required = true) String originalFileName,
			@RequestParam(value = "storedFileName", required = true) String storedFileName)
			throws MalformedURLException, Exception {
		return userNoticesServiceImpl.fileDownload(originalFileName, storedFileName);
	}
}
