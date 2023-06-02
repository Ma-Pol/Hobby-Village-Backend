package com.hobbyvillage.backend.user_mypage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@Service
public class MyPageServiceImpl implements MyPageService {

	private MyPageMapper mapper;

	public MyPageServiceImpl(MyPageMapper mapper) {
		this.mapper = mapper;
	}

	private String profileUploadPath = Common.uploadDir + "//Uploaded//UserProfileImage//";

	// 유저 프로필 사진 출력
	@Override
	public ResponseEntity<byte[]> printProfPicture(String profPicture) throws IOException {
		File file = new File(profileUploadPath + profPicture);
		ResponseEntity<byte[]> result = null;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

		return result;
	}

	// 유저 적립금 조회
	@Override
	public int getSavedMoney(String email) {
		return mapper.getSavedMoney(email);
	}

	// 유저 쿠폰 개수 조회
	@Override
	public int getCouponCount(String email) {
		return mapper.getCouponCount(email);
	}

	// 유저 쿠폰 리스트 조회
	@Override
	public List<MyPageCouponsDTO> getCouponList(String email) {
		return mapper.getCouponList(email);
	}

	// 유저 쿠폰 삭제
	@Override
	public void deleteCoupon(String email, int couponCode) {
		mapper.deleteCoupon(email, couponCode);
	}

	// 유저 프로필 사진 변경
	@Override
	public String modifyProfPicture(String email, String prevProfPicture, MultipartFile[] uploadImg)
			throws IOException {
		String profPicture = null;

		for (MultipartFile image : uploadImg) {

			if (!image.isEmpty()) {
				// 기존 프로필 사진 삭제
				File filePath = new File(profileUploadPath + prevProfPicture);
				filePath.delete();

				profPicture = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

				// 파일 저장 위치
				File file = new File(profileUploadPath + profPicture);

				// 여기서 실제 업로드가 이뤄집니다.
				image.transferTo(file);

				// DB에 파일명을 저장할 경우 이곳에서 처리하기
				mapper.modifyProfPicture(email, profPicture);
			}
		}

		return profPicture;
	}
	
	@Override
	public int getRequestCountAll(String reqEmail) {
		return mapper.getRequestCountAll(reqEmail);
	}

	@Override
	public List<MyPageRequestDTO> getRequestListAll(String reqEmail, int pageNum) {
		return mapper.getRequestListAll(reqEmail, pageNum);
	}

	@Override
	public int getRequestCount(String reqEmail, String reqProgress) {
		return mapper.getRequestCount(reqEmail, reqProgress);
	}

	@Override
	public List<MyPageRequestDTO> getRequestList(String reqEmail, String reqProgress, int pageNum) {
		return mapper.getRequestList(reqEmail, reqProgress, pageNum);
	}

	@Override
	public List<String> getRequestPictures(String reqCode) {
		return mapper.getRequestPictures(reqCode);
	}

	@Override
	public void withdrawRequest(String reqCode) {
		mapper.withdrawRequest(reqCode);
	}

	@Override
	public void updateAccount(String reqBank, String reqAccountNum, String reqCode) {
		mapper.updateAccount(reqBank, reqAccountNum, reqCode);
	}
	
	
	

}
