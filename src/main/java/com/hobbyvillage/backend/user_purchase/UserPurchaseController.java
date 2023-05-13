package com.hobbyvillage.backend.user_purchase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.UploadDir;

@RestController
@RequestMapping("/purchase")
public class UserPurchaseController {

	private UserPurchaseServiceImpl userPurchaseServiceImpl;

	public UserPurchaseController(UserPurchaseServiceImpl userPurchaseServiceImpl) {
		this.userPurchaseServiceImpl = userPurchaseServiceImpl;
	}

	// 상품의 대여 상태 체크
	@GetMapping("/productState/{prodCode}")
	public int getProductState(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return userPurchaseServiceImpl.getProductState(prodCode);
	}

	// state에 저장된 상품 정보와 실제 상품 정보 비교
	@GetMapping("/productInfo")
	public int checkProductInfo(@RequestParam(value = "prodCode", required = true) String prodCode,
			@RequestParam(value = "prodPrice", required = true) int prodPrice,
			@RequestParam(value = "prodShipping", required = true) int prodShipping,
			@RequestParam(value = "prodHost", required = true) String prodHost,
			@RequestParam(value = "period", required = true) int period) {

		return userPurchaseServiceImpl.checkProductInfo(prodCode, prodPrice, prodShipping, prodHost, period);
	}

	// 회원 정보 조회
	@GetMapping("/{email}")
	public UserPurchaseUserDTO getUserInfo(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getUserInfo(email);
	}

	// 회원의 배송지 목록 조회
	@GetMapping("/addresses/{email}")
	public List<UserPurchaseAddressDTO> getAddressList(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getAddressList(email);
	}

	// 회원의 쿠폰 목록 조회
	@GetMapping("/coupons/{email}")
	public List<UserPurchaseCouponDTO> getCouponList(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getCouponList(email);
	}

