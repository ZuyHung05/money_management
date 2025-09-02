package com.example.demo.service;

import com.example.demo.entity.PaymentGroup;
import com.example.demo.entity.UserGroup;
import com.example.demo.model.dto.PaymentGroupBody;
import com.example.demo.model.mapper.GroupMapper;
import com.example.demo.repository.HouseRepository;
import com.example.demo.repository.PaymentGroupRepository;
import com.example.demo.repository.UserGroupRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.serializable.UserGroupId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentGroupServiceImp implements PaymentGroupService {
    private final PaymentGroupRepository paymentGroupRepository;
    private final HouseRepository houseRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentGroupServiceImp(PaymentGroupRepository paymentGroupRepository, HouseRepository houseRepository, UserGroupRepository userGroupRepository, UserRepository userRepository) {
        this.paymentGroupRepository = paymentGroupRepository;
        this.houseRepository = houseRepository;
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
    }


    @Override
    public PaymentGroupBody getPaymentGroupById(Integer id) {
        return GroupMapper.toPaymentGroupBody(paymentGroupRepository.getPaymentGroupById(id));
    }

    @Override
    public List<PaymentGroupBody> getPaymentGroupByUserIdAndHouseId(Integer userId, String houseId) {
        List<PaymentGroupBody> paymentGroupBodyList = new ArrayList<>();
        for (PaymentGroup paymentGroup : paymentGroupRepository.getPaymentGroupByUserAndHouse(userId, houseId)) {
            paymentGroupBodyList.add(GroupMapper.toPaymentGroupBody(paymentGroup));
        }
        return paymentGroupBodyList;
    }

    @Override
    public void createGroup(PaymentGroupBody groupBody) {
        PaymentGroup paymentGroup = new PaymentGroup();
        paymentGroup.setId(groupBody.getId());
        paymentGroup.setName(groupBody.getName());
        paymentGroup.setHouse(houseRepository.getHouseById(groupBody.getHouseId()));
        paymentGroupRepository.save(paymentGroup);
        if(groupBody.getId()!=0) userGroupRepository.deleteAllByPaymentGroup_Id(groupBody.getId());
        for (Integer id : groupBody.getUserIds()) {
            UserGroup userGroup = new UserGroup();
            userGroup.setId(new UserGroupId(groupBody.getId(),id));
            userGroup.setPaymentGroup(paymentGroup);
            userGroup.setUser(userRepository.getUserById(id));
            userGroupRepository.save(userGroup);
        }
    }

    @Override
    public void removeGroupById(Integer id) {
        paymentGroupRepository.removeGroupById(id);
    }

    @Override
    public Boolean checkGroup(String houseId, String name) {
        return paymentGroupRepository.checkGroup(houseId, UriEncoder.decode(name) );
    }

    @Override
    public PaymentGroupBody getRemovedPaymentGroupById(Integer id) {
        return GroupMapper.toPaymentGroupBody(paymentGroupRepository.getRemovedPaymentGroupById(id));
    }


    @Override
    public List<PaymentGroupBody> getPaymentGroupByHouseId(String houseId) {
        List<PaymentGroupBody> paymentGroupBodyList = new ArrayList<>();
        for (PaymentGroup paymentGroup : paymentGroupRepository.getPaymentGroupsByHouse_Id(houseId)) {
            paymentGroupBodyList.add(GroupMapper.toPaymentGroupBody(paymentGroup));
        }
        return paymentGroupBodyList;
    }
}
