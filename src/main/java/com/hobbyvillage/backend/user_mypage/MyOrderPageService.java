package com.hobbyvillage.backend.user_mypage;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface MyOrderPageService {
	List<Integer> getOdrStateCount(String email);
	List<MyOrderListDTO> getOrderList(String email, String odrState);
	String getProdPicture(String prodCode);
	boolean checkReviewed(String prodCode, String nickname);
	ResponseEntity<byte[]> printProdPicture(String prodPicture) throws IOException;
	int returnProduct(int opCode);
	int cancelProduct(int opCode);
}
