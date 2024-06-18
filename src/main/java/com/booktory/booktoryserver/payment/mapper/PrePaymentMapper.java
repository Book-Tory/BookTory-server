package com.booktory.booktoryserver.payment.mapper;

import com.booktory.booktoryserver.payment.dto.request.PaymentRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface PrePaymentMapper {

    @Insert("INSERT INTO pre_payment (merchant_uid, amount, created_at) VALUES (#{merchant_uid}, #{amount}, NOW())")
    void insertPrePayment(PaymentRequest prePayment);

    @Select("SELECT * FROM pre_payment WHERE merchant_uid = #{merchant_uid}")
    Optional<PaymentRequest> findById(String merchant_uid);
}
