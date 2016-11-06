package com.happydriving.rockets.server.mapper;

import com.happydriving.rockets.server.entity.PaymentInfo;
import com.happydriving.rockets.server.entity.PaymentInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PaymentInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    int countByExample(PaymentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    int deleteByExample(PaymentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    @Delete({
        "delete from payment_info",
        "where order_id = #{orderId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    @Insert({
        "insert into payment_info (order_id, phone, ",
        "school_id, price, ",
        "LAST_UPDATED_TX_STAMP, LAST_UPDATED_STAMP, ",
        "CREATED_STAMP, CREATED_TX_STAMP, ",
        "name, trade_state, ",
        "out_trade_no, third_party_trade_no)",
        "values (#{orderId,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
        "#{schoolId,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, ",
        "#{lastUpdatedTxStamp,jdbcType=TIMESTAMP}, #{lastUpdatedStamp,jdbcType=TIMESTAMP}, ",
        "#{createdStamp,jdbcType=TIMESTAMP}, #{createdTxStamp,jdbcType=TIMESTAMP}, ",
        "#{name,jdbcType=VARCHAR}, #{tradeState,jdbcType=VARCHAR}, ",
        "#{outTradeNo,jdbcType=VARCHAR}, #{thirdPartyTradeNo,jdbcType=VARCHAR})"
    })
    int insert(PaymentInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    int insertSelective(PaymentInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    List<PaymentInfo> selectByExample(PaymentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "order_id, phone, school_id, price, LAST_UPDATED_TX_STAMP, LAST_UPDATED_STAMP, ",
        "CREATED_STAMP, CREATED_TX_STAMP, name, trade_state, out_trade_no, third_party_trade_no",
        "from payment_info",
        "where order_id = #{orderId,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    PaymentInfo selectByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PaymentInfo record, @Param("example") PaymentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PaymentInfo record, @Param("example") PaymentInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PaymentInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_info
     *
     * @mbggenerated
     */
    @Update({
        "update payment_info",
        "set phone = #{phone,jdbcType=VARCHAR},",
          "school_id = #{schoolId,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DECIMAL},",
          "LAST_UPDATED_TX_STAMP = #{lastUpdatedTxStamp,jdbcType=TIMESTAMP},",
          "LAST_UPDATED_STAMP = #{lastUpdatedStamp,jdbcType=TIMESTAMP},",
          "CREATED_STAMP = #{createdStamp,jdbcType=TIMESTAMP},",
          "CREATED_TX_STAMP = #{createdTxStamp,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "trade_state = #{tradeState,jdbcType=VARCHAR},",
          "out_trade_no = #{outTradeNo,jdbcType=VARCHAR},",
          "third_party_trade_no = #{thirdPartyTradeNo,jdbcType=VARCHAR}",
        "where order_id = #{orderId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(PaymentInfo record);
//
//    int updateTradeStateAndThirdPartyNo(@Param("tradeState")String tradeState,@Param("thirdPartyTradeNo")String thirdPartyTradeNo,@Param("outTradeNo")String outTradeNo);
//    @Update({
//            "update payment_info",
//            "set trade_state=#{tradeState,jdbcType=VARCHAR},",
//            "third_party_tradeNo=#{thirdPartyTradeNo,jdbcType=VARCHAR},",
//            "where out_trade_state=#{outTradeState,jdbcType=VARCHAR}"
//    })
    void updateTradeStateAndThirdPartyNo(PaymentInfo record);

    int updatePaymentByOutTradeNo(@Param("outTradno")String outTradno,@Param("thirdnum")String thirdnum);
}