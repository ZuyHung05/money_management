package com.example.demo.controller;

import com.example.demo.service.RecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/analytic")
public class AnalyzeController {
    private final RecordService recordService;

    public AnalyzeController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/get_total_fee_by_month/{houseId}/{userId}")
    public ResponseEntity<?> getTotalFeeByMonth(@PathVariable("houseId") String houseId, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(recordService.getTotalFeeByMonth(houseId, userId));
    }

    @GetMapping("/get_total_paid_by_month/{houseId}/{userId}")
    public ResponseEntity<?> getTotalPaidByMonth(@PathVariable("houseId") String houseId, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(recordService.getTotalPaidByMonth(houseId, userId));
    }

    @GetMapping("/get_paid_for_by_month/{houseId}/{userId}")
    public ResponseEntity<?> getPaidForByMonth(@PathVariable("houseId") String houseId, @PathVariable("userId") Integer userId, @RequestParam(name = "date") @DateTimeFormat(pattern = "MM/yyyy") YearMonth date) {
        return ResponseEntity.ok(recordService.getPaidForByMonth(houseId, userId, date));
    }
    @GetMapping("/get_fee_from_by_month/{houseId}/{userId}")
    public ResponseEntity<?> getFeeFromByMonth(@PathVariable("houseId") String houseId, @PathVariable("userId") Integer userId, @RequestParam(name = "date") @DateTimeFormat(pattern = "MM/yyyy") YearMonth date) {
        return ResponseEntity.ok(recordService.getFeeFromByMonth(houseId, userId, date));
    }
    @GetMapping("/calculate/{houseId}")
    public ResponseEntity<?> calculate(@PathVariable("houseId") String houseId, @RequestParam(name = "date") @DateTimeFormat(pattern = "MM/yyyy") YearMonth date) {
        return ResponseEntity.ok(recordService.getSummaryByMonth(houseId, date));
    }

}
