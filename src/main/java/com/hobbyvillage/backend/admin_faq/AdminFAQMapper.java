package com.hobbyvillage.backend.admin_faq;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;




@Mapper
public interface AdminFAQMapper {

	@Insert("INSERT INTO faqs(faqTitle,faqCategory,faqContent) VALUES (#{faqTitle}, #{faqCategory}, #{faqContent})")
	int faqCreate(AdminFAQDTO faqs);
	
	@Select("SELECT faqCode, faqTitle, faqCategory, faqContent FROM faqs WHERE faqCode = #{faqCode};")
	AdminFAQDTO getFAQDetail(@Param("faqCode")int faqCode);

	@Delete("DELETE FROM faqs WHERE faqCode = #{faqCode}; ")
	int deleteFAQ(AdminFAQDTO faqs);
	
	@Update("UPDATE faqs SET faqTitle = #{faqTitle}, faqCategory = #{faqCategory}, faqContent = #{faqContent}  "
			+ " WHERE faqCode = ${faqCode}; ")
	int modifyFAQ(AdminFAQDTO faqs);


}
