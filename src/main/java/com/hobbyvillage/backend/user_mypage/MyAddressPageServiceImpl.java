package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MyAddressPageServiceImpl implements MyAddressPageService {

	private MyAddressPageMapper mapper;

	public MyAddressPageServiceImpl(MyAddressPageMapper mapper) {
		this.mapper = mapper;
	}

	// 배송지 목록 조회
	@Override
	public List<MyAddressPageDTO> getAddressList(String email) {
		return mapper.getAddressList(email);
	}

	// 배송지 접근 권한 조회
	@Override
	public int checkAddress(int addressCode, String email) {
		return mapper.checkAddress(addressCode, email);
	}

	// 배송지 단일 정보 조회
	@Override
	public MyAddressPageDTO getAddressDetail(int addressCode) {
		return mapper.getAddressDetail(addressCode);
	}

	// 배송지 삭제
	@Override
	public int deleteAddress(int addressCode) {
		int result = 0;
		MyAddressPageDTO addressData = mapper.getAddressDetail(addressCode);

		if (addressData.getIsDefault() != 1) {
			result = mapper.deleteAddress(addressCode);
		}

		return result;
	}

	// 배송지 등록
	@Override
	public int createAddress(MyAddressPageDTO addressData) {
		if (addressData.getIsDefault() == 1) {
			String email = addressData.getEmail();
			mapper.deleteDefaultAddress(email);
		}

		return mapper.createAddress(addressData);
	}

	// 배송지 수정
	@Override
	public int modifyAddress(MyAddressPageDTO addressData) {
		if (addressData.getIsDefault() == 1) {
			String email = addressData.getEmail();
			mapper.deleteDefaultAddress(email);
		}

		return mapper.modifyAddress(addressData);
	}

}
