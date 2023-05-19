package com.hobbyvillage.backend.admin_notices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/m/notices")
public class AdminNoticesController {

	private AdminNoticesServiceImpl adminNoticesServiceImpl;

	public AdminNoticesController(AdminNoticesServiceImpl adminNoticesServiceImpl) {
		this.adminNoticesServiceImpl = adminNoticesServiceImpl;
	}

	@GetMapping("/count")
	public int getNoticeCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int noticeCount;

		// 검색 여부 확인
		if (keyword == null) {
			noticeCount = adminNoticesServiceImpl.getNoticeCount(filter);
		} else {
			noticeCount = adminNoticesServiceImpl.getSearchNoticeCount(filter, keyword);
		}

		return noticeCount;
	}

	@GetMapping("")
	public List<AdminNoticesDTO> getNoticeList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminNoticesDTO> noticeList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			noticeList = adminNoticesServiceImpl.getNoticeList(filter, sort, pageNum);
		} else {
			noticeList = adminNoticesServiceImpl.getSearchNoticeList(filter, keyword, sort, pageNum);
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
			result = adminNoticesServiceImpl.checkNotice(notCodeInt);
		}

		return result;
	}

	// 공지사항 상세 조회 2: 공지사항 상세 조회
	@GetMapping("/noticeDetails/{notCode}")
	public AdminNoticesDTO getNoticeDetail(@PathVariable(value = "notCode", required = true) int notCode) {
		return adminNoticesServiceImpl.getNoticeDetail(notCode);
	}

	// 공지사항 상세 조회 3: 공지사항 파일 조회
	@GetMapping("/noticeFiles/{notCode}")
	public List<AdminNoticesFileDTO> getFileData(@PathVariable(value = "notCode", required = true) int notCode) {
		return adminNoticesServiceImpl.getFileData(notCode);
	}

	// 공지사항 등록
	@PostMapping("/create")
	public int createNotice(@RequestBody AdminNoticesDTO notice) {
		return adminNoticesServiceImpl.createNotice(notice);
	}

	// 파일 받아와서 서버에 저장하기
	@PostMapping("/upload/file/{notCode}")
	public int fileUpload(@PathVariable(value = "notCode", required = true) int notCode,
			@RequestParam(value = "uploadFile", required = true) MultipartFile[] uploadFile) throws IOException {
		return adminNoticesServiceImpl.fileUpload(notCode, uploadFile);
	}

	// 공지사항 파일 다운로드
	@PostMapping("/download/file")
	public ResponseEntity<UrlResource> fileDownload(
			@RequestParam(value = "originalFileName", required = true) String originalFileName,
			@RequestParam(value = "storedFileName", required = true) String storedFileName)
			throws MalformedURLException, Exception {
		return adminNoticesServiceImpl.fileDownload(originalFileName, storedFileName);
	}

	// 공지사항 삭제 (공지사항 삭제, 공지사항 파일명 조회, 파일 삭제, 공지사항 파일명 삭제)
	@DeleteMapping("/delete/{notCode}")
	public int deleteNotice(@PathVariable(value = "notCode", required = true) int notCode) {
		return adminNoticesServiceImpl.deleteNotice(notCode);
	}

	// 공지사항 수정
	@PatchMapping("/modify/{notCode}")
	public int modifyNotice(@PathVariable(value = "notCode", required = true) int notCode,
			@RequestBody AdminNoticesDTO notice) {
		return adminNoticesServiceImpl.modifyNotice(notCode, notice);
	}

	// 공지사항 파일 삭제
	@DeleteMapping("/delete/file/{notCode}")
	public void deleteNoticeFileName(@PathVariable(value = "notCode", required = true) int notCode) {
		adminNoticesServiceImpl.deleteNoticeFileName(notCode);
	}
}
