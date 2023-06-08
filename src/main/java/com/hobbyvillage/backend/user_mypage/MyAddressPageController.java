package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses/mypages")
public class MyAddressPageController {

	private MyAddressPageServiceImpl myAddressPageServiceImpl;

	public MyAddressPageController(MyAddressPageServiceImpl myAddressPageServiceImpl) {
		this.myAddressPageServiceImpl = myAddressPageServiceImpl;
	}

	// 배송지 목록 조회
	@GetMapping("/lists")
	public List<MyAddressPageDTO> getAddressList(@RequestParam(value = "email", required = true) String email) {
		return myAddressPageServiceImpl.getAddressList(email);
	}

	// 배송지 접근 권한 조회
	@GetMapping("/check")
	public int checkAddress(@RequestParam(value = "addressCode", required = true) int addressCode,
			@RequestParam(value = "email", required = true) String email) {
		return myAddressPageServiceImpl.checkAddress(addressCode, email);
	}

	// 배송지 상세 조회
	@GetMapping("")
	public MyAddressPageDTO getAddressDetail(@RequestParam(value = "addressCode", required = true) int addressCode) {
		return myAddressPageServiceImpl.getAddressDetail(addressCode);
	}

	// 배송지 삭제
	@DeleteMapping("")
	public int deleteAddress(@RequestParam(value = "addressCode", required = true) int addressCode) {
		return myAddressPageServiceImpl.deleteAddress(addressCode);
	}

	// 배송지 등록
	@PostMapping("")
	public int createAddress(@RequestBody MyAddressPageDTO addressData) {
		return myAddressPageServiceImpl.createAddress(addressData);
	}

	// 배송지 수정
	@PatchMapping("")
	public int modifyAddress(@RequestBody MyAddressPageDTO addressData) {
		return myAddressPageServiceImpl.modifyAddress(addressData);
	}
}
