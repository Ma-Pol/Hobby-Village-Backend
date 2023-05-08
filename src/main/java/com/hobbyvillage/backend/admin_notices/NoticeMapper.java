package com.hobbyvillage.backend.admin_notices;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface NoticeMapper {
    @Select("SELECT notCode, notCategory, notTitle, notDate FROM notices WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
    List<NoticeDTO> getAllNotice(@Param("filter") String filter, @Param("sort") String sort,
                                        @Param("pageNum") int pageNum);


}
