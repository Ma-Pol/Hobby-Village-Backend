package com.hobbyvillage.backend.user_purchase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class UserPurchaseServiceImpl implements UserPruchaseService {

	@Value("${import-base-url}")
	private String import_baseURL;

	@Value("${api-key}")
	private String api_Key;

	@Value("${api-secret-key}")
	private String api_Secret;

	private UserPurchaseMapper mapper;

	public UserPurchaseServiceImpl(UserPurchaseMapper mapper) {
		this.mapper = mapper;
	}

	private String prevPageFiltering(String prevPage) {
		String searchTable = "";

		if (!prevPage.equals("mypages")) {
			searchTable = "orders";
		} else {
			searchTable = "exOrders";
		}

		return searchTable;
	}

	@Override // 아임포트 API 사용을 위한 토큰 획득 메서드
	public String getImportToken() throws IOException {
		// 요청 URL 설정
		URL url = new URL(import_baseURL + "/users/getToken");

		// 요청(통신) 객체 생성
		HttpsURLConnection con = null;

		// 요청 URL, 요청 방식, 요청 데이터 타입(이곳 -> 아임포트), 응답 데이터 타입(아임포트 -> 이곳)
		con = (HttpsURLConnection) url.openConnection(); // 요청 URL 설정
		con.setRequestMethod("POST"); // 요청 방식 설정
		con.setRequestProperty("Content-type", "application/json"); // 요청 데이터 타입(이곳 -> 아임포트) 설정
		con.setRequestProperty("Accept", "application/json"); // 응답 데이터 타입(아임포트 -> 이곳) 설정
		con.setDoOutput(true); // POST 방식으로 BODY에 데이터를 담아 보내도록 설정

		// BODY에 담을 JSON 형식의 데이터를 만들기 위한 JSON 객체 생성
		JsonObject json = new JsonObject();

		// JSON 객체에 값 입력
		json.addProperty("imp_key", api_Key);
		json.addProperty("imp_secret", api_Secret);

		// 데이터 전송 준비
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		// 전송할 데이터를 버퍼에 저장
		bw.write(json.toString());
		// 데이터 전송
		bw.flush();
		// 데이터 전송 객체 종료
		bw.close();

		// 아임포트의 응답 데이터를 버퍼에 저장
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

		// 응답 데이터(JSON 형식)를 파싱하기 위한 GSON 객체 생성
		Gson gson = new Gson();

		// 버퍼에 저장된 응답 데이터를 Map 형식으로 불러온 뒤,
		// .get("response")로 key가 response인 value를 받아오고,
		// .toString()으로 해당 value를 String 타입으로 변환해 저장
		String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

		// 마찬가지의 과정으로 response 내에서 key가 access_token인 value를 받아와 String 타입으로 변환해 저장
		String token = gson.fromJson(response, Map.class).get("access_token").toString();

		// 응답 데이터 객체 종료

		br.close();
		// 요청 객체 종료
		con.disconnect();

		return token;
	}

	@Override
	public int getProductState(String prodCode) {
		return mapper.getProductState(prodCode);
	}

	@Override
	public int checkProductInfo(String prodCode, int prodPrice, int prodShipping, String prodHost, int period) {
		int result = mapper.checkProductInfo(prodCode, prodPrice, prodShipping, prodHost);

		if (result == 1) {
			if (period % 7 != 0) {
				result = 0;
			}
		}

		return result;
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

	@Override
	public int updateProductState(String prodCode) {
		return mapper.updateProductState(prodCode);
	}

	@Override
	public String getPrevOdrNumber(String prodCode) {
		return mapper.getPrevOdrNumber(prodCode);
	}

	@Override
	public int purchasePreProcess(UserPurchaseProcessDTO data, String prevPage) {
		int result;

		if (!prevPage.equals("mypages")) {
			result = mapper.purchasePreProcess1(data);
		} else {
			result = mapper.purchasePreProcess2(data);
		}

		return result;
	}

	@Override
	public int paymentsPrepare(String odrNumber, int amount, String token) throws IOException {
		int result = -1;
		URL url = new URL(import_baseURL + "/payments/prepare");

		HttpsURLConnection con = null;

		con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Authorization", token);
		con.setDoOutput(true);

		JsonObject json = new JsonObject();

		json.addProperty("merchant_uid", odrNumber);
		json.addProperty("amount", amount);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

		bw.write(json.toString());
		bw.flush();
		bw.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

		Gson gson = new Gson();

		String code = gson.fromJson(br.readLine(), Map.class).get("code").toString();

		Double code2 = Double.valueOf(code);

		result = (int) Math.round(code2);

		br.close();
		con.disconnect();

		return result; // 정상 동작했을 경우 0 return
	}

	@Override
	public int compareToDatabase(String odrNumber, int paid_amount, String prevPage) {
		String searchTable = prevPageFiltering(prevPage);

		return mapper.compareToDatabase(odrNumber, paid_amount, searchTable);
	}

	@Override
	public int compareToImport(String odrNumber, int paid_amount, String prevPage, String imp_uid, String token)
			throws IOException {
		int result = 0;

		String searchTable = prevPageFiltering(prevPage);
		int exactPrice = mapper.compareToImport(odrNumber, paid_amount, searchTable);

		URL url = new URL(import_baseURL + "/payments/" + imp_uid);

		HttpsURLConnection con = null;

		con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Authorization", token);
		con.setDoOutput(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

		Gson gson = new Gson();

		ImportPaymentCheckDTO response = gson.fromJson(br.readLine(), ImportPaymentCheckDTO.class);

		int amount = (int) Math.round((Double) response.getResponse().get("amount"));

		br.close();
		con.disconnect();

		if (exactPrice == amount) {
			result = 1;
		}

		return result;
	}

	@Override
	public int increaseRentalCount(String prodCode, int rentalCount) {
		return mapper.increaseRentalCount(prodCode, rentalCount);
	}

	@Override
	public int decreaseSavedMoney(String email, int exactSavedMoney) {
		return mapper.decreaseSavedMoney(email, exactSavedMoney);
	}

	@Override
	public int deleteCoupon(String email, int couponCode) {
		return mapper.deleteCoupon(email, couponCode);
	}

	@Override
	public int purchaseFinalProcess(UserPurchaseProcessDTO data, String prevPage) {
		String odrNumber = data.getOdrNumber();
		String imp_uid = data.getImp_uid();
		String searchTable = prevPageFiltering(prevPage);
		int result = 0;

		if (!prevPage.equals("mypages")) {
			result = mapper.insertOrderProduct(data);
		} else {
			result = mapper.updateOrderProduct(data);
		}

		if (result == 1) {
			result = mapper.updateImpUid(odrNumber, imp_uid, searchTable);
		}

		return result;
	}

	@Override
	public int updateProductStateFailed(String prodCode) {
		return mapper.updateProductStateFailed(prodCode);
	}

	@Override
	public int deleteOrder(String odrNumber, String prevPage) {
		String searchTable = prevPageFiltering(prevPage);

		return mapper.deleteOrder(odrNumber, searchTable);
	}

	@Override
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
}
