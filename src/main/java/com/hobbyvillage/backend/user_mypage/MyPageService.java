package com.hobbyvillage.backend.user_mypage;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MyPageService {
	ResponseEntity<byte[]> printProfPicture(String profPicture) throws IOException;
	int getSavedMoney(String email);
	int getCouponCount(String email);
	List<MyPageCouponsDTO> getCouponList(String email);
	void deleteCoupon(String email, int couponCode);
	String modifyProfPicture(String email, String profPicture, MultipartFile[] uploadImg) throws IOException;
}

