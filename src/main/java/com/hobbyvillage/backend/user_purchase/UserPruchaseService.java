package com.hobbyvillage.backend.user_purchase;

import java.io.IOException;
import java.util.List;

public interface UserPruchaseService {
	String getImportToken() throws IOException;
	int getProductState(String prodCode);
	int checkProductInfo(String prodCode, int prodPrice, int prodShipping, String prodHost, int period);
	UserPurchaseUserDTO getUserInfo(String email);
	List<UserPurchaseAddressDTO> getAddressList(String email);
	List<UserPurchaseCouponDTO> getCouponList(String email);
	int updateProductState(String prodCode);
	String getPrevOdrNumber(String prodCode);
	int purchasePreProcess(UserPurchaseProcessDTO data, String prevPage);
	int paymentsPrepare(String odrNumber, int amount, String token) throws IOException;
	int compareToDatabase(String odrNumber, int paid_amount, String prevPage);
	int compareToImport(String odrNumber, int paid_amount, String prevPage, String imp_uid, String token) throws IOException;
	int increaseRentalCount(String prodCode, int rentalCount);
	int decreaseSavedMoney(String email, int exactSavedMoney);
	int deleteCoupon(String email, int couponCode);
	void deleteCart(String email, String prodCode);
	int purchaseFinalProcess(UserPurchaseProcessDTO data, String prevPage);
	int updateProductStateFailed(String prodCode);
	int deleteOrder(String odrNumber, String prevPage);
	int cancelOrder(String imp_uid, String token) throws IOException;
}
