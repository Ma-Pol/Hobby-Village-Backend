package com.hobbyvillage.backend.user_users;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@RestController
public class MemberController {

	private MemberServiceImpl memberServiceImpl;

	public MemberController(MemberServiceImpl memberServiceImpl) {
		this.memberServiceImpl = memberServiceImpl;
	}

	// 로그인 과정
	@PostMapping("/loginCheck")
	public LoginDTO login(@RequestBody LoginDTO users) throws Exception {
		return memberServiceImpl.login(users);
	}

	// 프로필 사진 로드
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

	// 회원가입: 이메일/닉네임 중복 검사
	@GetMapping("/signup/check")
	public boolean dataCheck(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "nickname", required = false) String nickname) {
		boolean result = false;

		if (email != null) {
			result = memberServiceImpl.emailCheck(email);
		} else if (nickname != null) {
			result = memberServiceImpl.nicknameCheck(nickname);
		}

		return result;
	}

	// 회원가입 과정 1: 사용자 정보 저장
	@PostMapping("/signup")
	public int signup(@RequestBody MemberDTO users) throws Exception {
		return memberServiceImpl.signup(users);
	}

	// 회원가입 과정 2: 프로필 사진 저장
	@PostMapping("/signup/upload/{email}")
	public int imageUpload(@PathVariable(value = "email", required = true) String email,
			@RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg) throws IOException {
		return memberServiceImpl.imageUpload(email, uploadImg);
	}

	// 회원 정보 조회
	@GetMapping("/users/modify")
	public MemberDTO getUserDetail(@RequestParam(value = "email", required = true) String email) throws Exception {
		return memberServiceImpl.getUserDetail(email);
	}

	// 회원 정보 수정
	@PatchMapping("/users/modify")
	public int modifyUser(@RequestBody MemberDTO user) throws Exception {
		return memberServiceImpl.modifyUser(user);
	}

	// 회원 탈퇴 준비 과정 1: 판매/위탁 중인 물품 중 완료, 심사 탈락, 철회 완료 상태가 아닌 물품 검색
	@GetMapping("/users/delete/checkRequest")
	public int checkRequest(@RequestParam(value = "email", required = true) String email) {
		return memberServiceImpl.checkRequest(email);
	}

	// 회원 탈퇴 준비 과정 2: 반납 완료되지 않은 주문 물품 검색
	@GetMapping("/users/delete/checkOrderProduct")
	public int checkOrderProduct(@RequestParam(value = "email", required = true) String email) {
		return memberServiceImpl.checkOrderProduct(email);
	}

	// 회원 탈퇴
	@DeleteMapping("/users/delete")
	public int deleteUser(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "nickname", required = true) String nickname,
			@RequestParam(value = "profPicutre", required = true) String profPicture) {
		return memberServiceImpl.deleteUser(email, nickname, profPicture);
	}
}
