package com.hobbyvillage.backend.user_mypage;

import java.util.List;

public interface MyAddressPageService {
	List<MyAddressPageDTO> getAddressList(String email);
	int checkAddress(int addressCode, String email);
	MyAddressPageDTO getAddressDetail(int addressCode);
	int deleteAddress(int addressCode);
	int createAddress(MyAddressPageDTO addressData);
	int modifyAddress(MyAddressPageDTO addressData);
}
