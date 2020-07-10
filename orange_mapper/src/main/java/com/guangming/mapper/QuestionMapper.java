package com.guangming.mapper;

import com.guangming.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface QuestionMapper {

    //添加题目
    @Insert("<script> insert into question (problem,xx,answer) values  " +
            "  <foreach collection='question' item='items' separator=',' > " +
            "  (#{items.problem},#{items.xx},#{items.answer})\n" +
            "  </foreach> </script>")
    void save(@Param("question") List<Question> question);

    //修改题目
    @Update("<script>" +
            "update question " +
            "<set>" +
            "<if test='question.problem!=null and question.problem!=\"\"'>problem=#{question.problem},</if>" +
            "<if test='question.xx!=null and question.xx!=\"\"'>xx=#{question.xx},</if>" +
            "<if test='question.answer!=null and question.answer!=\"\"'>answer=#{question.answer},</if>" +
            "</set>" +
            "where id=#{product.id}" +
            "</script>")
    void update(@Param("question")  Question question);

    //查询题目-模糊查询
    @Select("SELECT id,problem,xx,answer from question WHERE MATCH(problem,answer,xx) AGAINST(#{query} IN BOOLEAN MODE)")
    List<Question> query(@Param("query") String query);


    //查询题目-Id查询
    @Select("select id,problem,xx,answer from question where id=#{id}")
    Question queryById(@Param("id") Integer id);

    //删除题目
    @Delete("<script> delete from question " +
            "where id in" +
            "<foreach collection ='id' item ='items' separator=',' open='(' close=')'  > " +
            "#{items}" +
            "</foreach>" +
            "</script>")
    void deleteById(@Param("id") List<Integer> id);


}
