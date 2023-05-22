package com.hobbyvillage.backend.admin_products;

import java.util.List;

public interface AdminProductsService {
	int getProductCount(String filter);
	int getSearchProductCount(String filter, String condition, String keyword);
	List<AdminProductsDTO> getProductList(String filter, String sort, int pageNum);
	List<AdminProductsDTO> getSearchProductList(String filter, String condition, String keyword, String sort, int pageNum);
	public AdminProductsDTO getProductDetailWR(String prodCode);
	public AdminProductsDTO getProductDetail(String prodCode);
	List<String> getProdPictures(String prodCode);
	List<String> getProdTag(String prodCode);
	List<String> getBrandList();
	List<String> getCategoryList();
	public boolean addProduct(AdminProductsDTO products);
	void addPicture(String prodCode, String prodPicture);
	public void addProductTags(AdminProductsDTO prodTags);
	public boolean modifyProduct(AdminProductsDTO products);
	public boolean deleteProduct(String prodCode);
	
}

