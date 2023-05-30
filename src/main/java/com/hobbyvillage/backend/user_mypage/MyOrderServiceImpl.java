package com.hobbyvillage.backend.user_mypage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.hobbyvillage.backend.Common;

@Service
public class MyOrderServiceImpl implements MyOrderPageService {

	private MyOrderPageMapper mapper;

	public MyOrderServiceImpl(MyOrderPageMapper mapper) {
		this.mapper = mapper;
	}

	private String profileUploadPath = Common.uploadDir + "\\Uploaded\\ProductsImage\\";

	private String odrStateFiltering(String odrState) {
		if (odrState.equals("payment-completed")) {
			odrState = "결제 완료";

		} else if (odrState.equals("preparing-for-delivery")) {
			odrState = "배송 준비 중";

		} else if (odrState.equals("shipping")) {
			odrState = "배송 중";

		} else if (odrState.equals("delivery-completed")) {
			odrState = "배송 완료";

		} else if (odrState.equals("returning")) {
			odrState = "반납 중";

		} else if (odrState.equals("returned")) {
			odrState = "반납 완료";

		} else if (odrState.equals("cancel-request")) {
			odrState = "취소 요청";

		} else {
			odrState = "취소 처리 완료";
		}

		return odrState;
	}

	// 주문 상태 별 주문 상품 개수 조회
	@Override
	public List<Integer> getOdrStateCount(String email) {
		List<Integer> result = new ArrayList<>();

		List<String> stateList = List.of("결제 완료", "배송 준비 중", "배송 중", "배송 완료", "반납 중", "반납 완료", "취소 요청", "취소 처리 완료");

		for (String odrState : stateList) {
			result.add(mapper.getOdrStateCount(email, odrState));
		}

		return result;
	}

	// 주문 상태 별 주문 목록 조회
	@Override
	public List<MyOrderListDTO> getOrderList(String email, String odrState) {
		odrState = odrStateFiltering(odrState);
		return mapper.getOrderList(email, odrState);
	}

	// 주문 별 상품 이미지 명 조회
	@Override
	public String getProdPicture(String prodCode) {
		return mapper.getProdPicture(prodCode);
	}

	// 리뷰 작성 여부 확인
	@Override
	public boolean checkReviewed(String prodCode, String nickname) {
		return mapper.checkReviewed(prodCode, nickname);
	}

	// 이미지 출력
	@Override
	public ResponseEntity<byte[]> printProdPicture(String prodPicture) throws IOException {
		File file = new File(profileUploadPath + prodPicture);
		ResponseEntity<byte[]> result = null;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

		return result;
	}

	// 주문 물품 반납 처리
	@Override
	public int returnProduct(int opCode) {
		String odrState = "반납 중";
		return mapper.changeProduct(opCode, odrState);
	}

	// 주문 물품 반납 처리
	@Override
	public int cancelProduct(int opCode) {
		String odrState = "취소 요청";
		return mapper.changeProduct(opCode, odrState);
	}

}
