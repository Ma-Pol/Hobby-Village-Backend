package com.hobbyvillage.backend.admin_products;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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

		if (condition == null) {
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

		if (condition == null) {
			productList = adminProductsServiceImpl.getProductList(filter, sort, pageNum);
		} else {
			productList = adminProductsServiceImpl.getSearchProductList(filter, condition, keyword, sort, pageNum);
		}

		return productList;
	}
}
