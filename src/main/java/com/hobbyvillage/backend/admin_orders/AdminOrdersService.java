package com.hobbyvillage.backend.admin_orders;

import java.util.List;

public interface AdminOrdersService {
	AdminOrdersDetailDTO getOrderDetail(String odrNumber);
	List<AdminOrdersProductsDTO> getOrderedProductList(String odrNumber);
	int modifyOrderAddress(AdminOrdersDetailDTO addressData);
	int returnProduct(int opCode, String prodCode);
}
