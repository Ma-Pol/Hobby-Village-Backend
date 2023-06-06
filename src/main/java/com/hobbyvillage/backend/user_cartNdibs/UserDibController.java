package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dibs")
public class UserDibController {

	private UserDibServiceimpl userDibServiceimpl;

	public UserDibController(UserDibServiceimpl userDibServiceimpl) {
		this.userDibServiceimpl = userDibServiceimpl;
	}

	// 찜 상품 개수 조회
	@GetMapping("/count")
	public UserDibCountDTO getDibCount(@RequestParam(value = "email", required = true) String email) {
		return userDibServiceimpl.getDibCount(email);
	}

	// 전체 찜 상품 목록 조회
	@GetMapping("/lists")
	public List<UserDibDTO> getDibList(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "category", required = true) String filter) {
		return userDibServiceimpl.getDibList(email, filter);
	}

	// 찜 단일 상품 삭제
	@DeleteMapping("/products")
	public int deleteDib(@RequestParam(value = "dibCode", required = true) Integer dibCode,
			@RequestParam(value = "prodCode", required = true) String prodCode) {
		return userDibServiceimpl.deleteDib(dibCode, prodCode);
	}

	// 찜 선택 상품 삭제
	@PostMapping("/lists")
	public int deleteSelectedDib(@RequestBody List<UserDibCodeDTO> dibList) {
		return userDibServiceimpl.deleteSelectedDib(dibList);
	}

	// 찜 선택 상품 장바구니 추가
	@PostMapping("/carts")
	public int insertSelectedDib(@RequestParam(value = "email", required = true) String email,
			@RequestBody List<UserDibDTO> dibList) {
		return userDibServiceimpl.insertSelectedDib(email, dibList);
	}
}
