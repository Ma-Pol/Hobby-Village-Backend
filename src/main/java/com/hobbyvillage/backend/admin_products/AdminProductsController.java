package com.hobbyvillage.backend.admin_products;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/m/products")
public class AdminProductsController {

	private AdminProductsServiceImpl adminProductsServiceImpl;

	public AdminProductsController(AdminProductsServiceImpl adminProductsServiceImpl) {
		this.adminProductsServiceImpl = adminProductsServiceImpl;
	}

	@GetMapping("/count")
	public int getProductCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int productCount;

		// 검색 여부 확인
		if (keyword == null) {
			productCount = adminProductsServiceImpl.getProductCount(filter);
		} else {
			productCount = adminProductsServiceImpl.getSearchProductCount(filter, condition, keyword);
		}

		return productCount;
	}

	@GetMapping("")
	public List<AdminProductsDTO> getProductList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminProductsDTO> productList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			productList = adminProductsServiceImpl.getProductList(filter, sort, pageNum);
		} else {
			productList = adminProductsServiceImpl.getSearchProductList(filter, condition, keyword, sort, pageNum);
		}

		return productList;
	}

	@GetMapping("/check/{prodCode}") // 실재 상품인지 확인
	public int checkProduct(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return adminProductsServiceImpl.checkProduct(prodCode);
	}

	@GetMapping("/productDetails/{prodCode}") // 상품 상세 조회
	public AdminProductsDTO getProductDetail(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return adminProductsServiceImpl.getProductDetail(prodCode);
	}

	@GetMapping("/pictures/{prodCode}") // 상품 상세 조회 - 이미지 파일명
	public List<String> getProdPictures(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return adminProductsServiceImpl.getProdPictures(prodCode);
	}

	// 상품 상세 조회 - 이미지 출력 (macOS 경로: //Uploaded//ProductsImage)
	// 윈도우 경로: \\Uploaded\\ProductsImage
	@GetMapping("/upload/{path}/{fileName}")
	public ResponseEntity<byte[]> getReqeustFileData(@PathVariable(value = "path", required = true) String path,
			@PathVariable(value = "fileName", required = true) String fileName) {
		File file = null;

		if (path.equals("product")) {
			file = new File(Common.uploadDir + "\\Uploaded\\ProductsImage", fileName);
		} else {
			file = new File(Common.uploadDir + "\\Uploaded\\RequestsFile", fileName);
		}

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

	@GetMapping("/tags/{prodCode}") // 상품 상세 조회 - 연관검색어
	public String getProdTag(@PathVariable(value = "prodCode", required = true) String prodCode) {
		List<String> list = adminProductsServiceImpl.getProdTag(prodCode);

		String str = "";
		for (String tag : list) {
			str += tag + ", ";
		}

		return str.substring(0, str.length() - 2);
	}

	@GetMapping("/getBrandList") // 브랜드 목록 조회
	public List<String> getBrandList() {
		return adminProductsServiceImpl.getBrandList();
	}

	@GetMapping("/getCategoryList") // 카테고리 목록 조회
	public List<String> getCategoryList() {
		return adminProductsServiceImpl.getCategoryList();
	}

	@PostMapping("/addProduct") // 상품 등록
	public boolean addProduct(@RequestBody AdminProductsDTO products) {
		return adminProductsServiceImpl.addProduct(products);
	}

	// 상품 등록 - 이미지 등록 1 (기존 판매/위탁 신청 이미지 업로드)
	@PostMapping("/upload/requestImg/{prodCode}")
	public int requestImageUpload(@PathVariable(value = "prodCode", required = true) String prodCode,
			@RequestBody Map<String, List<String>> requestImg) throws IOException {
		// 이미지명 리스트 저장
		List<String> requestImages = requestImg.get("requestImg");

		return adminProductsServiceImpl.requestImageUpload(prodCode, requestImages);
	}

	// 상품 등록 - 이미지 등록 2 (새 이미지 등록)
	@PostMapping("/upload/img/{prodCode}")
	public int imageUpload(@PathVariable(value = "prodCode", required = true) String prodCode,
			@RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg) throws IOException {
		return adminProductsServiceImpl.imageUpload(prodCode, uploadImg);
	}

	@PostMapping("/addProdTags") // 상품 등록 - 연관검색어
	public void addProductTags(@RequestBody AdminProductsDTO prodTags) {
		adminProductsServiceImpl.addProductTags(prodTags);
	}

	@DeleteMapping("/delete") // 상품 삭제
	public void deleteProduct(@RequestParam(value = "prodCode", required = true) String prodCode) {
		adminProductsServiceImpl.deleteProduct(prodCode);
	}

	@PatchMapping("/modifyProduct") // 상품 정보 수정
	public boolean modifyProduct(@RequestBody AdminProductsDTO products) {
		return adminProductsServiceImpl.modifyProduct(products);
	}

	@PatchMapping("/modifyProdTags") // 상품 정보 수정 - 연관검색어
	public void modifyProductTags(@RequestBody AdminProductsDTO prodTags) {
		adminProductsServiceImpl.modifyProductTags(prodTags);
	}

	@PatchMapping("/upload/img/{prodCode}") // 상품 정보 수정 - 이미지
	public int imageModify(@PathVariable(value = "prodCode", required = true) String prodCode,
			@RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg) throws IOException {
		return adminProductsServiceImpl.imageModify(prodCode, uploadImg);
	}

}
