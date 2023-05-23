package com.hobbyvillage.backend.admin_faq;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class AdminFAQController {

	private AdminFAQServiceImpl faqService;

	public AdminFAQController(AdminFAQServiceImpl faqService) {
		this.faqService = faqService;
	}
	
	@RequestMapping("/m/faqs/create")
	public int faqCreate(@RequestBody AdminFAQDTO faqs) throws Exception {
		System.out.println(faqs.getFaqCategory());
		int res = faqService.faqCreate(faqs);

		return res;
	}
	
	@RequestMapping("/m/faqs/faqdetails/{faqCode}")
	public AdminFAQDTO getFAQDetail(@PathVariable(value = "faqCode", required = true) int faqCode) throws Exception {
		AdminFAQDTO FAQDetail = faqService.getFAQDetail(faqCode);
		return FAQDetail;
	}
	
	@PostMapping("/m/faqs/details/delete")
	public int deleteFAQ(@RequestBody AdminFAQDTO faqs) {
		int res = faqService.deleteFAQ(faqs);
		return res;
	}
	
	@PostMapping("/m/faqs/modify")
	public int modifyFAQ(@RequestBody AdminFAQDTO faqs) {
		int res = faqService.modifyFAQ(faqs);
		return res;
	}
	
//	@RequestMapping("/m/faqs/modify/{faqCode}")
//	public int modifyFAQ(@RequestBody FAQDTO faqs) throws Exception {
//		int res = faqService.modifyFAQ(faqs);
//		return res;
//	}
}
