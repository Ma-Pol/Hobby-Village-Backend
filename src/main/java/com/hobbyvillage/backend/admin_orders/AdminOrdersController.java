package com.hobbyvillage.backend.admin_orders;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.hobbyvillage.backend.Common;

@RestController
@RequestMapping("/m/orders")
public class AdminOrdersController {

	private AdminOrdersServiceImpl adminOrdersServiceImpl;

	public AdminOrdersController(AdminOrdersServiceImpl adminOrdersServiceImpl) {
		this.adminOrdersServiceImpl = adminOrdersServiceImpl;
	}

	// 주문 개수 조회
	@GetMapping("/count")
	public int getOrderCount(@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int orderCount;

		// 검색 여부 확인
		if (keyword == null) {
			orderCount = adminOrdersServiceImpl.getOrderCount(sort, filter);
		} else {
			orderCount = adminOrdersServiceImpl.getSearchOrderCount(sort, condition, keyword, filter);
		}

		return orderCount;
	}

	// 주문 목록 조회
	@GetMapping("")
	public List<AdminOrdersDTO> getOrderList(@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminOrdersDTO> orderList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			orderList = adminOrdersServiceImpl.getOrderList(sort, filter, pageNum);
		} else {
			orderList = adminOrdersServiceImpl.getSearchOrderList(condition, keyword, sort, filter.trim(), pageNum);
		}

		return orderList;
	}

	@GetMapping("/check/{odrNumber}")
	public int checkOdrNumber(@PathVariable(value = "odrNumber", required = true) String odrNumber) {
		return adminOrdersServiceImpl.checkOdrNumber(odrNumber);
	}

	// 주문 상세 조회
	@GetMapping("/orderDetails/{odrNumber}")
	AdminOrdersDetailDTO getOrderDetail(@PathVariable(value = "odrNumber", required = true) String odrNumber) {
		return adminOrdersServiceImpl.getOrderDetail(odrNumber);
	}

	// 각 주문 별 상품 목록 조회
	@GetMapping("/productLists/{odrNumber}")
	List<AdminOrdersProductsDTO> getOrderedProductList(
			@PathVariable(value = "odrNumber", required = true) String odrNumber) {
		return adminOrdersServiceImpl.getOrderedProductList(odrNumber);
	}

	// 주문자 주소 수정
	@PatchMapping("/modify")
	public int modifyOrderAddress(@RequestBody AdminOrdersDetailDTO addressData) {
		return adminOrdersServiceImpl.modifyOrderAddress(addressData);
	}

	// 주문 확인 처리 (결제 완료 -> 배송 준비 중)
	@PatchMapping("/odrState/preparing-for-delivery")
	public int payCompToPreDeli(@RequestBody Map<String, String> stateMap) {
		String odrNumber = stateMap.get("odrNumber");

		return adminOrdersServiceImpl.payCompToPreDeli(odrNumber);
	}

	// 운송 정보 등록 처리 (배송 준비 중 -> 배송 중)
	@PatchMapping("/odrState/shipping")
	public int preDeliToShipping(@RequestBody AdminOrdersProductsDTO data) {
		int opCode = data.getOpCode();
		String courierCompany = data.getCourierCompany();
		String trackingNumber = data.getTrackingNumber();

		return adminOrdersServiceImpl.preDeliToShipping(opCode, courierCompany, trackingNumber);
	}

	// 주문 취소 처리 과정 1: 주문 상품 상태 확인
	@GetMapping("/odrState/{opCode}")
	public String checkOdrState(@PathVariable(value = "opCode", required = true) int opCode) {
		return adminOrdersServiceImpl.checkOdrState(opCode);
	}

	// 주문 취소 처리 과정 2: 실제 주문 취소
	@PostMapping("/cancelOrder")
	public void cancelOrder(@RequestBody AdminOrdersCancelOrderDTO data) throws IOException {
		String token = Common.getImportToken();
		adminOrdersServiceImpl.cancelOrder(data, token);
	}

	// 반납 완료 처리 (반납 중 -> 반납 완료)
	@PatchMapping("/returned")
	public int returningToReturned(@RequestBody Map<String, String> data) {
		int opCode = Integer.parseInt(data.get("opCode"));
		String prodCode = data.get("prodCode");
		int prodPrice = Integer.parseInt(data.get("prodPrice"));
		int rentalPeriod = Integer.parseInt(data.get("rentalPeriod"));
		String email = data.get("odrEmail");

		return adminOrdersServiceImpl.returningToReturned(opCode, prodCode, prodPrice, rentalPeriod, email);
	}
}
