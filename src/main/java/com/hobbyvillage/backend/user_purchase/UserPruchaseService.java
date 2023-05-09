package com.hobbyvillage.backend.user_purchase;

import java.util.List;

public interface UserPruchaseService {
	int getProductState(String prodCode);
	UserPurchaseUserDTO getUserInfo(String email);
	List<UserPurchaseAddressDTO> getAddressList(String email);
}
