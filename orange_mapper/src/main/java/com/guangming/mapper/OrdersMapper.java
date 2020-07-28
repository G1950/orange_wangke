package com.guangming.mapper;

import com.guangming.pojo.Orders;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrdersMapper {
    //增
    @Insert("insert into orders (id,user_id,product_id,nums,price,discount,real_price,create_time) values  " +
            "  (#{orders.id},#{orders.user_id},#{orders.product_id},#{orders.nums},#{orders.price},#{orders.discount},#{orders.real_price},#{orders.create_time})")
    void save(@Param("orders") Orders orders);

    //删
    @Delete("delete from orders " +
            "where id=#{id} ")
    void deleteById(@Param("id") String id);

    //改
    @Update("<script>" +
            "update orders " +
            "<set>" +
            "<if test='orders.user_id!=null and orders.user_id!=\"\"'>user_id=#{orders.user_id},</if>" +
            "<if test='orders.product_id!=null and orders.product_id!=\"\"'>product_id=#{orders.product_id},</if>" +
            "<if test='orders.alipay_id!=null and orders.alipay_id!=\"\"'>alipay_id=#{orders.alipay_id},</if>" +
            "<if test='orders.nums!=null'>nums=#{orders.nums},</if>" +
            "<if test='orders.price!=null'>price=#{orders.price},</if>" +
            "<if test='orders.discount!=null'>discount=#{orders.discount},</if>" +
            "<if test='orders.real_price!=null'>real_price=#{orders.real_price},</if>" +
            "<if test='orders.create_time!=null'>create_time=#{orders.create_time},</if>" +
            "<if test='orders.status!=null'>status=#{orders.status},</if>" +
            "</set>" +
            "where id=#{orders.id}" +
            "</script>")
    void update(@Param("orders") Orders orders);

    //查
    @Select("<script>" +
            "select id,user_id,product_id,alipay_id,nums,price,discount,real_price,create_time,last_time,status from orders " +
            "where 1=1 " +
            "<when test='orders.id!=null and orders.id!=\"\"'> and id=#{orders.id}</when>" +
            "<when test='orders.user_id!=null and orders.user_id!=\"\"'> and user_id=#{orders.user_id}</when>" +
            "<when test='orders.product_id!=null and orders.product_id!=\"\"'> and product_id=#{orders.product_id}</when>" +
            "<when test='orders.alipay_id!=null and orders.alipay_id!=\"\"'> and alipay_id=#{orders.alipay_id}</when>" +
            "<when test='orders.nums!=null'> and nums=#{orders.nums}</when>" +
            "<when test='orders.price!=null'> and price=#{orders.price}</when>" +
            "<when test='orders.discount!=null'> and discount=#{orders.discount}</when>" +
            "<when test='orders.real_price!=null'> and real_price=#{orders.real_price}</when>" +
            "<when test='orders.create_time!=null'> and create_time=#{orders.create_time}</when>" +
            "<when test='orders.last_time!=null'> and last_time=#{orders.last_time}</when>" +
            "<when test='orders.status!=null'> and status=#{orders.status}</when>" +
            "</script>")
    List<Orders> query(@Param("orders") Orders orders);

    @Select("select id,user_id,product_id,alipay_id,nums,price,discount,real_price,create_time,last_time,status from orders where id=#{id}")
    Orders queryByOrderId(@Param("id") String id);

    @Select("select id,user_id,product_id,alipay_id,nums,price,discount,real_price,create_time,last_time,status from orders where user_id=#{id}")
    List<Orders> queryByUserId(@Param("id") String id);

    @Update("update orders " +
            "set status=-1 where id=#{id}")
    void cancel(@Param("id") String id);

}
