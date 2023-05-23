package com.hobbyvillage.backend.admin_faq;

import org.springframework.stereotype.Service;




@Service("faqService")
public class AdminFAQServiceImpl {

	private AdminFAQMapper mapper;
	
	public AdminFAQServiceImpl(AdminFAQMapper mapper) {
		this.mapper = mapper;
	}

	public int faqCreate(AdminFAQDTO faqs) {
		return mapper.faqCreate(faqs);
	}

	public AdminFAQDTO getFAQDetail(int faqCode) {
		AdminFAQDTO FAQDetail = mapper.getFAQDetail(faqCode);
		return FAQDetail;
	}

	public int deleteFAQ(AdminFAQDTO faqs) {
		int res = mapper.deleteFAQ(faqs);
		return res;
	}

	public int modifyFAQ(AdminFAQDTO faqs) {
		int res = mapper.modifyFAQ(faqs);
		return res;
	}
	
	
	
}
