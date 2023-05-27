package com.hobbyvillage.backend.user_requests;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/requests")
public class UserRequestsController {

	private UserReqeustsServiceImpl userReqeustsServiceImpl;

	public UserRequestsController(UserReqeustsServiceImpl userReqeustsServiceImpl) {
		this.userReqeustsServiceImpl = userReqeustsServiceImpl;
	}

	@GetMapping("/categories")
	public List<String> getCategories() {
		return userReqeustsServiceImpl.getCategories();
	}

	// 신청 내용 저장 및 reqCode 조회
	@PostMapping("/create")
	public int createReqeust(@RequestBody UserRequestsDTO request) {
		return userReqeustsServiceImpl.createRequest(request);
	}

	// 이미지 파일 받아와서 서버에 저장하기
	@PostMapping("/upload/img/{reqCode}")
	public int imageUpload(@PathVariable(value = "reqCode", required = true) int reqCode,
			@RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg) throws IOException {
		String uploadPath = Common.uploadDir + "\\Uploaded\\RequestsFile";
		int result = 0;

		// 이미지 개수만큼 반복
		for (MultipartFile image : uploadImg) {

			// 값이 없는 이미지가 아닌 경우(정상적인 이미지인 경우)
			if (!image.isEmpty()) {
				// 저장할 파일명 설정(UUID를 사용해 파일명 중복을 피함)
				String storedFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

				// 파일 저장 위치 + 파일명 설정
				File fileName = new File(uploadPath, storedFileName);

				// 파일 저장
				image.transferTo(fileName);

				// DB에 파일명을 저장할 경우 이곳에서 처리하기
				userReqeustsServiceImpl.insertFileName(reqCode, storedFileName);

				result += 1;
			}
		}

		return result;
	}
}
