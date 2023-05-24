package com.hobbyvillage.backend.user_users;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.UploadDir;

@RestController
public class MemberController {

	private MemberServiceImpl memberServiceImpl;

	public MemberController(MemberServiceImpl memberServiceImpl) {
		this.memberServiceImpl = memberServiceImpl;
	}

	@PostMapping("/loginCheck")
	public LoginDTO login(@RequestBody LoginDTO users) throws Exception {
		return memberServiceImpl.login(users);
	}

	@GetMapping("/profPicture/{profPicture}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "profPicture", required = true) String profPicture) {
		File file = new File(UploadDir.uploadDir + "\\Uploaded\\UserProfileImage", profPicture);
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

	@RequestMapping("/users/register/nickname")
	public boolean nicknameCheck(@RequestBody LoginDTO users) throws Exception {
		int res = memberServiceImpl.nicknameCheck(users);

		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping("/users/register/email")
	public boolean emailCheck(@RequestBody LoginDTO users) throws Exception {
		int res = memberServiceImpl.emailCheck(users);

		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping("/signup")
	public int signup(@RequestBody LoginDTO users) throws Exception {
		int res = memberServiceImpl.signup(users);
		return res;
	}

	@RequestMapping("/users/{email}/modify")
	public LoginDTO getUserDetail(@PathVariable(value = "email", required = true) String email) throws Exception {
		LoginDTO UserDetail = memberServiceImpl.getUserDetail(email);
		return UserDetail;
	}

	@PostMapping("/users/modify")
	public int modifyMember(@RequestBody LoginDTO users) {
		int res = memberServiceImpl.modifyMember(users);
		return res;
	}

	@PostMapping("/users/withdrawal")
	public int deleteMember(@RequestBody LoginDTO users) {
		int res = memberServiceImpl.deleteMember(users);
		return res;
	}

}
