package com.example.demo.controller;

import com.example.demo.model.dto.RecordBody;
import com.example.demo.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/records")
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{houseId}/{userId}")
    public ResponseEntity<?> getAllRecordsByUsersAndHouse(@PathVariable Integer userId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getAllRecordByUsersAndHouse(userId, houseId, year, month));
    }

    @GetMapping("/{houseId}/{userId}/{groupId}")
    public ResponseEntity<?> getRecordsByUsersAndGroup(@PathVariable Integer userId, @PathVariable Integer groupId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getRecordByUsersAndGroup(userId, groupId, houseId, year, month));
    }

    @GetMapping("/payer/{houseId}/{payerId}")
    public ResponseEntity<?> getAllRecordsByPayerAndHouse(@PathVariable Integer payerId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getAllRecordByPayerAndHouse(payerId, houseId, year, month));
    }

    @GetMapping("/payer/{houseId}/{payerId}/{groupId}")
    public ResponseEntity<?> getRecordsByPayerAndGroup(@PathVariable Integer payerId, @PathVariable Integer groupId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getRecordByPayerAndGroup(payerId, groupId, houseId, year, month));
    }

    @GetMapping("/payer/other/{houseId}/{payerId}")
    public ResponseEntity<?> getRecordsByPayerOther(@PathVariable Integer payerId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getRecordByPayerAndOther(payerId, houseId, year, month));
    }

    @GetMapping("/other/{houseId}/{userId}")
    public ResponseEntity<?> getRecordsByUserOther(@PathVariable Integer userId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getRecordByUsersAndOther(userId, houseId, year, month));
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<?> getRecordsByHouseForAllMember(@PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getRecordByHouseForAllMember(houseId, year, month));
    }

    @GetMapping("/payer/house/{houseId}/{payerId}")
    public ResponseEntity<?> getRecordsByPayerAndHouse(@PathVariable Integer payerId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok(recordService.getRecordByPayerAndHouse(payerId, houseId, year, month));
    }


    @PutMapping("/save/{id}")
    public String saveRecord(@RequestBody RecordBody recordBody, @PathVariable String id) {
        recordService.saveRecord(recordBody, id);
        return "done";
    }

    @GetMapping("/date/{houseId}")
    public ResponseEntity<?> findDateOfRecords(@PathVariable String houseId) {
        return ResponseEntity.ok(recordService.findDateOfRecords(houseId));
    }

    @GetMapping("/paid/{houseId}/{userId}")
    public Integer findPaidMoneyByDate(@PathVariable Integer userId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return recordService.findPaidMoneyByDate(userId, houseId, year, month);
    }

    @GetMapping("/debt/{houseId}/{userId}")
    public Integer findDebtMoneyByDate(@PathVariable Integer userId, @PathVariable String houseId, @RequestParam String year, @RequestParam String month) {
        return recordService.findDebtMoneyByDate(userId, houseId, year, month);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable("id") String id) {
        return ResponseEntity.ok(recordService.getRecordById(id));
    }

    @GetMapping("/getRemovedById/{id}")
    public ResponseEntity<?> getRemovedRecordById(@PathVariable("id") String id) {
        return ResponseEntity.ok(recordService.getRemovedRecordById(id));
    }

    @PutMapping("/remove/{id}")
    void removeRecordById(@PathVariable("id") String id) {
        recordService.removeRecordById(id);
    }
}
