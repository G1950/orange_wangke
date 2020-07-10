package com.guangming.mapper;

import com.guangming.pojo.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper {

    @Select("select id,`name`,price,discount,nums,`desc`,img_url,type,tm_nums,tm_months,status from product from product where status=1")
    List<Product> query();

    @Select("select id,`name`,price,discount,nums,`desc`,img_url,type,tm_nums,tm_months,status from product where id=#{id} and status=1")
    Product queryById(@Param("id") String id);

    @Select("<script>" +
            "select id,`name`,price,discount,nums,`desc`,img_url,type,tm_nums,tm_months,status from product " +
            "where 1=1 " +
            "<when test='product.id!=null and product.id!=\"\"'>and id=#{product.id} </when>" +
            "<when test='product.name!=null and product.name!=\"\"'>and name=#{product.name} </when>" +
            "<when test='product.price!=null'>and price=#{product.price} </when>" +
            "<when test='product.discount!=null'>and discount=#{product.discount} </when>" +
            "<when test='product.desc!=null and product.desc!=\"\"'>and desc=#{product.desc} </when>" +
            "<when test='product.status!=null'>and status=#{product.status} </when>" +
            "</script>")
    List<Product> queryByProduct(@Param("product") Product product);


    @Insert("<script> insert into product (id,`name`,price,discount,nums,`desc`,img_url,type,tm_nums,tm_months,status) values  " +
            "  <foreach collection='products' item='items' separator=',' > " +
            "  (#{items.id},#{items.name},#{items.price},#{items.discount},#{items.nums},#{items.desc},#{items.img_url},#{items.type},#{items.tm_nums},#{items.tm_months},#{items.status})\n" +
            "  </foreach> </script>")
    void save(@Param("products") List<Product> products);

    @Update("<script>" +
            "update product " +
            "<set>" +
            "<if test='product.name!=null and product.name!=\"\"'>`name`=#{product.name},</if>" +
            "<if test='product.price!=null'>price=#{product.price},</if>" +
            "<if test='product.discount!=null'>discount=#{product.discount},</if>" +
            "<if test='product.nums!=null'>nums=#{product.nums},</if>" +
            "<if test='product.desc!=null and product.desc!=\"\"'>`desc`=#{product.desc},</if>" +
            "<if test='product.img_url!=null and product.img_url!=\"\"'>img_url=#{product.img_url},</if>" +
            "<if test='product.type!=null'>type=#{product.type},</if>" +
            "<if test='product.tm_nums!=null'>tm_nums=#{product.tm_nums},</if>" +
            "<if test='product.tm_months!=null'>tm_months=#{product.tm_months},</if>" +
            "<if test='product.status!=null'> `status`=#{product.status},</if>" +
            "</set>" +
            "where id=#{product.id}" +
            "</script>")
    void update(@Param("product") Product product);

    @Update("<script>" +
            "update product set nums=nums-#{nums} " +
            "where id=#{id}" +
            "</script>")
    void updateById(@Param("id") String  id,@Param("nums") Integer nums);

    @Update("<script> update product " +
            "set status=0 " +
            "where id in" +
            "<foreach collection ='id' item ='items' separator=',' open='(' close=')'  > " +
            "#{items}" +
            "</foreach>" +
            "</script>")
    void deleteById(@Param("id") List<String> id);
}
