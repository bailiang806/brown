package com.happydriving.rockets.server.mapper;

import com.happydriving.rockets.server.entity.UserLocation;
import com.happydriving.rockets.server.entity.UserLocationExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserLocationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int countByExample(UserLocationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int deleteByExample(UserLocationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    @Delete({
            "delete from user_location",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    @Insert({
            "insert into user_location (id, open_id, ",
            "latitude, longitude, ",
            "created_at)",
            "values (#{id,jdbcType=INTEGER}, #{openId,jdbcType=VARCHAR}, ",
            "#{latitude,jdbcType=DECIMAL}, #{longitude,jdbcType=DECIMAL}, ",
            "#{createdAt,jdbcType=TIMESTAMP})"
    })
    int insert(UserLocation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int insertSelective(UserLocation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    List<UserLocation> selectByExample(UserLocationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    @Select({
            "select",
            "id, open_id, latitude, longitude, created_at",
            "from user_location",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    UserLocation selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UserLocation record, @Param("example") UserLocationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UserLocation record, @Param("example") UserLocationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserLocation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    @Update({
            "update user_location",
            "set open_id = #{openId,jdbcType=VARCHAR},",
            "latitude = #{latitude,jdbcType=DECIMAL},",
            "longitude = #{longitude,jdbcType=DECIMAL},",
            "created_at = #{createdAt,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserLocation record);

    UserLocation selectLastLocation(@Param("open_id") String openId);

    UserLocation getUserLocationByOpenId(@Param("open_id") String openId);

    int updateUserLocationByOpenId(UserLocation record);
}