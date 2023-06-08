package com.hobbyvillage.backend.admin_faq;

import java.util.List;

public interface AdminFaqsService {
	int getFaqCount(String filter);
	int getSearchFaqCount(String filter, String keyword);
	List<AdminFaqsDTO> getFaqList(String filter, String sort, int pageNum);
	List<AdminFaqsDTO> getSearchFaqList(String filter, String keyword, String sort, int pageNum);
	int checkFaq(int faqCode);
	AdminFaqsDTO getFaqDetail(int faqCode);
	int deleteFaq(int faqCode);
	int createFaq(AdminFaqsDTO faq);
	int modifyFaq(AdminFaqsDTO faq);
}
