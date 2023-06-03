package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDibServiceimpl implements UserDibService {

	private UserDibMapper mapper;

	public UserDibServiceimpl(UserDibMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("all")) {
			filter = "";
		} else if (filter.equals("product")) {
			filter = "AND p.prodBrand IS NULL";
		} else if (filter.equals("brand")) {
			filter = "AND p.prodBrand IS NOT NULL";
		} else {
			filter = "잘못된 요청";
		}

		return filter;
	}

	// 찜 상품 개수 조회
	@Override
	public UserDibCountDTO getDibCount(String email) {
		int all = mapper.getAllDibCount(email);
		int brand = mapper.getBrandDibCount(email);

		UserDibCountDTO count = new UserDibCountDTO(all, all - brand, brand);

		return count;
	}

	// 전체 찜 상품 목록 조회
	@Override
	public List<UserDibDTO> getDibList(String email, String filter) {
		filter = filtering(filter);

		if (filter.equals("잘못된 요청")) {
			return null;
		}

		return mapper.getDibList(email, filter);
	}

	// 찜 단일 상품 삭제
	@Override
	public int deleteDib(int dibCode) {
		return mapper.deleteDib(dibCode);
	}

	// 찜 선택 상품 삭제
	@Override
	public int deleteSelectedDib(List<UserDibCodeDTO> dibList) {
		int result = 0;

		for (UserDibCodeDTO userDibCode : dibList) {
			deleteDib(userDibCode.getDibCode());
			result++;
		}

		return result;
	}

}
