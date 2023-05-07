package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminOrdersServiceImpl implements AdminOrdersService {

	private AdminOrdersMapper mapper;

	public AdminOrdersServiceImpl(AdminOrdersMapper mapper) {
		this.mapper = mapper;
	}

	@Override // 미검색 상태에서 주문 개수 조회
	public int getOrderCount(String sort) {
		int orderCount;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderCount = mapper.getDeliveriedOrderCount();
		} else {
			orderCount = mapper.getAllOrderCount();
		}

		return orderCount;
	}

	@Override // 검색 상태에서 주문 개수 조회
	public int getSearchOrderCount(String sort, String condition, String keyword) {
		int orderCount;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderCount = mapper.getSearchDeliveriedOrderCount(condition, keyword);
		} else {
			orderCount = mapper.getSearchAllOrderCount(condition, keyword);
		}

		return orderCount;
	}

	@Override // 미검색 상태에서 주문 목록 조회
	public List<AdminOrdersDTO> getOrderList(String sort, int pageNum) {
		List<AdminOrdersDTO> orderList;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderList = mapper.getDeliveriedOrderList(sort, pageNum);
		} else {
			orderList = mapper.getAllOrderList(sort, pageNum);
		}

		return orderList;
	}

	@Override // 검색 상태에서 주문 목록 조회
	public List<AdminOrdersDTO> getSearchOrderList(String condition, String keyword, String sort, int pageNum) {
		List<AdminOrdersDTO> orderList;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderList = mapper.getSearchDeliveriedOrderList(condition, keyword, sort, pageNum);
		} else {
			orderList = mapper.getSearchAllOrderList(condition, keyword, sort, pageNum);
		}

		return orderList;
	}

	@Override // 주문 상세 조회
	public AdminOrdersDetailDTO getOrderDetail(String odrNumber) {
		return mapper.getOrderDetail(odrNumber);
	}

	@Override // 각 주문 별 상품 목록 조회
	public List<AdminOrdersProductsDTO> getOrderedProductList(String odrNumber) {
		return mapper.getOrderedProductList(odrNumber);
	}

	@Override // 주문자 주소 수정
	public int modifyOrderAddress(AdminOrdersDetailDTO addressData) {
		return mapper.modifyOrderAddress(addressData);
	}

	@Override // 반납 완료 처리
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
