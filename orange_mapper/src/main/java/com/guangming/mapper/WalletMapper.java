package com.guangming.mapper;

import com.guangming.pojo.Wallet;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WalletMapper {

    //增
    @Insert("insert into wallet (user_id,pay_type,tm_nums,create_time,invalid_time) values  " +
            "  (#{wallet.user_id},#{wallet.pay_type},#{wallet.tm_nums},#{wallet.create_time},#{wallet.invalid_time})")
    void save(@Param("wallet") Wallet wallet);
    //删
    @Delete("delete from wallet " +
            "where user_id=#{id} ")
    void deleteById(@Param("id") String id);
    //改
    @Update("<script>" +
            "update wallet " +
            "<set>" +
            "<if test='wallet.pay_type!=null'>pay_type=#{wallet.pay_type},</if>" +
            "<if test='wallet.tm_nums!=null'>tm_nums=#{wallet.tm_nums},</if>" +
            "<if test='wallet.create_time!=null'>create_time=#{wallet.create_time},</if>" +
            "<if test='wallet.invalid_time!=null'>invalid_time=#{wallet.invalid_time},</if>" +
            "</set>" +
            "where user_id=#{wallet.user_id}" +
            "</script>")
    void update(@Param("wallet")Wallet wallet);
    //查
    @Select("<script>" +
            "select user_id,pay_type,tm_nums,create_time,invalid_time from wallet " +
            "where 1=1 " +
            "<when test='wallet.user_id!=null and wallet.user_id!=\"\"'>and user_id=#{wallet.user_id} </when>" +
            "<when test='wallet.pay_type!=null'>and pay_type=#{wallet.pay_type} </when>" +
            "<when test='wallet.tm_nums!=null'>and tm_nums=#{wallet.tm_nums} </when>" +
            "<when test='wallet.create_time!=null'>and create_time=#{wallet.create_time} </when>" +
            "<when test='wallet.invalid_time!=null '>and invalid_time=#{wallet.invalid_time} </when>" +
            "</script>")
    List<Wallet> query(@Param("wallet")Wallet wallet);
    //查
    @Select("select user_id,pay_type,tm_nums,create_time,invalid_time from wallet where user_id=#{id} ")
    Wallet queryById(@Param("id")String id);
}
