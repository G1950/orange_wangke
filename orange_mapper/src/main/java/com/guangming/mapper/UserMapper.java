package com.guangming.mapper;

import com.guangming.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    //根据id查询
   @Select("select id,nickname,email,phone,avatar_url from user where id=#{id}")
    User findUserInfoById(@Param("id") String id);

   //根据user查询
    @Select("<script>" +
            "select id,nickname,email,phone,avatar_url from user " +
            "where 1=1" +
            "<when test='user.id!=null and user.id!=\"\"'>and id=#{user.id}</when>" +
            "<when test='user.nickname!=null and user.nickname!=\"\"'>and nickname=#{user.nickname}</when>" +
            "<when test='user.email!=null and user.email!=\"\"'>and email=#{user.email} </when>" +
            "<when test='user.phone!=null and user.phone!=\"\"'>and phone=#{user.phone} </when>" +
            "<when test='user.avatar_url!=null and user.avatar_url!=\"\"'>and avatar_url=#{user.avatar_url} </when>" +
            "</script>")
   List<User> findUserInfo(@Param("user") User user);

    //添加用户
    @Insert("insert into user (id,nickname,email,phone,avatar_url) values  " +
            "  (#{item.id},#{item.nickname},#{item.email},#{item.phone},#{item.avatar_url})")
    void saveUserInfo(@Param("item") User user);

    //修改用户信息
    @Update("<script>" +
            "update user" +
            "<set>" +
            "<if test='user.nickname!=null and user.nickname!=\"\"'>nickname=#{user.nickname},</if>" +
            "<if test='user.email!=null and user.email!=\"\"'>email=#{user.email},</if>" +
            "<if test='user.phone!=null and user.phone!=\"\"'>phone=#{user.phone},</if>" +
            "<if test='user.avatar_url!=null and user.avatar_url!=\"\"'>avatar_url=#{user.avatar_url},</if>" +
            "</set>" +
            "where id=#{user.id}" +
            "</script>")
    void updateUserInfo(@Param("user") User user);


    //删除用户
    @Delete("<script> delete from user " +
            "where id in" +
            "<foreach collection ='id' item ='items' separator=',' open='(' close=')'  > " +
            "#{items}" +
            "</foreach>" +
            "</script>")
    void deleteUserInfo(@Param("id") List<String> id);
}
