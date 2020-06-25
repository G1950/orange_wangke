package com.guangming.mapper;

import com.guangming.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where _id=#{id}")
    User findUserInfoById(@Param("id") String id);
}
