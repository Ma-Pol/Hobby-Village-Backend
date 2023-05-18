package com.hobbyvillage.backend.user_requests;

import java.util.List;

public interface UserRequestsService {
	List<String> getCategories();
	int createRequest(UserRequestsDTO request);
	void insertFileName(int reqCode, String storedFileName);
}
