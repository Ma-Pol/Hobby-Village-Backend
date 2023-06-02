package com.hobbyvillage.backend.user_mypage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/users/mypages")
public class MyPageController {

	private MyPageServiceImpl myPageServiceImpl;

	public MyPageController(MyPageServiceImpl myPageServiceImpl) {
		this.myPageServiceImpl = myPageServiceImpl;
	}

	// 프로필 사진 출력
	@GetMapping("/profile/{profPicture}")
	public ResponseEntity<byte[]> printProfPicture(
			@PathVariable(value = "profPicture", required = true) String profPicture) throws IOException {
		return myPageServiceImpl.printProfPicture(profPicture);
	}

	// 유저 적립금 조회
	@GetMapping("/savedMoney/{email}")
	public int getSavedMoney(@PathVariable(value = "email", required = true) String email) {
		return myPageServiceImpl.getSavedMoney(email);
	}

	// 유저 쿠폰 개수 조회
	@GetMapping("/couponCount/{email}")
	public int getCouponCount(@PathVariable(value = "email", required = true) String email) {
		return myPageServiceImpl.getCouponCount(email);
	}

	// 유저 쿠폰 리스트 조회
	@GetMapping("/couponList/{email}")
	public List<MyPageCouponsDTO> getCouponList(@PathVariable(value = "email", required = true) String email) {
		return myPageServiceImpl.getCouponList(email);
	}

	// 유저 쿠폰 삭제
	@DeleteMapping("/couponList/{email}")
	public void deleteCoupon(@PathVariable(value = "email", required = true) String email,
			@RequestParam(value = "couponCode", required = true) int couponCode) {
		myPageServiceImpl.deleteCoupon(email, couponCode);
	}

	// 유저 프로필 사진 변경
	@PatchMapping("/profPicture/{email}/{prevProfPicture}")
	public String modifyProfPicture(@PathVariable(value = "email", required = true) String email,
			@PathVariable(value = "prevProfPicture", required = true) String prevProfPicture,
			@RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg) throws IOException {
		return myPageServiceImpl.modifyProfPicture(email, prevProfPicture, uploadImg);
	}
	
	// ------------------------------
	
	// 판매/위탁 신청 개수 조회
	@GetMapping("/request/count")
	public int getRequestCount(@RequestParam(value="userEmail") String userEmail, @RequestParam(value="filter") String filter) {
		
		if (filter.equals("전체")) {
			return myPageServiceImpl.getRequestCountAll(userEmail);
		} else {
			return myPageServiceImpl.getRequestCount(userEmail, filter);
		}
	}
	
	// 판매/위탁 신청 목록 조회
	@GetMapping("/request/list")
	public List<MyPageRequestDTO> getRequestList(@RequestParam(value="userEmail") String userEmail, @RequestParam(value="filter") String filter, @RequestParam(value="pages") int pages){
		int pageNum = (pages - 1) * 5;
		if (filter.equals("전체")) {
			return myPageServiceImpl.getRequestListAll(userEmail, pageNum);
		} else {
			return myPageServiceImpl.getRequestList(userEmail, filter, pageNum);
		}
	}
	
	// 판매/위탁 신청 물품 이미지 파일명 조회
	@GetMapping("/request/pictures")
	public List<String> getRequestPictures(@RequestParam(value="reqCode") String reqCode) {
		return myPageServiceImpl.getRequestPictures(reqCode);
	}
	
	// 판매/위탁 신청 물품 이미지 불러오기 
	@GetMapping("/upload/{fileName}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "fileName", required = true) String fileName) {
		File file = new File(Common.uploadDir + "//Uploaded//RequestsFile", fileName);
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
	
	@GetMapping("/request/withdraw")
	public void withdrawRequest(@RequestParam(value="reqCode") String reqCode) {
		myPageServiceImpl.withdrawRequest(reqCode);
	}
	
	@GetMapping("/request/updateAccount")
	public void updateAccount(@RequestParam(value="reqBank") String reqBank, @RequestParam(value="reqAccountNum") String reqAccountNum, 
			@RequestParam(value="reqCode") String reqCode) {
		myPageServiceImpl.updateAccount(reqBank, reqAccountNum, reqCode);
	}
}
