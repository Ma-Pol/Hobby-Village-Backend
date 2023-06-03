package com.hobbyvillage.backend.user_mypage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/requests/mypages")
public class MyRequestPageController {

	private MyRequestPageServiceImpl myRequestPageServiceImpl;

	public MyRequestPageController(MyRequestPageServiceImpl myRequestPageServiceImpl) {
		this.myRequestPageServiceImpl = myRequestPageServiceImpl;
	}

	// 판매/위탁 신청 개수 조회
	@GetMapping("/count")
	public int getRequestCount(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "filter", required = true) String filter) {
		return myRequestPageServiceImpl.getRequestCount(email, filter);
	}

	// 판매/위탁 신청 목록 조회
	@GetMapping("/lists")
	public List<MyRequestPageDTO> getRequestList(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "pages", required = true) int pages) {
		int pageNum = (pages - 1) * 5;

		return myRequestPageServiceImpl.getRequestList(email, filter, pageNum);
	}

	// 판매/위탁 신청 물품 이미지 파일명 조회
	@GetMapping("/pictures")
	public List<String> getRequestPictures(@RequestParam(value = "reqCode", required = true) int reqCode) {
		return myRequestPageServiceImpl.getRequestPictures(reqCode);
	}

	// 위탁 철회
	@PatchMapping("/withdraw")
	public void withdrawRequest(@RequestParam(value = "reqCode", required = true) int reqCode) {
		myRequestPageServiceImpl.withdrawRequest(reqCode);
	}

	// 계좌정보 수정
	@PatchMapping("/account")
	public void updateAccount(@RequestParam(value = "reqBank", required = true) String reqBank,
			@RequestParam(value = "reqAccountNum", required = true) String reqAccountNum,
			@RequestParam(value = "reqCode", required = true) int reqCode) {
		myRequestPageServiceImpl.updateAccount(reqBank, reqAccountNum, reqCode);
	}

	// 판매/위탁 신청 물품 이미지 불러오기
	@GetMapping("/view-image/{imgName}")
	public ResponseEntity<byte[]> getReqeustFileData(@PathVariable(value = "imgName", required = true) String imgName)
			throws IOException {
		File file = new File(Common.uploadDir + "\\Uploaded\\RequestsFile", imgName);
		ResponseEntity<byte[]> result = null;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

		return result;
	}
}
