package com.hobbyvillage.backend.user_cartNdibs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.Common;

@RestController
public class cartController {

	private cartServiceImpl cartServiceImpl;

	public cartController(cartServiceImpl cartServiceImpl) {
		this.cartServiceImpl = cartServiceImpl;
	}

	// 장바구니 리스트 조회
	@RequestMapping("/carts/{email}/lists")
	public List<cartDTO> getcartlists(@PathVariable("email") String email,
			@RequestParam(value = "category", required = true) String category) {
		return cartServiceImpl.getcartlists(email, category);
	}

	// 장바구니 리스트 조회(카테고리 수량체크)
	@RequestMapping("/carts/{email}/lists/item")
	public List<cartDTO> getcategorylist(@PathVariable("email") String email) {
		return cartServiceImpl.getcategorylist(email);
	}

	// 상품 사진 조회
	@RequestMapping("/carts/getPhoto")
	public String getcartitems(@RequestParam(value = "prodCode", required = true) String prodCode) {
		return cartServiceImpl.getcartitems(prodCode);
	}

	// 대여기간 변경
	@PostMapping("/carts/{period}/modify/{cartCode}")
	public int modifyperiod(@PathVariable("cartCode") int cartCode, @PathVariable("period") int period) {
		return cartServiceImpl.modifyperiod(cartCode, period);
	}

	// 장바구니 삭제
	@DeleteMapping("/carts/delete/{cartCode}")
	public int deletecart(@PathVariable(value = "cartCode", required = true) int cartCode) {
		return cartServiceImpl.deletecart(cartCode);
	}

	// 장바구니 선택 품목 삭제
	@DeleteMapping("/carts/delete/lists/{cartCode}")
	public List<cartDTO> deletecartlist(@PathVariable(value = "cartCode", required = true) int cartCode) {
		return cartServiceImpl.deletecartlist(cartCode);
	}

	// 이미지 출력
	@GetMapping("/carts/viewImage/{imageName}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "imageName", required = true) String imageName) {
		ResponseEntity<byte[]> result = null;

		if (!imageName.equals("undefined")) {

			File file = new File(Common.uploadDir + "\\Uploaded\\ProductsImage", imageName);
			result = null;

			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

}
