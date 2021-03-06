package com.happydriving.rockets.server.mapper;

import com.happydriving.rockets.server.entity.StudentInfo;
import com.happydriving.rockets.server.entity.StudentInfoExample;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.happydriving.rockets.server.model.CoachDetailInfo;
import com.happydriving.rockets.server.model.CoachScheduleDetailInfo;
import com.happydriving.rockets.server.model.StudentQueryInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface StudentInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    int countByExample(StudentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    int deleteByExample(StudentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    @Delete({
        "delete from student",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    @Insert({
        "insert into student (id, name, ",
        "urgent_name, urgent_phone, ",
        "address, ",
        "banji_id, ",
        "chexing_id, created_at, ",
        "updated_at, ",
        "shenhe, t1, t2, ",
        "t3, t4, photo, ",
        "shenfenzheng, product_id, user_id)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{urgentName,jdbcType=VARCHAR}, #{urgentPhone,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{banjiId,jdbcType=INTEGER}, ",
        "#{chexingId,jdbcType=INTEGER}, #{createdAt,jdbcType=TIMESTAMP}, ",
        "#{updatedAt,jdbcType=TIMESTAMP},",
        "#{shenhe,jdbcType=VARCHAR}, #{t1,jdbcType=VARCHAR}, #{t2,jdbcType=VARCHAR}, ",
        "#{t3,jdbcType=VARCHAR}, #{t4,jdbcType=VARCHAR}, #{photo,jdbcType=VARCHAR}, ",
        "#{shenfenzheng,jdbcType=VARCHAR}, #{productId,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER})"
    })
    int insert(StudentInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    int insertSelective(StudentInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    List<StudentInfo> selectByExample(StudentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, name, urgent_name, urgent_phone, address, ",
        "banji_id, chexing_id, created_at, updated_at, phone, shenhe, t1, t2, t3, t4, ",
        "photo, shenfenzheng, product_id, personalitytest, user_id",
        "from student",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    StudentInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") StudentInfo record, @Param("example") StudentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") StudentInfo record, @Param("example") StudentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StudentInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated
     */
    @Update({
        "update student",
        "set name = #{name,jdbcType=VARCHAR},",
          "urgent_name = #{urgentName,jdbcType=VARCHAR},",
          "urgent_phone = #{urgentPhone,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "banji_id = #{banjiId,jdbcType=INTEGER},",
          "chexing_id = #{chexingId,jdbcType=INTEGER},",
          "created_at = #{createdAt,jdbcType=TIMESTAMP},",
          "updated_at = #{updatedAt,jdbcType=TIMESTAMP},",
          "shenhe = #{shenhe,jdbcType=VARCHAR},",
          "t1 = #{t1,jdbcType=VARCHAR},",
          "t2 = #{t2,jdbcType=VARCHAR},",
          "t3 = #{t3,jdbcType=VARCHAR},",
          "t4 = #{t4,jdbcType=VARCHAR},",
          "photo = #{photo,jdbcType=VARCHAR},",
          "shenfenzheng = #{shenfenzheng,jdbcType=VARCHAR},",
          "product_id = #{productId,jdbcType=INTEGER},",
          "user_id=#{userId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(StudentInfo record);

    List<CoachScheduleDetailInfo> getCoachScheduleByStudentId(@Param("studentId") int studentId);

    List<Map> getCoachByStudentId(@Param("studentId") int studentId);

    List<StudentQueryInfo> getStudentInfoListByCoachId(@Param("coachId") int coachId);

    void  updatePersonalTest(@Param("pics") String pics, @Param("userId") String studentId);
}