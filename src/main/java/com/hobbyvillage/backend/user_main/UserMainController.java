package com.hobbyvillage.backend.user_main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/main")
public class UserMainController {

	private UserMainServiceImpl userMainServiceImpl;

	public UserMainController(UserMainServiceImpl userMainServiceImpl) {
		this.userMainServiceImpl = userMainServiceImpl;
	}

	// 인기 상품 조회
	@GetMapping("/most-popular-products")
	public List<UserMainProductDTO> getmostpopularproducts() {
		return userMainServiceImpl.getPopularProductList();
	}

	// 인기 브랜드 조회
	@GetMapping("/most-popular-brand-products")
	public List<UserMainProductDTO> mostpopularbrand() {
		return userMainServiceImpl.getPopularBrandProductList();
	}

	// 상품 별 최상단 이미지 이름 조회
	@GetMapping("/getImageName/{prodCode}")
	public String getImageName(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return userMainServiceImpl.getProductPicture(prodCode);
	}

	// 이미지 출력
	@GetMapping("/viewImage/{imageName}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "imageName", required = true) String imageName) {
		ResponseEntity<byte[]> result = null;

		if (!imageName.equals("undefined")) {

			File file = new File(Common.uploadDir + "//Uploaded//ProductsImage", imageName);
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
