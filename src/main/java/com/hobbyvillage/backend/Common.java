package com.hobbyvillage.backend;

import java.io.*;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.*;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
public class Common {

	private static String import_baseURL;
	private static String api_Key;
	private static String api_Secret;
	private static String solapi_api_key;
	private static String solapi_secret_key;

	@Value("${import-base-url}")
	private void setImportBaseURL(String url) {
		import_baseURL = url;
	}

	@Value("${api-key}")
	private void setImportApiKey(String key) {
		api_Key = key;
	}

	@Value("${api-secret-key}")
	private void setImportApiSecretKey(String secretKey) {
		api_Secret = secretKey;
	}

	@Value("${solapi-api-key}")
	private void setSolapiApiKey(String key) {
		solapi_api_key = key;
	}

	@Value("${solapi-secret-key}")
	private void setSolapiApiSecretKey(String secretKey) {
		solapi_secret_key = secretKey;
	}

	// Upload 폴더 조회를 위한 프로젝트 폴더 절대경로 조회 필드
	public static final String uploadDir = System.getProperty("user.dir");

	// 결제용 아임포트 API 토큰 획득 메서드
	public static String getImportToken() throws IOException {
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

	// 문자 메세지 전송 API 메서드
	public static void sendMessage(String message, String phone) {
		DefaultMessageService messageSender = NurigoApp.INSTANCE.initialize(solapi_api_key, solapi_secret_key,
				"https://api.solapi.com");

		Message msg = new Message();
		msg.setFrom("01026336485");
		msg.setTo("01026336485"); // 실제 서비스 시에는 이 위치에 phone 설정
		msg.setText(message);

		try {
			messageSender.send(msg);
		} catch (NurigoMessageNotReceivedException e) {
			System.out.println(e.getFailedMessageList());
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
