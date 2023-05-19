package com.hobbyvillage.backend.admin_notices;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface AdminNoticesService {
	int getNoticeCount(String filter);
	int getSearchNoticeCount(String filter, String keyword);
	List<AdminNoticesDTO> getNoticeList(String filter, String sort, int pageNum);
	List<AdminNoticesDTO> getSearchNoticeList(String filter, String keyword, String sort, int pageNum);
	int checkNotice(int notCode);
	AdminNoticesDTO getNoticeDetail(int notCode);
	List<AdminNoticesFileDTO> getFileData(int notCode);
	int createNotice(AdminNoticesDTO notice);
	int fileUpload(int notCode, MultipartFile[] uploadFile) throws IOException;
	ResponseEntity<UrlResource> fileDownload(String originalFileName, String storedFileName) throws MalformedURLException, Exception;
	int deleteNotice(int notCode);
	int modifyNotice(int notCode, AdminNoticesDTO notice);
	void deleteNoticeFileName(int notCode);
}
