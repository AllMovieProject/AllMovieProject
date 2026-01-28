package com.sist.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sist.web.vo.PaymentVO;

@Mapper
public interface PaymentMapper {

    @Insert("INSERT INTO payment(payment_id, userid, merchant_uid, amount, buyer_name, buyer_tel, buyer_email, payment_status) "
          + "VALUES(seq_payment_id.nextval, #{userid}, #{merchant_uid}, #{amount}, #{buyer_name}, #{buyer_tel}, #{buyer_email}, 'ready')")
    public void insertPayment(PaymentVO vo);

    @Update("UPDATE payment SET "
          + "imp_uid = #{imp_uid, jdbcType=VARCHAR}, "
          + "payment_method = #{payment_method, jdbcType=VARCHAR}, "
          + "payment_status = #{payment_status, jdbcType=VARCHAR}, "
          + "payment_date = SYSDATE "
          + "WHERE merchant_uid = #{merchant_uid}")
    public void updatePayment(PaymentVO vo);

    @Select("SELECT * FROM payment WHERE merchant_uid = #{merchant_uid}")
    public PaymentVO getPaymentByMerchantUid(String merchant_uid);
    
}