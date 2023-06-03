package com.hobbyvillage.backend.user_cartNdibs;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.hobbyvillage.backend.Common;

@Service
public class UserCartServiceImpl implements UserCartService {

	private UserCartMapper mapper;

	public UserCartServiceImpl(UserCartMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("all")) {
			filter = "";
		} else if (filter.equals("product")) {
			filter = "AND p.prodBrand IS NULL";
		} else if (filter.equals("brand")) {
			filter = "AND p.prodBrand IS NOT NULL";
		} else {
			filter = "잘못된 요청";
		}

		return filter;
	}

	// 장바구니 상품 개수 조회
	@Override
	public UserCartCountDTO getCartCount(String email) {
		int all = mapper.getAllCartCount(email);
		int brand = mapper.getBrandCartCount(email);

		UserCartCountDTO count = new UserCartCountDTO(all, all - brand, brand);

		return count;
	}

	// 전체 장바구니 상품 목록 조회
	@Override
	public List<UserCartDTO> getCartList(String email, String filter) {
		filter = filtering(filter);

		if (filter.equals("잘못된 요청")) {
			return null;
		}

		return mapper.getCartList(email, filter);
	}

	// 장바구니 상품 대여기간 변경
	@Override
	public void modifyPeriod(UserCartPeriodDTO periodData) {
		mapper.modifyPeriod(periodData);
	}

	// 장바구니 단일 상품 삭제
	@Override
	public int deleteCart(int cartCode) {
		return mapper.deleteCart(cartCode);
	}

	// 장바구니 선택 상품 삭제
	@Override
	public int deleteSelectedCart(List<UserCartCodeDTO> cartList) {
		int result = 0;

		for (UserCartCodeDTO userCartCode : cartList) {
			deleteCart(userCartCode.getCartCode());
			result++;
		}

		return result;
	}

	// 상품 이미지 출력
	@Override
	public ResponseEntity<byte[]> getProdPicture(String prodPicture) {
		ResponseEntity<byte[]> result = null;

		if (!prodPicture.equals("undefined")) {

			File file = new File(Common.uploadDir + "\\Uploaded\\ProductsImage", prodPicture);
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
