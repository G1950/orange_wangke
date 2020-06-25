package com.guangming.mapper;


import com.guangming.pojo.Authority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AuthorityMapper {
    @Select("select * from authority where authority_account=#{account} and authority_pass=#{pass}")
    Authority findAuthorityInfoByAccountPwd(@Param("account") String account, @Param("pass") String password);
}
