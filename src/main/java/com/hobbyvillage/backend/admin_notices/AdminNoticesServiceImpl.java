package com.hobbyvillage.backend.admin_notices;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.hobbyvillage.backend.UploadDir;

@Service
public class AdminNoticesServiceImpl implements AdminNoticesService {

	private AdminNoticesMapper mapper;

	public AdminNoticesServiceImpl(AdminNoticesMapper mapper) {
		this.mapper = mapper;
	}

	private String uploadPath = UploadDir.uploadDir + "\\Uploaded\\NoticesFile\\";

	// 필터 조건에 따른 쿼리문 설정 메서드
	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "notCategory IS NOT NULL";

		} else if (filter.equals("info")) {
			filter = "notCategory = '안내'";

		} else {
			filter = "notCategory = '이벤트'";
		}

		return filter;
	}

	@Override // 미검색 상태에서 공지사항 개수 조회
	public int getNoticeCount(String filter) {
		filter = filtering(filter);

		return mapper.getNoticeCount(filter);
	}

	@Override // 검색 상태에서 공지사항 개수 조회
	public int getSearchNoticeCount(String filter, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchNoticeCount(filter, keyword);
	}

	@Override // 미검색 상태에서 공지사항 목록 조회
	public List<AdminNoticesDTO> getNoticeList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getNoticeList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 공지사항 목록 조회
	public List<AdminNoticesDTO> getSearchNoticeList(String filter, String keyword, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchNoticeList(filter, keyword, sort, pageNum);
	}

	// 공지사항 상세 조회 1: 실재하는 공지사항인지 확인
	public int checkNotice(int notCode) {
		return mapper.checkNotice(notCode);
	}

	// 공지사항 상세 조회 2: 공지사항 상세 조회
	@Override
	public AdminNoticesDTO getNoticeDetail(int notCode) {
		return mapper.getNoticeDetail(notCode);
	}

	// 공지사항 상세 조회 3: 공지사항 파일 조회
	public List<AdminNoticesFileDTO> getFileData(int notCode) {
		List<AdminNoticesFileDTO> result = null;
		int count = mapper.getNoticeFileCount(notCode);

		if (count > 0) {
			result = mapper.getFileData(notCode);
		}

		return result;
	}

	// 공지사항 등록 1: 공지사항 등록
	@Override
	public int createNotice(AdminNoticesDTO notice) {
		int result = mapper.createNotice(notice);

		if (result == 1) {
			result = mapper.getNotCode(notice);
		}

		return result;
	}

	// 파일 업로드
	public int fileUpload(int notCode, MultipartFile[] uploadFile) throws IOException {
		int result = 0;

		for (MultipartFile file : uploadFile) {

			if (!file.isEmpty()) {
				String originalFileName = file.getOriginalFilename();
				String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

				File filePath = new File(uploadPath, storedFileName);

				file.transferTo(filePath);

				mapper.createFileName(notCode, originalFileName, storedFileName);

				result++;
			}
		}

		return result;
	}

	// 파일 다운로드
	public ResponseEntity<UrlResource> fileDownload(String originalFileName, String storedFileName)
			throws MalformedURLException, Exception {
		UrlResource resource = new UrlResource("file:" + uploadPath + "/" + storedFileName);

		String firstEncodedFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
		String secondEncodedFileName = URLEncoder.encode(firstEncodedFileName, "UTF-8");
		String contentDisposition = "attachment; filename=\"" + secondEncodedFileName + "\"";

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);

	}

	// 공지사항 삭제
	@Override
	public int deleteNotice(int notCode) {
		int result = 0;
		int check = mapper.checkNotice(notCode);

		if (check == 1) {
			int count = mapper.getNoticeFileCount(notCode);

			if (count > 0) {
				count = 0;

				List<String> fileNameList = mapper.getNoticeFileName(notCode);

				for (String fileName : fileNameList) {
					File filePath = new File(uploadPath + fileName);

					filePath.delete();
					count++;
				}

				if (count > 0) {
					result = mapper.deleteNotice(notCode);
				}

			} else {
				result = mapper.deleteNotice(notCode);
			}
		}

		return result;

	}

	// 공지사항 수정
	@Override
	public int modifyNotice(int notCode, AdminNoticesDTO notice) {
		notice.setNotCode(notCode);

		int result = 0;
		int count = mapper.getNoticeFileCount(notCode);

		if (count > 0) {
			count = 0;

			List<String> fileNameList = mapper.getNoticeFileName(notCode);

			for (String fileName : fileNameList) {
				File filePath = new File(uploadPath + fileName);

				filePath.delete();
				count++;
			}

			if (count > 0) {

				result = mapper.modifyNotice(notice);
			}

		} else {
			result = mapper.modifyNotice(notice);
		}

		return result;
	}

	// 공지사항 파일 삭제
	@Override
	public void deleteNoticeFileName(int notCode) {
		mapper.deleteNoticeFileName(notCode);
	}
}
