package com.happydriving.rockets.server.mapper;

import com.happydriving.rockets.server.entity.CarType;
import com.happydriving.rockets.server.entity.CarTypeExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CarTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    int countByExample(CarTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    int deleteByExample(CarTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    @Delete({
        "delete from car_type",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    @Insert({
        "insert into car_type (id, created_at, ",
        "updated_at, car_type)",
        "values (#{id,jdbcType=INTEGER}, #{createdAt,jdbcType=TIMESTAMP}, ",
        "#{updatedAt,jdbcType=TIMESTAMP}, #{carType,jdbcType=VARCHAR})"
    })
    int insert(CarType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    int insertSelective(CarType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    List<CarType> selectByExample(CarTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, created_at, updated_at, car_type",
        "from car_type",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    CarType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") CarType record, @Param("example") CarTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") CarType record, @Param("example") CarTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CarType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_type
     *
     * @mbggenerated
     */
    @Update({
        "update car_type",
        "set created_at = #{createdAt,jdbcType=TIMESTAMP},",
          "updated_at = #{updatedAt,jdbcType=TIMESTAMP},",
          "car_type = #{carType,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CarType record);
}