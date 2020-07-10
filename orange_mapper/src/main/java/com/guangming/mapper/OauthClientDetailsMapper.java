package com.guangming.mapper;

import com.guangming.pojo.OauthClientDetails;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OauthClientDetailsMapper {
    //增
    @Insert("insert into oauth_client_details (client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri," +
            "authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove) values  " +
            "  (#{oauth.client_id},#{oauth.resource_ids},#{oauth.client_secret},#{oauth.scope},#{oauth.authorized_grant_types},#{oauth.web_server_redirect_uri}," +
            " #{oauth.authorities},#{oauth.access_token_validity},#{oauth.refresh_token_validity},#{oauth.additional_information},#{oauth.autoapprove} )")
    void save(@Param("oauth") OauthClientDetails oauthClientDetails);

    //删
    @Delete("delete from oauth_client_details " +
            "where client_id=#{id} ")
    void deleteById(@Param("id") String id);

    //改
    @Update("<script>" +
            "update oauth_client_details" +
            "<set>" +
            "<if test='oauth.resource_ids!=null and oauth.resource_ids!=\"\"'>resource_ids=#{oauth.resource_ids},</if>" +
            "<if test='oauth.client_secret!=null and oauth.client_secret!=\"\"'>client_secret=#{oauth.client_secret},</if>" +
            "<if test='oauth.scope!=null and oauth.scope!=\"\"'>scope=#{oauth.scope},</if>" +
            "<if test='oauth.authorized_grant_types!=null and oauth.authorized_grant_types!=\"\"'>authorized_grant_types=#{oauth.authorized_grant_types},</if>" +
            "<if test='oauth.web_server_redirect_uri!=null and oauth.web_server_redirect_uri!=\"\"'>web_server_redirect_uri=#{oauth.web_server_redirect_uri},</if>" +
            "<if test='oauth.authorities!=null and oauth.authorities!=\"\"'>authorities=#{oauth.authorities},</if>" +
            "<if test='oauth.access_token_validity!=null'>access_token_validity=#{oauth.access_token_validity},</if>" +
            "<if test='oauth.refresh_token_validity!=null'>refresh_token_validity=#{oauth.refresh_token_validity},</if>" +
            "<if test='oauth.additional_information!=null and oauth.additional_information!=\"\"'>additional_information=#{oauth.additional_information},</if>" +
            "<if test='oauth.autoapprove!=null and oauth.autoapprove!=\"\"'>autoapprove=#{oauth.autoapprove},</if>" +
            "</set>" +
            "where client_id=#{oauth.client_id}" +
            "</script>")
    void update(@Param("oauth") OauthClientDetails oauthClientDetails);

    //查
    @Select("<script>" +
            "select client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri," +
            "authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove from oauth_client_details " +
            "where 1=1" +
            "<when test='oauth.client_id!=null and oauth.client_id!=\"\"'> and client_id=#{oauth.client_id}</when>" +
            "<when test='oauth.resource_ids!=null and oauth.resource_ids!=\"\"'> and resource_ids=#{oauth.resource_ids}</when>" +
            "<when test='oauth.client_secret!=null and oauth.client_secret!=\"\"'> and client_secret=#{oauth.client_secret}</when>" +
            "<when test='oauth.scope!=null and oauth.scope!=\"\"'> and scope=#{oauth.scope}</when>" +
            "<when test='oauth.authorized_grant_types!=null and oauth.authorized_grant_types!=\"\"'> and authorized_grant_types=#{oauth.authorized_grant_types}</when>" +
            "<when test='oauth.web_server_redirect_uri!=null and oauth.web_server_redirect_uri!=\"\"'> and web_server_redirect_uri=#{oauth.web_server_redirect_uri}</when>" +
            "<when test='oauth.authorities!=null and oauth.authorities!=\"\"'> and authorities=#{oauth.authorities}</when>" +
            "<when test='oauth.access_token_validity!=null'> and access_token_validity=#{oauth.access_token_validity}</when>" +
            "<when test='oauth.refresh_token_validity!=null'> and refresh_token_validity=#{oauth.refresh_token_validity}</when>" +
            "<when test='oauth.additional_information!=null and oauth.additional_information!=\"\"'> and additional_information=#{oauth.additional_information}</when>" +
            "<when test='oauth.autoapprove!=null and oauth.autoapprove!=\"\"'> and autoapprove=#{oauth.autoapprove}</when>" +
            "</script>")
    List<OauthClientDetails> query(@Param("oauth") OauthClientDetails oauthClientDetails);

    @Select("select client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri," +
            " authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove from oauth_client_details where client_id=#{id}")
    OauthClientDetails queryById(@Param("id") String id);
}
