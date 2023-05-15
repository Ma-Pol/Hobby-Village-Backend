package com.hobbyvillage.backend.admin_products;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AdminProductsService {
	int getProductCount(String filter);
	int getSearchProductCount(String filter, String condition, String keyword);
	List<AdminProductsDTO> getProductList(String filter, String sort, int pageNum);
	List<AdminProductsDTO> getSearchProductList(String filter, String condition, String keyword, String sort, int pageNum);
	public AdminProductsDTO getProductDetail(@Param("prodCode") String prodCode);
}
