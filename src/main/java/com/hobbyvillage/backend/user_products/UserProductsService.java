package com.hobbyvillage.backend.user_products;

import java.util.List;

public interface UserProductsService {
	int getProductCount(String filter);
	int getSearchProductCount(String filter, String condition, String keyword);
	List<UserProductsDTO> getProductList(String filter, String sort, int pageNum);
	List<UserProductsDTO> getSearchProductList(String filter, String condition, String keyword, String sort,
			int pageNum);
	String[] getCategories();
}
