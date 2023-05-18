package com.hobbyvillage.backend.user_requests;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserReqeustsServiceImpl implements UserRequestsService {

	private UserRequestsMapper mapper;

	public UserReqeustsServiceImpl(UserRequestsMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<String> getCategories() {
		return mapper.getCategories();
	}

	@Override
	public int createRequest(UserRequestsDTO request) {
		int result = 0;
		int check = mapper.createRequest(request);

		if (check == 1) {
			if (request.getReqSort().equals("판매")) {
				result = mapper.getReqCodeSell(request);
			} else {
				result = mapper.getReqCodeConsign(request);
			}
		}

		return result;
	}

	@Override
	public void insertFileName(int reqCode, String storedFileName) {
		mapper.insertFileName(reqCode, storedFileName);
	}
}
