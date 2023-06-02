package com.hobbyvillage.backend.user_mypage;

import java.util.List;

public interface MyRequestPageService {
	int getRequestCount(String email, String filter);
	List<MyRequestPageDTO> getRequestList(String email, String filter, int pageNum);
	List<String> getRequestPictures(int reqCode);
	void withdrawRequest(int reqCode);
	void updateAccount(String reqBank, String reqAccountNum, int reqCode);
}
