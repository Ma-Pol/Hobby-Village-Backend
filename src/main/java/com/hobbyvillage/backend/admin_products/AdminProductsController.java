package com.hobbyvillage.backend.admin_products;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.UploadDir;

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
	
	@GetMapping("/getProdPictures") // 상품 상세 조회 - 이미지 파일명
	public List<String> getProdPictures(@RequestParam String prodCode) {
		return adminProductsServiceImpl.getProdPictures(prodCode);
	}

	// 상품 상세 조회 - 이미지 (macOS 경로: //Uploaded//ProductsImage)
	// 윈도우 경로: \\Uploaded\\ProductsImage
	@GetMapping("/upload/{fileName}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "fileName", required = true) String fileName) {
		File file = new File(UploadDir.uploadDir + "//Uploaded//ProductsImage", fileName);
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
	
	// 상품 등록 - 이미지 등록 
	@PostMapping("/upload/img") // {reqCode} 받아오기
	public int imageUpload(@RequestParam String prodCode, @RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg)
			throws IOException {
		// 이미지 업로드 위치 설정(상황에 맞게 RequestsFiles를 고쳐서 사용하세요)
		// 윈도우 경로 : \\Uploaded\\ProductsImage
		// mac 경로 : //Uploaded//ProductsImage
		String uploadPath = UploadDir.uploadDir + "//Uploaded//ProductsImage";
		int result = 0;

		// 이미지 개수만큼 반복
		for (MultipartFile image : uploadImg) {
			// 값이 없는 이미지가 아닌 경우(정상적인 이미지인 경우)
			if (!image.isEmpty()) {
				// 파일 저장 위치 + 저장할 파일명 설정(UUID를 사용해 파일명 중복을 피함)
				File fileName = new File(uploadPath, UUID.randomUUID().toString() + "_" + image.getOriginalFilename());
				// 여기서 실제 업로드가 이뤄집니다.
				image.transferTo(fileName);
				// DB에 파일명을 저장할 경우 이곳에서 처리하기
				adminProductsServiceImpl.addPicture(prodCode, fileName.toString());
				result += 1;
			}
		}
		return result;
	}

	@PostMapping("/addProdTags") // 상품 등록 - 연관검색어 
	public void addProductTags(@RequestBody AdminProductsDTO prodTags) {
		adminProductsServiceImpl.addProductTags(prodTags);
	}

	@PostMapping("/modifyProduct") // 상품 수정 
	public boolean modifyProduct(@RequestBody AdminProductsDTO products) {
		return adminProductsServiceImpl.modifyProduct(products);
	}
	
	@DeleteMapping("/delete")
	public void deleteProduct(@RequestParam(value = "prodCode", required = true) String prodCode) {
		adminProductsServiceImpl.deleteProduct(prodCode);
	}
}


