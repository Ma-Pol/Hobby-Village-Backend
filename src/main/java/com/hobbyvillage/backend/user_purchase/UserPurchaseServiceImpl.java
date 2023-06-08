package com.hobbyvillage.backend.user_purchase;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.*;

@Service
public class UserPurchaseServiceImpl implements UserPruchaseService {

	@Value("${import-base-url}")
	private String import_baseURL;

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

	// 결제 전 금액 저장
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

	// 결제 후 금액 비교
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
	public void deleteCart(String email, String prodCode) {
		mapper.deleteCart(email, prodCode);
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

	// 결제 금액 불일치 시 환불
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
