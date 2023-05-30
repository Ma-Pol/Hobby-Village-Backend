package com.hobbyvillage.backend.user_mypage;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/mypages")
public class MyOrderController {

	private MyOrderServiceImpl myOrderServiceImpl;

	public MyOrderController(MyOrderServiceImpl myOrderServiceImpl) {
		this.myOrderServiceImpl = myOrderServiceImpl;
	}

	// 주문 상태 별 주문 상품 개수 조회
	@GetMapping("/{email}/count")
	public List<Integer> getOdrStateCount(@PathVariable(value = "email", required = true) String email) {
		return myOrderServiceImpl.getOdrStateCount(email);
	}

	// 주문 상태 별 주문 목록 조회
	@GetMapping("/{email}/lists")
	public List<MyOrderListDTO> getOrderList(@PathVariable(value = "email", required = true) String email,
			@RequestParam(value = "odrState", required = true) String odrState) {
		return myOrderServiceImpl.getOrderList(email, odrState);
	}

	// 주문 별 상품 이미지 명 조회
	@GetMapping("/prodPicture/{prodCode}")
	public String getProdPicture(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return myOrderServiceImpl.getProdPicture(prodCode);
	}

	// 리뷰 작성 여부 확인
	@GetMapping("/review/{prodCode}/{nickname}")
	public boolean checkReviewed(@PathVariable(value = "prodCode", required = true) String prodCode,
			@PathVariable(value = "nickname", required = true) String nickname) {
		return myOrderServiceImpl.checkReviewed(prodCode, nickname);
	}

	// 이미지 출력
	@GetMapping("/product/{prodPicture}")
	public ResponseEntity<byte[]> printProdPicture(
			@PathVariable(value = "prodPicture", required = true) String prodPicture) throws IOException {
		return myOrderServiceImpl.printProdPicture(prodPicture);
	}

	// 주문 물품 반납 처리
	@PatchMapping("/return/{opCode}")
	public int returnProduct(@PathVariable(value = "opCode", required = true) int opCode) {
		return myOrderServiceImpl.returnProduct(opCode);
	}

	// 주문 물품 취소 처리
	@PatchMapping("/cancel/{opCode}")
	public int cancelProduct(@PathVariable(value = "opCode", required = true) int opCode) {
		return myOrderServiceImpl.cancelProduct(opCode);
	}
}
