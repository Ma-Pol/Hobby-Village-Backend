package com.hobbyvillage.backend.admin_users;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hobbyvillage.backend.Common;

@Service
public class AdminUsersServiceImpl implements AdminUsersService {

	private AdminUsersMapper mapper;

	public AdminUsersServiceImpl(AdminUsersMapper mapper) {
		this.mapper = mapper;
	}

	private String profUploadPath = Common.uploadDir + "\\Uploaded\\UserProfileImage\\";
	private String reviewUploadPath = Common.uploadDir + "\\Uploaded\\ReviewsInage\\";

	// 이미지 삭제
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

	@Override // 미검색 상태에서 회원 수 조회
	public int getUserCount() {
		return mapper.getUserCount();
	}

	@Override // 검색 상태에서 회원 수 조회
	public int getSearchUserCount(String condition, String keyword) {
		return mapper.getSearchUserCount(condition, keyword);
	}

	@Override // 미검색 상태에서 회원 목록 조회
	public List<AdminUsersDTO> getUserList(String sort, int pageNum) {
		return mapper.getUserList(sort, pageNum);
	}

	@Override // 검색 상태에서 회원 목록 조회
	public List<AdminUsersDTO> getSearchUserList(String condition, String keyword, String sort, int pageNum) {
		return mapper.getSearchUserList(condition, keyword, sort, pageNum);
	}

	@Override // 회원 존재 여부 확인
	public int checkUser(int userCode) {
		return mapper.checkUser(userCode);
	}

	@Override // 회원 상세 정보 조회
	public AdminUsersDTO getUserDetail(int userCode) {
		AdminUsersDTO result = mapper.getUserDetail(userCode);
		String email = result.getEmail();
		AdminUsersDTO address = mapper.getUserAddress(email);

		if (address != null) {
			result.setZipCode(address.getZipCode());
			result.setAddress1(address.getAddress1());
			if (address.getAddress2() == null) {
				result.setAddress2("");
			} else {
				result.setAddress2(address.getAddress2());
			}
		}

		return result;
	}

	// 회원 삭제 준비 과정 1: 판매/위탁 중인 물품 중 완료, 심사 탈락, 철회 완료 상태가 아닌 물품 검색
	@Override
	public int checkRequest(String email) {
		return mapper.checkRequest(email);
	}

	// 회원 삭제 준비 과정 2: 반납 완료되지 않은 주문 물품 검색
	@Override
	public int checkOrderProduct(String email) {
		return mapper.checkOrderProduct(email);
	}

	@Override // 회원 삭제
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
