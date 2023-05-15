package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminOrdersServiceImpl implements AdminOrdersService {

	private AdminOrdersMapper mapper;

	public AdminOrdersServiceImpl(AdminOrdersMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "odrState IS NOT NULL";

		} else if (filter.equals("payment-completed")) {
			filter = "odrState = '결제 완료'";

		} else if (filter.equals("preparing-for-delivery")) {
			filter = "odrState = '배송 준비 중'";

		} else if (filter.equals("shipping")) {
			filter = "odrState = '배송 중'";

		} else if (filter.equals("delivery-completed")) {
			filter = "odrState = '배송 완료'";

		} else if (filter.equals("returning")) {
			filter = "odrState = '반납 중'";

		} else if (filter.equals("returned")) {
			filter = "odrState = '반납 완료'";

		} else if (filter.equals("cancel-request")) {
			filter = "odrState = '취소 요청'";

		} else {
			filter = "odrState = '취소 처리 완료'";
		}

		return filter;
	}

	@Override // 미검색 상태에서 주문 개수 조회
	public int getOrderCount(String sort, String filter) {
		filter = filtering(filter);
		int orderCount;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderCount = mapper.getDeliveriedOrderCount(filter);
		} else {
			orderCount = mapper.getAllOrderCount(filter);
		}

		return orderCount;
	}

	@Override // 검색 상태에서 주문 개수 조회
	public int getSearchOrderCount(String sort, String condition, String keyword, String filter) {
		filter = filtering(filter);
		int orderCount;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderCount = mapper.getSearchDeliveriedOrderCount(condition, keyword, filter);
		} else {
			orderCount = mapper.getSearchAllOrderCount(condition, keyword, filter);
		}

		return orderCount;
	}

	@Override // 미검색 상태에서 주문 목록 조회
	public List<AdminOrdersDTO> getOrderList(String sort, String filter, int pageNum) {
		filter = filtering(filter);
		List<AdminOrdersDTO> orderList;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderList = mapper.getDeliveriedOrderList(sort, filter, pageNum);
		} else {
			orderList = mapper.getAllOrderList(sort, filter, pageNum);
		}

		return orderList;
	}

	@Override // 검색 상태에서 주문 목록 조회
	public List<AdminOrdersDTO> getSearchOrderList(String condition, String keyword, String sort, String filter,
			int pageNum) {
		filter = filtering(filter);
		List<AdminOrdersDTO> orderList;

		// 반납기한 정렬 여부 확인(반납기한이 설정되지 않은 데이터 제외 처리)
		if (sort.equals("-deadline")) {
			orderList = mapper.getSearchDeliveriedOrderList(condition, keyword, sort, filter, pageNum);
		} else {
			orderList = mapper.getSearchAllOrderList(condition, keyword, sort, filter, pageNum);
		}

		return orderList;
	}

	@Override // 주문 상세 조회
	public AdminOrdersDetailDTO getOrderDetail(String odrNumber) {
		AdminOrdersDetailDTO result = mapper.getOrderDetail(odrNumber);
		int exactPrice = mapper.getOrderExactPrice(odrNumber);
		int usedSavedMoney = mapper.getOrderUsedSavedMoney(odrNumber);

		result.setExactPrice(exactPrice);
		result.setUsedSavedMoney(usedSavedMoney);

		return result;
	}

	@Override // 각 주문 별 상품 목록 조회
	public List<AdminOrdersProductsDTO> getOrderedProductList(String odrNumber) {
		return mapper.getOrderedProductList(odrNumber);
	}

	@Override // 주문자 주소 수정
	public int modifyOrderAddress(AdminOrdersDetailDTO addressData) {
		return mapper.modifyOrderAddress(addressData);
	}

	@Override // 주문 확인 처리 (결제 완료 -> 배송 준비 중)
	public int payCompToPreDeli(String odrNumber) {
		return mapper.payCompToPreDeli(odrNumber);
	}

	@Override // 운송 정보 등록 처리 (배송 준비 중 -> 배송 중)
	public int preDeliToShipping(int opCode, String courierCompany, String trackingNumber) {
		return mapper.preDeliToShipping(opCode, courierCompany, trackingNumber);
	}

	@Override // 주문 취소 처리 과정 1
	public String checkOdrState(int opCode) {
		return mapper.checkOdrState(opCode);
	}

	@Override // 주문 취소 처리 과정 2
	public int cancelOrder(AdminOrdersCancelOrderDTO data) {
		String odrNumber = data.getOdrNumber(), // 주문번호
				prodCode = data.getProdCode(),
				odrEmail = data.getOdrEmail(); // 주문자 이메일
		int opCode = data.getOpCode(), // 주문 상품 코드
				prodPrice = data.getProdPrice(), // 상품 단위 가격
				rentalPeriod = data.getRentalPeriod(), // 상품 대여 기간
				prodShipping = data.getProdShipping(), // 상품 배송비
				exactPrice = data.getExactPrice(), // 실제 주문 가격 (상품 최종 가격 - 사용 적립금)
				usedSavedMoney = data.getUsedSavedMoney(); // 사용 적립금
		int amount; // 아임포트 취소 금액
		int returnSavedMoney; // 환급 적립금
		int result; // 메서드 결과

		// 주문번호에 해당하는 상품의 총 개수 구하기
		String filter = "odrNumber = " + odrNumber;
		int allOrderProducts = mapper.getAllOrderCount(filter);

		// 주문 상품이 하나 뿐
		if (allOrderProducts == 1) {
			// 이 경우, exactPrice를 환불 금액으로 설정
			amount = exactPrice;
			returnSavedMoney = usedSavedMoney;
		}

		// 주문 상품이 둘 이상
		else {
			// 주문번호에 해당하는 상품들 중 취소 가능한 상품의 총 개수 구하기
			// (결제 완료, 배송 준비 중, 취소 요청)
			filter = "odrNumber = " + odrNumber + " AND odrState IN('결제 완료', '배송 준비 중', '취소 요청')";
			int cancelableProducts = mapper.getAllOrderCount(filter);

			// 해당 주문의 취소 금액과 사용 취소된 적립금을 조회
			AdminOrdersCancelPriceDTO canceledData = mapper.getCanceledData(odrNumber);
			int cancelPrice = canceledData.getCancelPrice();
			int cancelSavedMoney = canceledData.getCancelSavedMoney();

			if (cancelableProducts == 1) {
				// 나머지 상품들이 모두 주문 취소된 상태인지 확인
				filter = "odrNumber = " + odrNumber + " AND odrState = '주문 취소 완료'";
				int canceledProducts = mapper.getAllOrderCount(filter);
				
				if (allOrderProducts == cancelableProducts - canceledProducts) {
					// 나머지 상품이 모두 취소된 상태에서 마지막 상품까지 취소하는 상황
					amount = exactPrice - cancelPrice;
					returnSavedMoney = usedSavedMoney - cancelSavedMoney;
				} else {
					// 배송 중 ~ 반납 완료 상태인 상품이 하나 이상 존재하는 상황(전액 환불 불가)
					if (exactPrice - cancelPrice <= 100) {
						// 최종 남은 주문 금액이 100원 이하인 경우, 아임포트 환불 없이 DB에서만 처리
						mapper.cancelComplate(opCode); // '주문 취소 완료' 처리
						mapper.modifyRentalState(prodCode);
						
						if (usedSavedMoney - cancelSavedMoney >= (prodPrice * rentalPeriod / 7) + prodShipping) {
							returnSavedMoney = usedSavedMoney - cancelSavedMoney;
						} else {
							returnSavedMoney = 
						}
						return mapper.returnSavedMoney(odrEmail, returnSavedMoney);
					} else {

					}
				}
				
			} else {

			}
		}

		return result;
	}

	@Override // 반납 완료 처리
	public int returningToReturned(int opCode, String prodCode, int prodPrice, int rentalPeriod, String email) {
		int result = 0;
		int savedMoney = (prodPrice * (rentalPeriod / 7)) * 5 / 100;
		String nextStep = "반납 완료";
		String odrState = mapper.checkOdrState(opCode);

		if (odrState.equals("반납 중")) {
			mapper.modifyOdrState(opCode, nextStep);
			mapper.modifyRentalState(prodCode);
			result = mapper.giveSavedMoney(email, savedMoney);
		}

		return result;
	}

}
