package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UserCartService {
	UserCartCountDTO getCartCount(String email);
	List<UserCartDTO> getCartList(String email, String filter);
	void modifyPeriod(UserCartPeriodDTO periodData);
	int deleteCart(int cartCode);
	int deleteSelectedCart(List<UserCartCodeDTO> cartList);
	ResponseEntity<byte[]> getProdPicture(String prodPicture);
}
