package com.hobbyvillage.backend.admin_orders;

import java.io.IOException;
import java.util.List;

public interface AdminOrdersService {
	String getImportToken() throws IOException;
	int getOrderCount(String sort, String filter);
	int getSearchOrderCount(String sort, String condition, String keyword, String filter);
	List<AdminOrdersDTO> getOrderList(String sort, String filter, int pageNum);
	List<AdminOrdersDTO> getSearchOrderList(String condition, String keyword, String sort, String filter, int pageNum);
	AdminOrdersDetailDTO getOrderDetail(String odrNumber);
	List<AdminOrdersProductsDTO> getOrderedProductList(String odrNumber);
	int modifyOrderAddress(AdminOrdersDetailDTO addressData);
	int payCompToPreDeli(String odrNumber);
	int preDeliToShipping(int opCode, String courierCompany, String trackingNumber);
	String checkOdrState(int opCode);
	int cancelOrder(AdminOrdersCancelOrderDTO data, String token) throws IOException;
	int returningToReturned(int opCode, String prodCode, int prodPrice, int rentalPeriod, String email);
	boolean trackingResult(AdminOrdersTrackingDTO trackingData) throws IOException;
}
