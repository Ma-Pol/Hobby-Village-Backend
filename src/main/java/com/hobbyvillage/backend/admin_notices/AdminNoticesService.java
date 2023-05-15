package com.hobbyvillage.backend.admin_notices;

import java.util.List;

public interface AdminNoticesService {
	int getNoticeCount(String filter);
	int getSearchNoticeCount(String filter, String keyword);
	List<AdminNoticesDTO> getNoticeList(String filter, String sort, int pageNum);
	List<AdminNoticesDTO> getSearchNoticeList(String filter, String keyword, String sort, int pageNum);
	AdminNoticesDTO getNoticeDetail(int notCode);
	int createNotice(AdminNoticesDTO notice);
	int deleteNotice(int notCode);
}
