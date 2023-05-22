package com.hobbyvillage.backend.user_notices;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;

public interface UserNoticesService {
	int getNoticeCount(String filter);
	int getSearchNoticeCount(String filter, String keyword);
	List<UserNoticesDTO> getNoticeList(String filter, int pageNum);
	List<UserNoticesDTO> getSearchNoticeList(String filter, String keyword, int pageNum);
	int checkNotice(int notCode);
	UserNoticesDTO getNoticeDetail(int notCode);
	List<UserNoticesFileDTO> getFileData(int notCode);
	int updateNoticeView(int notCode);
	ResponseEntity<UrlResource> fileDownload(String originalFileName, String storedFileName) throws MalformedURLException, Exception;
}
