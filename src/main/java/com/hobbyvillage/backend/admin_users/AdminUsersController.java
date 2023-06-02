package com.hobbyvillage.backend.admin_users;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/m/users")
public class AdminUsersController {

	private AdminUsersServiceImpl adminUsersServiceImpl;

	public AdminUsersController(AdminUsersServiceImpl adminUsersServiceImpl) {
		this.adminUsersServiceImpl = adminUsersServiceImpl;
	}

	@GetMapping("/count")
	public int getUserCount(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int userCount;

		// 검색 여부 확인
		if (keyword == null) {
			userCount = adminUsersServiceImpl.getUserCount();
		} else {
			userCount = adminUsersServiceImpl.getSearchUserCount(condition, keyword);
		}

		return userCount;
	}

	@GetMapping("")
	public List<AdminUsersDTO> getUserList(@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminUsersDTO> userList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			userList = adminUsersServiceImpl.getUserList(sort, pageNum);
		} else {
			userList = adminUsersServiceImpl.getSearchUserList(condition, keyword, sort, pageNum);
		}

		return userList;
	}

	// 회원 존재 여부 확인
	@GetMapping("/check/{userCode}")
	public int checkUser(@PathVariable(value = "userCode", required = true) String userCode) {
		int result = 0;

		// userCode가 숫자인지 확인
		if (userCode.matches("-?\\d+")) {
			int userCodeInt = Integer.parseInt(userCode);
			result = adminUsersServiceImpl.checkUser(userCodeInt);
		}

		return result;
	}

	// 회원 상세 정보 조회
	@GetMapping("/userDetails/{userCode}")
	public AdminUsersDTO getUserDetail(@PathVariable(value = "userCode", required = true) int userCode) {
		return adminUsersServiceImpl.getUserDetail(userCode);
	}

	// 회원 프로필 사진 출력
	@GetMapping("/profPicture/{profPicture}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "profPicture", required = true) String profPicture) {
		File file = new File(Common.uploadDir + "//Uploaded//UserProfileImage", profPicture);
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

	// 회원 탈퇴 준비 과정 1: 판매/위탁 중인 물품 중 완료, 심사 탈락, 철회 완료 상태가 아닌 물품 검색
	@GetMapping("/delete/checkRequest")
	public int checkRequest(@RequestParam(value = "email", required = true) String email) {
		return adminUsersServiceImpl.checkRequest(email);
	}

	// 회원 탈퇴 준비 과정 2: 반납 완료되지 않은 주문 물품 검색
	@GetMapping("/delete/checkOrderProduct")
	public int checkOrderProduct(@RequestParam(value = "email", required = true) String email) {
		return adminUsersServiceImpl.checkOrderProduct(email);
	}

	// 회원 삭제
	@DeleteMapping("/delete")
	public int deleteUser(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "nickname", required = true) String nickname,
			@RequestParam(value = "profPicutre", required = true) String profPicture) {
		return adminUsersServiceImpl.deleteUser(email, nickname, profPicture);
	}

}
