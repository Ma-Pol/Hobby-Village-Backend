package com.hobbyvillage.backend.user_notices;

import java.util.List;

public interface UserNoticesService {
	int getNoticeCount(String filter);
	int getSearchNoticeCount(String filter, String keyword);
	List<UserNoticesDTO> getNoticeList(String filter, String sort, int pageNum);
	List<UserNoticesDTO> getSearchNoticeList(String filter, String keyword, String sort, int pageNum);
	UserNoticesDTO getNoticeDetail(int notCode);
	int updateNoticeView(int notCode);
}
