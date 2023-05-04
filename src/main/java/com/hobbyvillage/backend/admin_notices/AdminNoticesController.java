package com.hobbyvillage.backend.admin_notices;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/notices")
public class AdminNoticesController {

	private AdminNoticesServiceImpl adminNoticesServiceImpl;

	public AdminNoticesController(AdminNoticesServiceImpl adminNoticesServiceImpl) {
		this.adminNoticesServiceImpl = adminNoticesServiceImpl;
	}

	@GetMapping("/count")
	public int getNoticeCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int noticeCount;

		if (keyword == null) {
			noticeCount = adminNoticesServiceImpl.getNoticeCount(filter);
		} else {
			noticeCount = adminNoticesServiceImpl.getSearchNoticeCount(filter, keyword);
		}

		return noticeCount;
	}

	@GetMapping("")
	public List<AdminNoticesDTO> getNoticeList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminNoticesDTO> noticeList;
		int pageNum = (pages - 1) * 10;

		if (keyword == null) {
			noticeList = adminNoticesServiceImpl.getNoticeList(filter, sort, pageNum);
		} else {
			noticeList = adminNoticesServiceImpl.getSearchNoticeList(filter, keyword, sort, pageNum);
		}

		return noticeList;
	}

}
