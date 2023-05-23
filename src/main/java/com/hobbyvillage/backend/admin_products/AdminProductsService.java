package com.hobbyvillage.backend.admin_products;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AdminProductsService {
	int getProductCount(String filter);
	int getSearchProductCount(String filter, String condition, String keyword);
	List<AdminProductsDTO> getProductList(String filter, String sort, int pageNum);
	List<AdminProductsDTO> getSearchProductList(String filter, String condition, String keyword, String sort, int pageNum);
	int checkProduct(String prodCode);
	AdminProductsDTO getProductDetail(String prodCode);
	List<String> getProdPictures(String prodCode);
	List<String> getProdTag(String prodCode);
	List<String> getBrandList();
	List<String> getCategoryList();
	boolean addProduct(AdminProductsDTO products);
	int imageUpload(String prodCode, MultipartFile[] uploadImg) throws IOException;
	void addProductTags(AdminProductsDTO prodTags);
	void deleteProduct(String prodCode);
	boolean modifyProduct(AdminProductsDTO products);
	void modifyProductTags(AdminProductsDTO prodTags);
	int imageModify(String prodCode, MultipartFile[] uploadImg) throws IOException;
}
