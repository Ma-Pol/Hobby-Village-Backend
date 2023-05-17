package com.hobbyvillage.backend.user_requests;

import org.springframework.stereotype.Service;

@Service
public class UserReqeustsServiceImpl implements UserRequestsService {

	private UserRequestsMapper mapper;

	public UserReqeustsServiceImpl(UserRequestsMapper mapper) {
		this.mapper = mapper;
	}
}
