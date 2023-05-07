package com.hobbyvillage.backend.admin_requests;

import java.util.List;

public interface AdminRequestsService {
	AdminRequestsDetailsDTO getRequestDetail(int reqCode);
	List<String> getRequestFileList(int reqCode);
	int updateRequestProgress(int reqCode, String reqProgress);
	int rejectRequestProgress(int reqCode);
	int cancelRequestProgress(int reqCode);
	int rejectCancelRequestProgress(int reqCode);
}
