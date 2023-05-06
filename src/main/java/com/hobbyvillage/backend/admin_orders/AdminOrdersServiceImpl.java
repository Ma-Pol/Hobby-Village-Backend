package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminOrdersServiceImpl implements AdminOrdersService {

	private AdminOrdersMapper mapper;

	public AdminOrdersServiceImpl(AdminOrdersMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public AdminOrdersDetailDTO getOrderDetail(String odrNumber) {
		return mapper.getOrderDetail(odrNumber);
	}

	@Override
	public List<AdminOrdersProductsDTO> getOrderedProductList(String odrNumber) {
		return mapper.getOrderedProductList(odrNumber);
	}

	@Override
	public int modifyOrderAddress(AdminOrdersDetailDTO addressData) {
		return mapper.modifyOrderAddress(addressData);
	}

	@Override
	public int returnProduct(int opCode, String prodCode) {
		int result = 0;
		int returningCheck = mapper.returningCheck(opCode);

		if (returningCheck == 1) {
			mapper.modifyOdrState(opCode);
			result = mapper.modifyRentalState(prodCode);
		}

		return result;
	}

}
