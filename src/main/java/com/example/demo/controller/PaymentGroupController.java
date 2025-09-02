package com.example.demo.controller;

import com.example.demo.model.dto.PaymentGroupBody;
import com.example.demo.service.PaymentGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class PaymentGroupController {
    private final PaymentGroupService paymentGroupService;

    public PaymentGroupController(PaymentGroupService paymentGroupService) {
        this.paymentGroupService = paymentGroupService;
    }

    @PutMapping("/create")
    public String createGroup(@RequestBody PaymentGroupBody paymentGroupBody) {
        paymentGroupService.createGroup(paymentGroupBody);
        return "done";
    }

    @GetMapping("/check/{houseId}")
    public Boolean checkGroup(@PathVariable("houseId") String houseId, @RequestParam(name = "name") String name) {
        return paymentGroupService.checkGroup(houseId, name);
    }

    @GetMapping("/{houseId}/{userId}")
    public ResponseEntity<?> getGroupByUserAndHouse(@PathVariable("houseId") String houseId, @PathVariable Integer userId) {
        return ResponseEntity.ok(paymentGroupService.getPaymentGroupByUserIdAndHouseId(userId, houseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentGroupService.getPaymentGroupById(id));
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<?> getGroupByHouse(@PathVariable String houseId) {
        return ResponseEntity.ok(paymentGroupService.getPaymentGroupByHouseId(houseId));
    }

    @PutMapping("/remove/{id}")
    public void removeGroupById(@PathVariable Integer id) {
        paymentGroupService.removeGroupById(id);
    }

    @GetMapping("/getRemovedById/{id}")
    public ResponseEntity<?> getRemovedPaymentGroupById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentGroupService.getRemovedPaymentGroupById(id));
    }
}
