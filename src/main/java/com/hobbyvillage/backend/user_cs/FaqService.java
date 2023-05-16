package com.hobbyvillage.backend.user_cs;

import java.util.List;

public interface FaqService {
	int getFaqCount(String filter);
	int getSearchFaqCount(String filter, String keyword);
	List<FaqDTO> getFaqList(String filter, int pageNum);
	List<FaqDTO> getSearchFaqList(String filter, String keyword, int pageNum);
	FaqDTO getFaqDetail(Integer faqCode);
}