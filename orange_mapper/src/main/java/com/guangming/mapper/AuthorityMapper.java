package com.guangming.mapper;


import com.guangming.pojo.Authority;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AuthorityMapper {

    //根据account，password查询
    @Select("select authority_id,authority_account,authority_pass,authority_role,last_login,status from authority where authority_account=#{account} and authority_pass=#{pass}")
    Authority findAuthorityInfoByAccountPwd(@Param("account") String account, @Param("pass") String password);

    @Select("select authority_id,authority_account,authority_pass,authority_role,last_login,status from authority where authority_id=#{id}")
    Authority findAuthorityInfoById(@Param("id") String id);

    @Select("select authority_id,authority_account,authority_pass,authority_role,last_login,status from authority where authority_account=#{account}")
    Authority findAuthorityInfoByAccount(@Param("account") String account);


    @Select("<script>" +
            "select authority_id,authority_account,authority_pass,authority_role,last_login,status from authority " +
            "where 1=1" +
            "<when test='authority.authority_id!=null and authority.authority_id!=\"\"'>and authority_id=#{authority.authority_id}</when>" +
            "<when test='authority.authority_account!=null and authority.authority_account!=\"\"'>and authority_account=#{authority.authority_account}</when>" +
            "<when test='authority.authority_pass!=null and authority.authority_pass!=\"\"'>and authority_pass=#{authority.authority_pass} </when>" +
            "<when test='authority.authority_role!=null and authority.authority_role!=\"\"'>and authority_role=#{authority.authority_role} </when>" +
            "<when test='authority.last_login!=null'>and last_login=#{authority.last_login} </when>" +
            "<when test='authority.status!=null'>and status=#{authority.status} </when>" +
            "</script>")
    List<Authority> queryAuthority(@Param("authority") Authority authority);



    //添加
    @Insert("insert into authority (authority_id,authority_account,authority_pass) values  " +
            "  (#{authority.authority_id},#{authority.authority_account},#{authority.authority_pass})")
    void saveAuthority(@Param("authority") Authority authority);


    //修改
    @Update("<script>" +
            "update authority" +
            "<set>" +
            "<if test='authority.authority_account!=null and authority.authority_account!=\"\"'>authority_account=#{authority.authority_account},</if>" +
            "<if test='authority.authority_pass!=null and authority.authority_pass!=\"\"'>authority_pass=#{authority.authority_pass},</if>" +
            "<if test='authority.authority_role!=null and authority.authority_role!=\"\"'>authority_role=#{authority.authority_role},</if>" +
            "<if test='authority.status!=null'>status=#{authority.status},</if>" +
            "</set>" +
            "where authority_id=#{authority.authority_id}" +
            "</script>")
    void updateAuthority(@Param("authority") Authority authority);
    //删除
    @Delete("<script> delete from authority " +
            "where authority_id in" +
            "<foreach collection ='id' item ='items' separator=',' open='(' close=')'  > " +
            "#{items}" +
            "</foreach>" +
            "</script>")
    void deleteAuthority(@Param("id") List<String> id);
}