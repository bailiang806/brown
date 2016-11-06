package com.happydriving.rockets.campaign.spread.mapper;

import com.happydriving.rockets.campaign.spread.entity.SpreadAdmin;
import com.happydriving.rockets.campaign.spread.entity.SpreadAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SpreadAdminMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    int countByExample(SpreadAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    int deleteByExample(SpreadAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    @Delete({
        "delete from spread_admin",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    @Insert({
        "insert into spread_admin (id, phone, ",
        "name, password_digest, ",
        "remember_token, created_at)",
        "values (#{id,jdbcType=INTEGER}, #{phone,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{passwordDigest,jdbcType=VARCHAR}, ",
        "#{rememberToken,jdbcType=VARCHAR}, #{createdAt,jdbcType=TIMESTAMP})"
    })
    int insert(SpreadAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    int insertSelective(SpreadAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    List<SpreadAdmin> selectByExample(SpreadAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, phone, name, password_digest, remember_token, created_at",
        "from spread_admin",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    SpreadAdmin selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SpreadAdmin record, @Param("example") SpreadAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SpreadAdmin record, @Param("example") SpreadAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SpreadAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spread_admin
     *
     * @mbggenerated
     */
    @Update({
        "update spread_admin",
        "set phone = #{phone,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "password_digest = #{passwordDigest,jdbcType=VARCHAR},",
          "remember_token = #{rememberToken,jdbcType=VARCHAR},",
          "created_at = #{createdAt,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SpreadAdmin record);
}