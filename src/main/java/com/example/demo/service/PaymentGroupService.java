package com.example.demo.service;

import com.example.demo.model.dto.PaymentGroupBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentGroupService {
    PaymentGroupBody getPaymentGroupById(Integer id);

    List<PaymentGroupBody> getPaymentGroupByUserIdAndHouseId(Integer userId, String houseId);

    void createGroup(PaymentGroupBody groupBody);
    void removeGroupById(Integer id);
    Boolean checkGroup(String houseId,  String name);
    PaymentGroupBody getRemovedPaymentGroupById( Integer id);

    List<PaymentGroupBody> getPaymentGroupByHouseId(String houseId);
}
