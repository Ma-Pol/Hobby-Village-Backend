package com.hobbyvillage.backend.user_mypage;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

}
