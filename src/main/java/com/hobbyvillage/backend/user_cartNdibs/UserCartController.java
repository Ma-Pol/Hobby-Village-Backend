package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class UserCartController {

	private UserCartServiceImpl userCartServiceImpl;

	public UserCartController(UserCartServiceImpl userCartServiceImpl) {
		this.userCartServiceImpl = userCartServiceImpl;
	}

	// 장바구니 상품 개수 조회
	@GetMapping("/count")
	public UserCartCountDTO getCartCount(@RequestParam(value = "email", required = true) String email) {
		return userCartServiceImpl.getCartCount(email);
	}

	// 전체 장바구니 상품 목록 조회
	@GetMapping("/lists")
	public List<UserCartDTO> getCartList(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "category", required = true) String filter) {
		return userCartServiceImpl.getCartList(email, filter);
	}

	// 장바구니 상품 대여기간 변경
	@PatchMapping("/period")
	public void modifyPeriod(@RequestBody UserCartPeriodDTO periodData) {
		userCartServiceImpl.modifyPeriod(periodData);
	}

	// 장바구니 단일 상품 삭제
	@DeleteMapping("/products")
	public int deleteCart(@RequestParam(value = "cartCode", required = true) Integer cartCode) {
		return userCartServiceImpl.deleteCart(cartCode);
	}

	// 장바구니 선택 상품 삭제
	@PostMapping("/lists")
	public int deleteSelectedCart(@RequestBody List<UserCartCodeDTO> cartList) {
		return userCartServiceImpl.deleteSelectedCart(cartList);
	}

	// 상품 이미지 출력
	@GetMapping("/image/{prodPicture}")
	public ResponseEntity<byte[]> getProdPicture(
			@PathVariable(value = "prodPicture", required = true) String prodPicture) {

		return userCartServiceImpl.getProdPicture(prodPicture);
	}
}
