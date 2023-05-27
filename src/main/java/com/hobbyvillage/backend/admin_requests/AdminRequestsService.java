package com.hobbyvillage.backend.admin_requests;

import java.util.List;

public interface AdminRequestsService {
	int getRequestCount(String filter, String category);
	int getSearchRequestCount(String filter, String category, String condition, String keyword);
	List<AdminRequestsDTO> getRequestList(String filter, String category, String sort, int pageNum);
	List<AdminRequestsDTO> getSearchRequestList(String filter, String category, String condition, String keyword, String sort, int pageNum);
	int checkReqeust(int reqCode);
	AdminRequestsDetailsDTO getRequestDetail(int reqCode);
	List<String> getRequestFileList(int reqCode);
	int updateRequestProgress(int reqCode, String reqProgress, String reqTitle, String reqPhone);
	int rejectRequestProgress(AdminRequestsRejectDTO rejectData);
	int cancelRequestProgress(int reqCode);
	int rejectCancelRequestProgress(int reqCode);
}
