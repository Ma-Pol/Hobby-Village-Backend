package com.hobbyvillage.backend.admin_products;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/getProductDetail") // 상품 상세 조회 
	public AdminProductsDTO getProductDetail(@RequestParam String prodCode) {
		if (adminProductsServiceImpl.getProductDetailWR(prodCode) != null) {
			return adminProductsServiceImpl.getProductDetailWR(prodCode);
		}
		return adminProductsServiceImpl.getProductDetail(prodCode);
	}
	
//	@GetMapping(value="/getProdPictures", produces="MediaType.IMAGE_PNG_VALUE") // 상품 상세 조회 - 이미지
//	public byte[] getProdPictures(@RequestParam String prodCode) throws IOException {
//		
//		List<String> images = adminProductsServiceImpl.getProdPictures(prodCode);
//
//		for (int i=0; i<images.size(); i++) {
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//			 
//		String res = "/Users/neung/desktop/devStudy/KDTProject/Hobby-Village/Uploaded/ProductsImage/" + images.get(i);
//		InputStream in = new FileInputStream(res);
//		IOUtils.toByteArray(in);
//		}
//	}

	@GetMapping("/getProdTag") // 상품 상세 조회 - 연관검색어
	public String getProdTag(@RequestParam String prodCode) {
		List<String> list = adminProductsServiceImpl.getProdTag(prodCode);
		String str = "";
		for (int i=0; i<list.size(); i++) {
			if (i==0) {
				str += list.get(i);
			} else {
				str += ", " + list.get(i);
			}
		}
		return str;
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
	
//	@PostMapping("/addProdPics") // 상품 등록 - 이미지 
//	public boolean addProductPictures(@RequestBody AdminProductsDTO prodPictures) {
//		return adminProductsServiceImpl.addProductPictures(prodPictures);
//	}

	@PostMapping("/addProdTags") // 상품 등록 - 연관검색어 
	public void addProductTags(@RequestBody AdminProductsDTO prodTags) {
		adminProductsServiceImpl.addProductTags(prodTags);
	}

	@PostMapping("/modifyProduct") // 상품 수정 
	public boolean modifyProduct(@RequestBody AdminProductsDTO products) {
		return adminProductsServiceImpl.modifyProduct(products);
	}
	
}


