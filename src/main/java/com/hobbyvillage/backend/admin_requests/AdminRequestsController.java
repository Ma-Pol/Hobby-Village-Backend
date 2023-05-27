package com.hobbyvillage.backend.admin_requests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/m/requests")
public class AdminRequestsController {

	private AdminRequestsServiceImpl adminRequestsServiceImpl;

	public AdminRequestsController(AdminRequestsServiceImpl adminRequestsServiceImpl) {
		this.adminRequestsServiceImpl = adminRequestsServiceImpl;
	}

	@GetMapping("/{category}/count")
	public int getProductCount(@RequestParam(value = "filter", required = true) String filter,
			@PathVariable("category") String category,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int requestCount;

		// 검색 여부 확인
		if (keyword == null) {
			requestCount = adminRequestsServiceImpl.getRequestCount(filter, category);
		} else {
			requestCount = adminRequestsServiceImpl.getSearchRequestCount(filter, category, condition, keyword);
		}

		return requestCount;
	}

	@GetMapping("/{category}")
	public List<AdminRequestsDTO> getProductList(@RequestParam(value = "filter", required = true) String filter,
			@PathVariable("category") String category, @RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminRequestsDTO> requestList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			requestList = adminRequestsServiceImpl.getRequestList(filter, category, sort, pageNum);
		} else {
			requestList = adminRequestsServiceImpl.getSearchRequestList(filter, category, condition, keyword, sort,
					pageNum);
		}

		return requestList;
	}

	@GetMapping("/check/{reqCode}")
	public int checkReqeust(@PathVariable(value = "reqCode", required = true) String reqCode) {
		int result = 0;

		// reqCode가 숫자인지 확인
		if (reqCode.matches("-?\\d+")) {
			int reqCodeInt = Integer.parseInt(reqCode);
			result = adminRequestsServiceImpl.checkReqeust(reqCodeInt);
		}

		return result;
	}

	// 신청 상세 정보 조회
	@GetMapping("/requestDetails/{reqCode}")
	public AdminRequestsDetailsDTO getRequestDetail(@PathVariable(value = "reqCode", required = true) int reqCode) {
		return adminRequestsServiceImpl.getRequestDetail(reqCode);
	}

	// 신청 파일명 조회
	@GetMapping("/requestFileList/{reqCode}")
	public List<String> getRequestFileList(@PathVariable(value = "reqCode", required = true) int reqCode) {
		return adminRequestsServiceImpl.getRequestFileList(reqCode);
	}

	// 신청 파일 데이터 업로드
	@GetMapping("/upload/{fileName}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "fileName", required = true) String fileName) {
		File file = new File(Common.uploadDir + "\\Uploaded\\RequestsFile", fileName);
		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 신청 진행 상황 업데이트
	@PatchMapping("/updateProgress/{reqCode}")
	public int updateRequestProgress(@PathVariable(value = "reqCode", required = true) int reqCode,
			@RequestBody Map<String, String> reqeustData) {
		String reqProgress = reqeustData.get("reqProgress");
		String reqTitle = reqeustData.get("reqTitle");
		String reqPhone = reqeustData.get("reqPhone");

		return adminRequestsServiceImpl.updateRequestProgress(reqCode, reqProgress, reqTitle, reqPhone);
	}

	// 심사 탈락 처리
	@PatchMapping("/rejectProgress")
	public int rejectRequestProgress(@RequestBody AdminRequestsRejectDTO rejectData) {
		return adminRequestsServiceImpl.rejectRequestProgress(rejectData);
	}

	// 위탁 철회 요청 승인 처리
	@PatchMapping("/cancelProgress/{reqCode}")
	public int cancelRequestProgress(@PathVariable(value = "reqCode", required = true) int reqCode) {
		return adminRequestsServiceImpl.cancelRequestProgress(reqCode);
	}

	// 위탁 철회 요청 거부 처리
	@PatchMapping("/rejectCancelProgress/{reqCode}")
	public int rejectCancelRequestProgress(@PathVariable(value = "reqCode", required = true) int reqCode) {
		return adminRequestsServiceImpl.rejectCancelRequestProgress(reqCode);
	}
}
