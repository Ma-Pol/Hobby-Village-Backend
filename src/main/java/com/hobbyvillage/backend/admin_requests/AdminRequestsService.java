package com.hobbyvillage.backend.admin_requests;

import java.util.List;

public interface AdminRequestsService {
	int getRequestCount(String filter, String category);
	int getSearchRequestCount(String filter, String category, String condition, String keyword);
	List<AdminRequestsDTO> getRequestList(String filter, String category, String sort, int pageNum);
	List<AdminRequestsDTO> getSearchRequestList(String filter, String category, String condition, String keyword, String sort, int pageNum);
}
