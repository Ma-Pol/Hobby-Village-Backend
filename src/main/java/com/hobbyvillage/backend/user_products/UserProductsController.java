package com.hobbyvillage.backend.user_products;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/products/lists")
public class UserProductsController {

	private UserProductsServiceImpl service;

	public UserProductsController(UserProductsServiceImpl service) {
		this.service = service;
	}

	// 카테고리 목록 불러오기
	@GetMapping("/category")
	public List<String> getCategories() {
		return service.getCategories();
	}

	// 브랜드 목록 불러오기
	@GetMapping("/brands")
	public List<String> getBrands() {
		return service.getBrands();
	}

	// ----------------------------

	// 상품 개수 조회 - if 검색 여부
	@GetMapping("/count")
	public int getProductCount(@RequestParam(value = "category", required = true) String category,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "keyword", required = false) String keyword) {

		if (keyword.equals("null")) { // no 검색
			return service.getProductCount(category, sort);
		} else { // yes 검색
			return service.getSearchProductCount(category, sort, keyword);
		}
	}

	// 상품 개수 조회 (브랜드관)
	@GetMapping("/brandProdCount")
	public int getBrandProductCount(@RequestParam(value = "brand", required = true) String brand,
			@RequestParam(value = "sort", required = true) String sort) {
		return service.getBrandProductCount(brand, sort);
	}

	// ----------------------------

	// 상품 목록 조회 - 검색여부 구분(if 추가 필요) - 평점순과 기타정렬 구분
	@GetMapping("")
	public List<UserProductsDTO> getProductList(@RequestParam(value = "category", required = true) String category,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "array", required = false) String array,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "keyword", required = false) String keyword) {

		int pageNum = (pages - 1) * 12;

		if (keyword.equals("null")) {
			if (array.equals("revwRate")) { // yes 평점순
				return service.getProductListRR(category, sort, pageNum);
			} else { // no 평점순
				return service.getProductList(category, sort, array, pageNum);
			}
		} else {
			if (array.equals("revwRate")) {
				return service.getProductListSRR(category, sort, keyword, pageNum);
			} else {
				return service.getProductListS(category, sort, array, keyword, pageNum);
			}
		}
	}

	// 상품 목록 조회 (브랜드관)
	@GetMapping("/brandProdList")
	public List<UserProductsDTO> getBrandProductList(@RequestParam(value = "brand", required = true) String brand,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "array", required = false) String array,
			@RequestParam(value = "pages", required = true) int pages) {

		int pageNum = (pages - 1) * 12;

		if (array.equals("revwRate")) { // yes 평점순
			return service.getBrandProductListRR(brand, sort, pageNum);
		} else { // no 평점순
			return service.getBrandProductList(brand, sort, array, pageNum);
		}
	}

	// 상품 실재 여부 확인
	@GetMapping("/checkProduct")
	public int checkProduct(@RequestParam(value = "prodCode", required = true) String prodCode) {
		return service.checkProduct(prodCode);
	}

	// 상품 상세 조회
	@GetMapping("/getProductDetail")
	public UserProductsDTO getProductDetail(@RequestParam(value = "prodCode", required = true) String prodCode) {
		return service.getProductDetail(prodCode);
	}

	@GetMapping("/checkDibs")
	public int checkDibs(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "prodCode") String prodCode) {
		return service.checkDibs(email, prodCode);
	}

	@GetMapping("/updateDibs")
	public void updateDibs(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "prodCode", required = true) String prodCode) {
		service.updateDibs(email, prodCode);
		service.updateDibCount(prodCode);
	}

	@GetMapping("/checkCart")
	public int checkCarts(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "prodCode") String prodCode) {
		return service.checkCarts(email, prodCode);
	}

	@GetMapping("/addCart")
	public void addCart(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "prodCode", required = true) String prodCode,
			@RequestParam(value = "period", required = true) String period) {
		service.addCart(email, prodCode, period);
	}

	@GetMapping("/checkOrders")
	public int checkOrders(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "prodCode", required = true) String prodCode) {
		return service.checkOrders(email, prodCode);
	}

	// ----------------------------

	@GetMapping("/getProdPicture") // 이미지 파일명 단일 조회
	public String getProdPicture(@RequestParam(value = "prodCode", required = true) String prodCode) {
		return service.getProdPicture(prodCode);
	}

	@GetMapping("/getProdPictures") // 이미지 파일명 전체 조회
	public List<String> getProdPictures(@RequestParam(value = "prodCode", required = true) String prodCode) {
		return service.getProdPictures(prodCode);
	}

	@GetMapping("/getBrandImgName") // 브랜드 로고 파일명 조회
	public String getBrandImgName(@RequestParam(value = "prodCode", required = true) String prodCode) {
		return service.getBrandImgName(prodCode);
	}

	// macOS 경로: //Uploaded//ProductsImage
	// 윈도우 경로: \\Uploaded\\ProductsImage
	@GetMapping("/upload/{fileName}") // 이미지 불러오기
	public ResponseEntity<byte[]> requestProdPics(@PathVariable(value = "fileName", required = true) String fileName) {
		File file = new File(Common.uploadDir + "\\Uploaded\\ProductsImage", fileName);
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

}
