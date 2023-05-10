package com.hobbyvillage.backend.user_purchase;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserPurchaseServiceImpl implements UserPruchaseService {

	private UserPurchaseMapper mapper;

	public UserPurchaseServiceImpl(UserPurchaseMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public int getProductState(String prodCode) {
		return mapper.getProductState(prodCode);
	}

	@Override
	public UserPurchaseUserDTO getUserInfo(String email) {
		return mapper.getUserInfo(email);
	}

	@Override
	public List<UserPurchaseAddressDTO> getAddressList(String email) {
		return mapper.getAddressList(email);
	}

	@Override
	public List<UserPurchaseCouponDTO> getCouponList(String email) {
		return mapper.getCouponList(email);
	}

}
