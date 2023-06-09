package com.hobbyvillage.backend.admin_products;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.Common;

@Service
public class AdminProductsServiceImpl implements AdminProductsService {

	private AdminProductsMapper mapper;

	public AdminProductsServiceImpl(AdminProductsMapper mapper) {
		this.mapper = mapper;
	}

	// 윈도우 경로 : \\Uploaded\\ProductsImage
	// mac 경로 : //Uploaded//ProductsImage
	private String prodUploadPath = Common.uploadDir + "\\Uploaded\\ProductsImage\\";
	private String revwUploadPath = Common.uploadDir + "\\Uploaded\\ReviewsImage\\";
	private String reqUploadPath = Common.uploadDir + "\\Uploaded\\RequestsFile\\";

	// 필터 조건에 따른 쿼리문 설정 메서드
	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "prodIsRental IS NOT NULL";
		} else if (filter.equals("rented")) {
			filter = "prodIsRental = 1";
		} else if (filter.equals("no-rent")) {
			filter = "prodIsRental = 0";
		}

		return filter;
	}

	@Override // 미검색 상태에서 상품 개수 조회
	public int getProductCount(String filter) {
		filter = filtering(filter);

		if (filter.equals("deleted")) {
			return mapper.getDeletedProductCount();
		} else {
			return mapper.getProductCount(filter);
		}

	}

	@Override // 검색 상태에서 상품 개수 조회
	public int getSearchProductCount(String filter, String condition, String keyword) {
		filter = filtering(filter);

		if (filter.equals("deleted")) {
			return mapper.getSearchDeletedProductCount(condition, keyword);
		} else {
			return mapper.getSearchProductCount(filter, condition, keyword);
		}

	}

	@Override // 미검색 상태에서 상품 목록 조회
	public List<AdminProductsDTO> getProductList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		if (filter.equals("deleted")) {
			return mapper.getDeletedProductList(sort, pageNum);
		} else {
			return mapper.getProductList(filter, sort, pageNum);
		}

	}

	@Override // 검색 상태에서 상품 목록 조회
	public List<AdminProductsDTO> getSearchProductList(String filter, String condition, String keyword, String sort,
			int pageNum) {
		filter = filtering(filter);

		if (filter.equals("deleted")) {
			return mapper.getSearchDeletedProductList(condition, keyword, sort, pageNum);
		} else {
			return mapper.getSearchProductList(filter, condition, keyword, sort, pageNum);
		}

	}

	@Override // 실재 상품인지 확인
	public int checkProduct(String prodCode) {
		return mapper.checkProductCount(prodCode);
	}

	@Override // 상품 상세 조회
	public AdminProductsDTO getProductDetail(String prodCode) {
		return mapper.getProductDetail(prodCode);
	}

	@Override // 상품 상세 조회 - 이미지
	public List<String> getProdPictures(String prodCode) {
		return mapper.getProdPictures(prodCode);
	}

	@Override // 상품 상세 조회 - 연관검색어
	public List<String> getProdTag(String prodCode) {
		List<String> tags = mapper.getProdTag(prodCode);
		return tags;
	}

	@Override // 브랜드 목록 조회
	public List<String> getBrandList() {
		List<String> brands = mapper.getBrandList();
		return brands;
	}

	@Override // 카테고리 목록 조회
	public List<String> getCategoryList() {
		List<String> categories = mapper.getCategoryList();
		return categories;
	}

	@Override // 상품 등록
	public boolean addProduct(AdminProductsDTO products) {
		if (products.getProdBrand().equals("none")) {
			products.setProdBrand(null);
		}

		return mapper.addProduct(products);
	}

	@Override
	public int requestImageUpload(String prodCode, List<String> requestImages) throws IOException {
		int result = 0;

		// 신청 이미지 폴더에 저장되어 있는 파일을 상품 이미지 폴더에 복사
		for (String prodPicture : requestImages) {
			File prevImg = new File(prodUploadPath, prodPicture);
			File nextImg = new File(reqUploadPath, prodPicture);

			Files.copy(nextImg.toPath(), prevImg.toPath(), StandardCopyOption.REPLACE_EXISTING);

			mapper.addPicture(prodCode, prodPicture);

			result++;
		}

		return result;
	}

	@Override // 상품 등록 - 이미지 파일명
	public int imageUpload(String prodCode, MultipartFile[] uploadImg) throws IOException {
		int result = 0;

		// 이미지 개수만큼 반복
		for (MultipartFile image : uploadImg) {
			// 값이 없는 이미지가 아닌 경우(정상적인 이미지인 경우)
			if (!image.isEmpty()) {
				// 저장할 파일 명 설정(UUID를 사용해 파일명 중복을 피함)
				String prodPicture = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

				// 파일 저장 위치
				File file = new File(prodUploadPath, prodPicture);

				// 여기서 실제 업로드가 이뤄집니다.
				image.transferTo(file);

				// DB에 파일명을 저장할 경우 이곳에서 처리하기
				mapper.addPicture(prodCode, prodPicture);
				result++;
			}
		}

		return result;
	}

	@Override // 상품 등록 - 연관검색어
	public void addProductTags(AdminProductsDTO prodTags) {
		List<String> tags = prodTags.getProdTag();
		String prodCode = prodTags.getProdCode();
		for (String tag : tags) {
			mapper.addProductTags(prodCode, tag);
		}
	}

	private void deletePicture(String prodCode) {
		// 1. 상품 이미지명 조회
		List<String> prodPictures = mapper.getProdPictures(prodCode);

		// 2. 상품 이미지 파일 삭제
		if (prodPictures != null) {
			for (String imageName : prodPictures) {
				File filePath = new File(prodUploadPath + imageName);

				filePath.delete();
			}
		}
	}

	@Override // 상품 삭제
	public void deleteProduct(String prodCode) {
		// 상품 삭제 처리
		mapper.deleteProduct(prodCode);

		// 장바구니에서 삭제
		mapper.deleteCart(prodCode);

		// 찜 목록에서 삭제
		mapper.deleteDib(prodCode);

		// 리뷰 이미지 목록 조회
		List<String> reviewImages = mapper.getReviewImage(prodCode);

		// 리뷰 이미지 삭제
		if (reviewImages != null) {
			for (String reviewImage : reviewImages) {
				File filePath = new File(revwUploadPath + reviewImage);

				filePath.delete();
			}
		}

		// 리뷰 삭제
		mapper.deleteReviews(prodCode);
	}

	@Override // 상품 수정
	public boolean modifyProduct(AdminProductsDTO products) {
		if (products.getProdBrand().equals("none")) {
			products.setProdBrand(null);
		}

		return mapper.modifyProduct(products);
	}

	@Override // 상품 수정 - 연관검색어
	public void modifyProductTags(AdminProductsDTO prodTags) {
		mapper.deleteProductTags(prodTags.getProdCode());
		addProductTags(prodTags);
	}

	@Override // 상품 수정 - 이미지 파일명
	public int imageModify(String prodCode, MultipartFile[] uploadImg) throws IOException {
		deletePicture(prodCode);
		mapper.deletePicture(prodCode);

		return imageUpload(prodCode, uploadImg);
	}

}