	// 상품 사진 출력
	@GetMapping("/pictures/{prodPicture}")
	public ResponseEntity<byte[]> getProductImgData(
			@PathVariable(value = "prodPicture", required = true) String prodPicture) {
		File file = new File(UploadDir.uploadDir + "\\Uploaded\\ProductsImage", prodPicture);
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

	// 상품의 대여 상태 변경 (미대여 -> 대여)
	@PatchMapping("/productState/{prodCode}")
	public int updateProductState(@PathVariable(value = "prodCode", required = true) String prodCode) {
		System.out.println("상품 대여 상태 변경");
		return userPurchaseServiceImpl.updateProductState(prodCode);
	}

	// 추가 결제인 경우 이전 주문번호 조회
	@GetMapping("/prevOdrNumber/{prodCode}")
	public String getPrevOdrNumber(@PathVariable(value = "prodCode", required = true) String prodCode) {
		System.out.println("추가 결제인 경우 이전 주문번호 조회");
		return userPurchaseServiceImpl.getPrevOdrNumber(prodCode);
	}

	// 실 결제 진행 전 주문 정보 임시 저장(최종 결제 후 결제 데이터 검증용)
	@PostMapping("/preInsertOrder/{prevPage}")
	public int purchasePreProcess(@RequestBody UserPurchaseProcessDTO data,
			@PathVariable(value = "prevPage") String prevPage) {
		System.out.println("실 결제 진행 전 주문 정보 임시 저장");
		return userPurchaseServiceImpl.purchasePreProcess(data, prevPage);
	}

	// 실 결제 진행 전 import 측에 결제 금액 사전 등록 (해당 금액과 실 결제 금액이 다르면 결제가 중단됨)
	@PostMapping("/payments/prepare")
	public int paymentsPrepare(@RequestBody UserPurchaseProcessDTO data) {
		System.out.println("실 결제 진행 전 import측에 금액 사전 등록");
		String odrNumber = data.getOdrNumber();
		int amount = data.getExactPrice();
		int result = 1;

		try {
			String token = userPurchaseServiceImpl.getImportToken();
			result = userPurchaseServiceImpl.paymentsPrepare(odrNumber, amount, token);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result; // 정상 동작했을 경우 0 return
	}

	// 결제 성공 시 결제 금액 검증 과정 수행
	@GetMapping("/compare/{odrNumber}/{imp_uid}/{paid_amount}/{prevPage}")
	public int compareOrderData(@PathVariable(value = "odrNumber", required = true) String odrNumber,
			@PathVariable(value = "imp_uid", required = true) String imp_uid,
			@PathVariable(value = "paid_amount", required = true) int paid_amount,
			@PathVariable(value = "prevPage", required = true) String prevPage) throws IOException {
		System.out.println("결제 성공. 금액 검증 과정 수행 시작");
		int result = 0;

		// 1. paid_amount와 odrNumber를 통해 DB에서 받아 온 DB 내 exactPrice를 비교해서 같은지 확인
		result = userPurchaseServiceImpl.compareToDatabase(odrNumber, paid_amount, prevPage);

		// 1번 비교 결과(1 or 0)가 일치하면 수행
		if (result == 1) {
			System.out.println("1차 검증 성공");

			// 2. DB 내 exactPrice와 import에 요청해서 받아온 실제 결제 금액을 비교해서 같은지 확인
			String token = userPurchaseServiceImpl.getImportToken();
			result = userPurchaseServiceImpl.compareToImport(odrNumber, paid_amount, prevPage, imp_uid, token);

		}
		if (result == 1) {
			System.out.println("2차 검증 성공");
		}
		System.out.println("최종 검증 결과값" + result);

		return result;
	}

	// 결제 성공 시 상품 대여 횟수 증가
	@PatchMapping("/success/increaseRentalCount/{prodCode}/{rentalPeriod}")
	public int increaseRentalCount(@PathVariable(value = "prodCode", required = true) String prodCode,
			@PathVariable(value = "rentalPeriod", required = true) int rentalPeriod) {
		System.out.println("결제 성공. 상품 대여 횟수 증가");
		int rentalCount = rentalPeriod / 7;

		return userPurchaseServiceImpl.increaseRentalCount(prodCode, rentalCount);
	}

	// 결제 성공 시 유저 적립금 차감
	@PatchMapping("/success/decreaseSavedMoney/{email}/{exactSavedMoney}")
	public int decreaseSavedMoney(@PathVariable(value = "email", required = true) String email,
			@PathVariable(value = "exactSavedMoney", required = true) int exactSavedMoney) {
		System.out.println("결제 성공. 유저 적립금 차감");
		return userPurchaseServiceImpl.decreaseSavedMoney(email, exactSavedMoney);
	}

	// 결제 성공 시 유저 쿠폰 데이터 삭제
	@DeleteMapping("/success/deleteCoupon/{email}/{couponCode}")
	public int deleteCoupon(@PathVariable(value = "email", required = true) String email,
			@PathVariable(value = "couponCode", required = true) int couponCode) {
		System.out.println("결제 성공. 유저 쿠폰 삭제");
		return userPurchaseServiceImpl.deleteCoupon(email, couponCode);
	}

	// 결제 성공 시 주문 상품 데이터 추가 (+ 추가 주문의 경우 기존 주문 상품의 rentalPeriod, deadline 변경)
	@PostMapping("/success/purchaseFinalProcess/{prevPage}")
	public int purchaseFinalProcess(@RequestBody UserPurchaseProcessDTO data,
			@PathVariable(value = "prevPage") String prevPage) {
		System.out.println("결제 성공. orderProducts 데이터 추가");
		return userPurchaseServiceImpl.purchaseFinalProcess(data, prevPage);
	}

	// 결제 실패 시 상품의 대여 상태 변경 (대여 -> 미대여)
	@PatchMapping("/fail/productState/{prodCode}")
	public int updateProductStateFail(@PathVariable(value = "prodCode", required = true) String prodCode) {
		System.out.println("결제 실패. 상품의 대여 상태 변경");
		return userPurchaseServiceImpl.updateProductStateFailed(prodCode);
	}

	// 결제 실패 시 임시 저장된 주문 정보 삭제
	@DeleteMapping("/fail/deleteOrder/{odrNumber}/{prevPage}")
	public int deleteOrder(@PathVariable(value = "odrNumber", required = true) String odrNumber,
			@PathVariable(value = "prevPage", required = true) String prevPage) {
		System.out.println("결제 실패. 임시 저장된 주문 정보 삭제");
		return userPurchaseServiceImpl.deleteOrder(odrNumber, prevPage);
	}

	// 결제 실패 시 실 주문 취소
	@GetMapping("/fail/cancelOrder/{imp_uid}")
	public int cancelOrder(@PathVariable(value = "imp_uid", required = true) String imp_uid) throws IOException {
		System.out.println("결제 실패. 실제 주문내역 삭제");
		int result;

		String token = userPurchaseServiceImpl.getImportToken();
		result = userPurchaseServiceImpl.cancelOrder(imp_uid, token);

		return result;
	}

}
