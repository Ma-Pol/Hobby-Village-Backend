package com.hobbyvillage.backend.user_products;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/lists")
public class UserProductsController {
	private UserProductsServiceImpl userProductsServiceImpl;

	public UserProductsController(UserProductsServiceImpl userProductsServiceImpl) {
		this.userProductsServiceImpl = userProductsServiceImpl;
	}
	
	@GetMapping("/")
	public String[] getCategories() {
		String[] categories = userProductsServiceImpl.getCategories();
		return categories;
	}

	@GetMapping("/count")
	public int getProductCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int productCount;

		// 검색 여부 확인
		if (keyword == null) {
			productCount = userProductsServiceImpl.getProductCount(filter);
		} else {
			productCount = userProductsServiceImpl.getSearchProductCount(filter, condition, keyword);
		}

		return productCount;
	}

	@GetMapping("")
	public List<UserProductsDTO> getProductList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<UserProductsDTO> productList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			productList = userProductsServiceImpl.getProductList(filter, sort, pageNum);
		} else {
			productList = userProductsServiceImpl.getSearchProductList(filter, condition, keyword, sort, pageNum);
		}

		return productList;
	}

}
