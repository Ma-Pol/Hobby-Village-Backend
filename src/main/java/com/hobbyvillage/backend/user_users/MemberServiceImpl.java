package com.hobbyvillage.backend.user_users;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@Transactional
@Service
public class MemberServiceImpl {

	private MemberMapper mapper;

	public MemberServiceImpl(MemberMapper mapper) {
		this.mapper = mapper;
	}

	private String profUploadPath = Common.uploadDir + "\\Uploaded\\UserProfileImage\\";
	private String reviewUploadPath = Common.uploadDir + "\\Uploaded\\ReviewsInage\\";

	private void deletePicture(List<String> fileNames, String path) {

		if (fileNames != null) {
			for (String fileName : fileNames) {
				File filePath = null;

				if (path.equals("REVIEW")) {
					filePath = new File(reviewUploadPath + fileName);
				} else {
					filePath = new File(profUploadPath + fileName);
				}

				filePath.delete();
			}
		}

	}

	// 로그인 과정
	public LoginDTO login(LoginDTO users) {
		LoginDTO result = new LoginDTO();
		result.setNickname("취미빌리지");

		int check = mapper.loginCheck(users);

		if (check == 1) {
			result = mapper.getUserData(users);
		}

		return result;
	}

	// 회원가입: 이메일 중복 체크
	public boolean emailCheck(String email) {
		boolean result = false;

		int check = mapper.emailCheck(email);

		if (check == 1) {
			result = true;
		}

		return result;
	}

	// 회원가입: 닉네임 중복 체크
	public boolean nicknameCheck(String nickname) {
		boolean result = false;

		int check = mapper.nicknameCheck(nickname);

		if (check == 1) {
			result = true;
		}

		return result;
	}

	// 회원가입 과정 1: 사용자 정보 저장
	public int signup(MemberDTO users) {
		int result = mapper.signup(users);

		if (result != 0) {
			result = mapper.signupAddr(users);
		}

		return result;
	}

	public int imageUpload(String email, MultipartFile[] uploadImg) throws IOException {
		int result = 0;

		// 이미지 개수만큼 반복
		for (MultipartFile image : uploadImg) {
			// 값이 없는 이미지가 아닌 경우(정상적인 이미지인 경우)
			if (!image.isEmpty()) {
				// 저장할 파일 명 설정(UUID를 사용해 파일명 중복을 피함)
				String profPicture = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

				// 파일 저장 위치
				File file = new File(profUploadPath, profPicture);

				// 여기서 실제 업로드가 이뤄집니다.
				image.transferTo(file);

				// DB에 파일명을 저장할 경우 이곳에서 처리하기
				mapper.addPicture(email, profPicture);
				result += 1;
			}
		}

		return result;
	}

	// 회원 정보 조회
	public MemberDTO getUserDetail(String email) {
		return mapper.getUserDetail(email);
	}

	// 회원 정보 수정
	public int modifyUser(MemberDTO user) {
		return mapper.modifyMember(user);
	}

	// 회원 탈퇴 준비 과정 1: 판매/위탁 중인 물품 중 완료, 심사 탈락, 철회 완료 상태가 아닌 물품 검색
	public int checkRequest(String email) {
		return mapper.checkRequest(email);
	}

	// 회원 탈퇴 준비 과정 2: 반납 완료되지 않은 주문 물품 검색
	public int checkOrderProduct(String email) {
		return mapper.checkOrderProduct(email);
	}

	// 회원 탈퇴
	public int deleteUser(String email, String nickname, String profPicture) {
		List<String> profile = new ArrayList<>();
		profile.add(profPicture);
		deletePicture(profile, "PROFILE");

		mapper.updateRequest(email);

		mapper.updateProduct(nickname);

		List<String> reviewImages = mapper.getReviewPictures(nickname);
		deletePicture(reviewImages, "REVIEW");

		return mapper.deleteUser(email);
	}

}
