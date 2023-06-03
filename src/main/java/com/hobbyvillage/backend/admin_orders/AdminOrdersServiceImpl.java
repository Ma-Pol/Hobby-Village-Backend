package com.hobbyvillage.backend.admin_orders;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hobbyvillage.backend.Common;

@EnableScheduling
@Service
public class AdminOrdersServiceImpl implements AdminOrdersService {

	private AdminOrdersMapper mapper;

	@Value("${import-base-url}")
	private String import_baseURL;

	@Value("${sweettracker-base-url}")
	private String sweettracker_baseURL;

	@Value("${sweettracker-api-key}")
	private String sweettracker_api_Key;

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

	@Override
	public int checkOdrNumber(String odrNumber) {
		return mapper.checkOdrNumber(odrNumber);
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

	@Override // 주문 취소 처리 과정 1: 현재 주문 상태 확인
	public String checkOdrState(int opCode) {
		return mapper.checkOdrState(opCode);
	}

	@Override // 주문 취소 처리 과정 2: 취소 금액 및 환급 적립금 설정
	public int cancelOrder(AdminOrdersCancelOrderDTO data, String token) throws IOException {
		String odrNumber = data.getOdrNumber(), // 주문번호
				prodCode = data.getProdCode(), odrEmail = data.getOdrEmail(); // 주문자 이메일
		int opCode = data.getOpCode(), // 주문 상품 코드
				prodPrice = data.getProdPrice(), // 상품 단위 가격
				rentalPeriod = data.getRentalPeriod(), // 상품 대여 기간
				prodShipping = data.getProdShipping(), // 상품 배송비
				exactPrice = data.getExactPrice(), // 실제 주문 가격 (상품 최종 가격 - 사용 적립금)
				usedSavedMoney = data.getUsedSavedMoney(); // 사용 적립금
		int realPrice = (prodPrice * rentalPeriod / 7) + prodShipping; // 상품의 실제 총 가격
		int amount; // 아임포트 취소 금액
		int returnSavedMoney; // 환급 적립금

		int result; // 메서드 결과

		// 주문번호에 해당하는 상품의 총 개수 구하기
		String filter = "odrNumber = '" + odrNumber + "'";
		int allOrderProducts = mapper.getAllOrderCount(filter);

		// 주문 상품이 하나 뿐
		if (allOrderProducts == 1) {
			// 이 경우 exactPrice를 환불 금액으로 설정
			amount = exactPrice;
			returnSavedMoney = usedSavedMoney;
		}
		// 주문 상품이 둘 이상
		else {
			// 주문번호에 해당하는 상품들 중 취소 가능한 상품의 총 개수 구하기
			// (결제 완료, 배송 준비 중, 취소 요청)
			filter = "odrNumber = '" + odrNumber + "' AND odrState IN('결제 완료', '배송 준비 중', '취소 요청')";
			int cancelableProducts = mapper.getAllOrderCount(filter);

			// 해당 주문의 취소 금액과 사용 취소된 적립금을 조회
			AdminOrdersCancelPriceDTO canceledData = mapper.getCanceledData(odrNumber);
			int cancelPrice = canceledData.getCancelPrice();
			int cancelSavedMoney = canceledData.getCancelSavedMoney();

			// 주문 상품이 둘 이상이고, 환불 가능한 상품이 하나인 경우
			if (cancelableProducts == 1) {
				// 나머지 상품들이 모두 주문 취소된 상태인지 확인
				filter = "odrNumber = '" + odrNumber + "' AND odrState = '주문 취소 완료'";
				int canceledProducts = mapper.getAllOrderCount(filter);

				// 전체 상품 수 = 취소 가능 상품(1) + 취소된 상품인 경우
				if (allOrderProducts == cancelableProducts - canceledProducts) {
					// == 나머지 상품이 모두 취소된 상태에서 마지막 상품까지 취소하는 상황
					amount = exactPrice - cancelPrice;
					returnSavedMoney = usedSavedMoney - cancelSavedMoney;
				}
				// 배송 중 ~ 반납 완료 상태인 상품이 하나 이상 존재하는 상황(전액 환불 불가)
				else {
					if (exactPrice - cancelPrice <= 100) {
						// 최종 남은 주문 금액이 100원 이하인 경우, 아임포트 환불 없이 DB에서만 처리
						mapper.cnclReqTocnclComp(opCode); // '주문 취소 완료' 처리
						mapper.modifyRentalState(prodCode); // 상품 상태 미대여로 변경

						// 환급할 적립금 설정
						// 주문에 저장된 남은 적립금이 상품 가격이상인 경우, 환급 적립금 = 상품 가격
						if (usedSavedMoney - cancelSavedMoney >= realPrice) {
							returnSavedMoney = realPrice;
						}
						// 남은 적립금이 상품 가격 미만인 경우, 환급 적립금 = 남은 적립금
						else {
							returnSavedMoney = usedSavedMoney - cancelSavedMoney;
						}

						mapper.returnSavedMoney(odrEmail, returnSavedMoney); // 적립금 환급
						return mapper.cancelSavedMoneyChange(odrNumber, returnSavedMoney); // 주문 테이블 cancelSavedMoney 설정
					}
					// 최종 남은 주문 금액이 100원보다 많은 경우
					else {
						// 상품 금액이 남은 주문 금액 - 100 이하인 경우, amount = 상품 가격, 환급 적립금 = 0
						if (realPrice <= exactPrice - cancelPrice - 100) {
							amount = realPrice;
							returnSavedMoney = 0;
						}
						// 상품 금액이 남은 주문 금액 - 100 보다 큰 경우, amount = 남은 주문 금액 - 100
						else {
							amount = exactPrice - cancelPrice - 100;

							// 환급할 적립금 설정
							// 주문에 저장된 남은 적립금이 상품 가격이상인 경우, 환급 적립금 = 상품 가격
							if (usedSavedMoney - cancelSavedMoney >= realPrice) {
								returnSavedMoney = realPrice;
							}
							// 남은 적립금이 상품 가격 미만인 경우, 환급 적립금 = 남은 적립금
							else {
								returnSavedMoney = usedSavedMoney - cancelSavedMoney;
							}
						}
					}
				}
			}
			// 주문 상품이 둘 이상이고, 환불 가능한 상품이 둘 이상인 경우
			else {
				// 남은 주문 금액이 100원 이하인 경우
				if ((exactPrice - cancelPrice) <= 100) {
					// 남은 적립금이 0원이라면
					if ((usedSavedMoney - cancelSavedMoney) == 0) {
						mapper.cnclReqTocnclComp(opCode); // '주문 취소 완료' 처리
						mapper.modifyRentalState(prodCode); // 상품 상태 미대여로 변경
						return 1;
					}
					// 남은 적립금이 0원이 아니라면
					else {
						// 상품 금액이 남은 적립금이하인 경우, 환급 적립금 = 상품 금액
						if (realPrice <= (usedSavedMoney - cancelSavedMoney)) {
							returnSavedMoney = realPrice;
						}
						// 상품 금액이 남은 적립금보다 큰 경우, 남은 적립금 모두 환급
						else {
							returnSavedMoney = usedSavedMoney - cancelSavedMoney;
						}

						mapper.cnclReqTocnclComp(opCode); // '주문 취소 완료' 처리
						mapper.modifyRentalState(prodCode); // 상품 상태 미대여로 변경
						mapper.returnSavedMoney(odrEmail, returnSavedMoney); // 적립금 환급
						return mapper.cancelSavedMoneyChange(odrNumber, returnSavedMoney); // 주문 테이블 cancelSavedMoney 설정
					}
				}
				// 남은 주문 금액이 100원보다 많은 경우
				else {
					// 상품 금액이 남은 주문 금액 - 100 이하인 경우, amount = 상품 금액, 환급 적립금 = 0
					if (realPrice <= (exactPrice - cancelPrice - 100)) {
						amount = realPrice;
						returnSavedMoney = 0;
					}
					// 상품 금액이 남은 주문 금액 - 100 보다 큰 경우, amount = exactPrice - cancelPrice - 100
					else {
						amount = exactPrice - cancelPrice - 100;

						// 상품 금액 - amount 가 남은 적립금 이하라면,
						// returnSavedMoney = (prodPrice * rentalPeriod / 7) + prodShipping - amount
						if (realPrice - amount <= (usedSavedMoney - cancelSavedMoney)) {
							returnSavedMoney = realPrice - amount;
						}
						// 상품 금액이 남은 적립금보다 많다면 returnSavedMoney = usedSavedMoney - cancelSavedMoney
						else {
							returnSavedMoney = usedSavedMoney - cancelSavedMoney;
						}
					}
				}
			}
		}

		// 여기서 아임포트 호출
		String imp_uid = mapper.getImpUid(odrNumber);

		result = cancelOrder(imp_uid, token);

		if (result == 0) {
			mapper.cnclReqTocnclComp(opCode); // '주문 취소 완료' 처리
			mapper.modifyRentalState(prodCode); // 상품 상태 미대여로 변경
			mapper.returnSavedMoney(odrEmail, returnSavedMoney); // 적립금 환급
			mapper.cancelPriceChange(odrNumber, amount); // 주문 테이블 cancelPrice 설정
			mapper.cancelSavedMoneyChange(odrNumber, returnSavedMoney); // 주문 테이블 cancelSavedMoney 설정
			result = 1;
		}

		return result;
	}

	public int cancelOrder(String imp_uid, String token) throws IOException {
		URL url = new URL(import_baseURL + "/payments/cancel");

		HttpsURLConnection con = null;

		con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Authorization", token);
		con.setDoOutput(true);

		JsonObject json = new JsonObject();

		json.addProperty("imp_uid", imp_uid);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

		bw.write(json.toString());
		bw.flush();
		bw.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

		Gson gson = new Gson();

		int code = gson.fromJson(br.readLine(), ImportPaymentCheckDTO.class).getCode();

		System.out.println("캔슬 code : " + code);

		br.close();
		con.disconnect();

		return code;
	}

	@Override // 반납 완료 처리
	public int returningToReturned(int opCode, String prodCode, int prodPrice, int rentalPeriod, String email) {
		int result = 0;
		int savedMoney = (prodPrice * (rentalPeriod / 7)) * 5 / 100;
		String nextStep = "반납 완료";
		String odrState = mapper.checkOdrState(opCode);

		if (odrState.equals("반납 중")) {

			// 위탁 철회 요청이 들어온 상품의 경우, 임시로 삭제 처리(추가 주문이 들어오지 않도록 하기 위함)
			if (mapper.checkCancelRequest(prodCode)) {
				mapper.setDeleteProduct(prodCode);
				mapper.deleteDibs(prodCode);
				mapper.deleteCarts(prodCode);
			}

			mapper.modifyOdrState(opCode, nextStep);
			mapper.modifyRentalState(prodCode);
			result = mapper.giveSavedMoney(email, savedMoney);
		}

		return result;
	}

	// 1시간 15분마다 상품의 배송 상태를 파악
	@Scheduled(fixedDelay = 4500000)
	private void deliveryTracking() {
		LocalDateTime now = LocalDateTime.now();
		List<AdminOrdersTrackingDTO> trackingList = mapper.getTrackingData();

		System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));
		System.out.println("===================================================");
		for (AdminOrdersTrackingDTO trackingData : trackingList) {
			try {
				// true 가 출력되면 배송 완료
				boolean isComplete = trackingResult(trackingData);

				if (isComplete) {
					// 주문 상태 및 deliDate, deadline 변경
					mapper.deliveryCompleted(trackingData.getOpCode());

					// 여기서 문자메세지 발송 준비
					AdminOrdersTrackingDTO smsData = mapper.getProdNameAndDeadline(trackingData.getOpCode());
					String phone = trackingData.getOdrPhone();
					String prodName = smsData.getProdName();
					Date deadline = smsData.getDeadline();

					String message = "안녕하세요, 취미빌리지입니다.\n\n고객님께서 주문하신 상품 [" + prodName + "] 이(가) 배송지에 도착했음을 알립니다.\n\n["
							+ prodName + "] 을(를) 통해 즐거운 취미 생활을 즐기시기를 바랍니다.\n\n반납 기한: [" + deadline + "]";

					Common.sendMessage(message, phone);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("===================================================\n");
	}

	// 매일 오후 12시 30분마다 상품 반납일을 체크 후 자동 메세지 전송
	@Scheduled(cron = "0 30 12 * * *")
	private void deadlineCheck() {
		List<AdminOrdersTrackingDTO> dataList = mapper.getDeadlineAndPhone();

		for (AdminOrdersTrackingDTO data : dataList) {
			String prodName = data.getProdName();
			String phone = data.getOdrPhone();
			Date deadline = data.getDeadline();
			String message = "";

			if (data.getDatediff() == 7) {
				message = "안녕하세요, 취미빌리지입니다.\n\n고객님께서 주문하신 상품 [" + prodName + "] 의 대여 기간이 만료되었습니다.\n\n[" + prodName
						+ "] 을/를 통해 즐거운 취미 생활을 즐기셨기를 바랍니다.\n\n반납 기한 내에 취미빌리지 사이트에서 해당 상품의 반납 신청을 완료해주시기 바랍니다."
						+ "\n\n반납 신청 위치: 마이 페이지 > 주문 목록\n\n반납 기한: [" + deadline + "]";
				Common.sendMessage(message, phone);

			} else if (data.getDatediff() == 3) {
				message = "안녕하세요, 취미빌리지입니다.\n\n고객님께서 주문하신 상품 [" + prodName + "] 의 반납 기한이 3일 남았습니다.\n\n"
						+ "반납 기한 내에 취미빌리지 사이트에서 해당 상품의 반납 신청을 완료해주시기 바랍니다.\n\n반납 신청 위치: 마이 페이지 > 주문 목록" + "\n\n반납 기한: ["
						+ deadline + "]";
				Common.sendMessage(message, phone);
			} else if (data.getDatediff() == 1) {
				message = "안녕하세요, 취미빌리지입니다.\n\n고객님께서 주문하신 상품 [" + prodName + "] 의 반납 기한이 하루 남았습니다.\n\n"
						+ "반납 기한 내에 취미빌리지 사이트에서 해당 상품의 반납 신청을 완료해주시기 바랍니다.\n\n반납 신청 위치: 마이 페이지 > 주문 목록" + "\n\n반납 기한: ["
						+ deadline + "]";
				Common.sendMessage(message, phone);
			} else if (data.getDatediff() == 0) {
				message = "안녕하세요, 취미빌리지입니다.\n\n고객님께서 주문하신 상품 [" + prodName + "] 의 반납 기한 당일입니다.\n\n"
						+ "취미빌리지 사이트에서 해당 상품의 반납 신청을 완료해주시기 바랍니다.\n\n반납 신청 위치: 마이 페이지 > 주문 목록\n\n반납 기한: [" + deadline
						+ "]";
				Common.sendMessage(message, phone);
			}
		}
	}

	@Override // 배송 추적 메서드
	public boolean trackingResult(AdminOrdersTrackingDTO trackingData) throws IOException {
		String courierCompany = trackingData.getCourierCompany();
		String trackingNumber = trackingData.getTrackingNumber();

		String trueURL = sweettracker_baseURL + "/api/v1/trackingInfo?t_key=" + sweettracker_api_Key;
		trueURL += "&t_code=";
		trueURL += courierCompany;
		trueURL += "&t_invoice=";
		trueURL += trackingNumber;

		URL url = new URL(trueURL);

		HttpURLConnection con = null;

		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

		Map<String, Object> response = new ObjectMapper().readValue(br.readLine(), Map.class);

		String code = (String) response.get("code");
		if (code != null) {
			String msg = (String) response.get("msg");
			System.out.println(msg); // 배송 조회 실패 시 실패 메세지를 콘솔에 표시
			return false;
		}

		Boolean complete = (Boolean) response.get("complete");

		br.close();
		con.disconnect();

		return complete;
	}

}
