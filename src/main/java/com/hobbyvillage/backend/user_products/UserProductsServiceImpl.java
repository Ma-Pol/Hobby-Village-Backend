package com.hobbyvillage.backend.user_products;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserProductsServiceImpl implements UserProductsService {

	private UserProductsMapper mapper;

	public UserProductsServiceImpl(UserProductsMapper mapper) {
		this.mapper = mapper;
	}
	
	// category 필터링
	private String categoryFilter(String category) {
		if(category.equals("all")) {
			category = "prodCategory IS NOT NULL";
		} else {
			category = "prodCategory=\"" + category + "\"";
		}
		return category;
	}
	
	// brand 필터링
	private String brandFilter(String brand) {
		if(brand.equals("all")) {
			brand = "prodBrand IS NOT NULL";
		} else {
			brand = "prodBrand=\"" + brand + "\"";
		}
		return brand;
	}
	
	// sort 필터링 (대여 여부)
	private String sortFilter(String sort) {
		if(sort.equals("all")) {
			sort = "prodIsRental IS NOT NULL";
		} else if(sort.equals("rented")) {
			sort = "prodIsRental = 1";
		} else {
			sort = "prodIsRental = 0";
		}
		return sort;
	}
	
	// category 필터링 (평점순)
	private String categoryFilterRR(String category) {
		if(category.equals("all")) {
			category = "p.prodCategory IS NOT NULL";
		} else {
			category = "p.prodCategory=\"" + category + "\"";
		}
		return category;
	}
	
	// brand 필터링 (평점순)
		private String brandFilterRR(String brand) {
			if(brand.equals("all")) {
				brand = "p.prodBrand IS NOT NULL";
			} else {
				brand = "p.prodBrand=\"" + brand + "\"";
			}
			return brand;
		}
	
	// sort 필터링 (대여 여부) (평점순)
	private String sortFilterRR(String sort) {
		if(sort.equals("all")) {
			sort = "p.prodIsRental IS NOT NULL";
		} else if(sort.equals("rented")) {
			sort = "p.prodIsRental = 1";
		} else {
			sort = "p.prodIsRental = 0";
		}
		return sort;
	}
	
	// array 필터링 (정렬) -- cf.평점순엔 array 없음 
	// 최신(regiDate)/인기(찜기준)/고가/저가
	private String arrayFilter(String array) {
		if(array.equals("recent")) {
			array = "prodRegiDate DESC"; // 기본값: 최신순 정렬
		} else if(array.equals("popular")) {
			array = "prodDibs DESC"; // 인기순 
		} else if(array.equals("expensive")) {
			array = "prodPrice DESC"; // 가격높은순
		} else {
			array = "prodPrice ASC"; // 가격낮은순 
		}
		return array;
	}

	// ------------------------------------------

	@Override // 카테고리 불러오기
	public List<String> getCategories() {
		return mapper.getCategories();
	}
	
	@Override // 브랜드 불러오기 
	public List<String> getBrands() {
		return mapper.getBrands();
	}
	
	// ------------------------------------------
	
	@Override // 상품 개수 조회 - 검색x
	public int getProductCount(String category, String sort) {
		category = categoryFilter(category);
		sort = sortFilter(sort);
		return mapper.getProductCount(category, sort);
	}
	
	@Override // 상품 개수 조회 - 검색o
	public int getSearchProductCount(String category, String sort, String keyword) {
		category = categoryFilter(category);
		sort = sortFilter(sort);
		return mapper.getSearchProductCount(category, sort, keyword);
	}
	
	@Override // 상품 개수 조회 - 검색x (브랜드관)
	public int getBrandProductCount(String brand, String sort) {
		brand = brandFilter(brand);
		sort = sortFilter(sort);
		return mapper.getBrandProductCount(brand, sort);
	}
	
	// ------------------------------------------



	@Override // 상품 목록 조회 - 검색x & 평점순
	public List<UserProductsDTO> getProductListRR(String category, String sort, int pageNum) {
		category = categoryFilterRR(category);
		sort = sortFilterRR(sort);
		return mapper.getProductListRR(category, sort, pageNum);
	}
	
	@Override // 상품 목록 조회 - 검색x & not 평점순
	public List<UserProductsDTO> getProductList(String category, String sort, String array, int pageNum) {
		category = categoryFilter(category);
		sort = sortFilter(sort);
		array = arrayFilter(array);
		return mapper.getProductList(category, sort, array, pageNum);
	}
	
	@Override // 상품 목록 조회 - 검색x & 평점순 (브랜드관)
	public List<UserProductsDTO> getBrandProductListRR(String brand, String sort, int pageNum) {
		brand = brandFilterRR(brand);
		sort = sortFilterRR(sort);
		return mapper.getBrandProductListRR(brand, sort, pageNum);
	}
	
	@Override // 상품 목록 조회 - 검색x & not 평점순 (브랜드관)
	public List<UserProductsDTO> getBrandProductList(String brand, String sort, String array, int pageNum) {
		brand = brandFilter(brand);
		sort = sortFilter(sort);
		array = arrayFilter(array);
		return mapper.getBrandProductList(brand, sort, array, pageNum);
	}

	@Override // 상품 목록 조회 - 검색o & 평점순
	public List<UserProductsDTO> getProductListSRR(String category, String sort, String keyword, int pageNum) {
		category = categoryFilterRR(category);
		sort = sortFilterRR(sort);
		return mapper.getProductListSRR(category, sort, keyword, pageNum);
	}
	
	@Override // 상품 목록 조회 - 검색o & not 평점순
	public List<UserProductsDTO> getProductListS(String category, String sort, String array, String keyword,
			int pageNum) {
		category = categoryFilter(category);
		sort = sortFilter(sort);
		array = arrayFilter(array);
		return mapper.getProductListS(category, sort, array, keyword, pageNum);
	}

	@Override // 상품 상세 조회 
	public UserProductsDTO getProductDetail(String prodCode) {
		return mapper.getProductDetail(prodCode);
	}
		
	// ------------------------------------------
	
	@Override // 상품 이미지 파일명 조회 
	public List<String> getProdPictures(String prodCode) {
		return mapper.getProdPictures(prodCode);
	}

	@Override // 상품 브랜드 로고 파일명 조회 
	public String getBrandImgName(String brand) {
		return mapper.getBrandImgName(brand);
	}

	

}
