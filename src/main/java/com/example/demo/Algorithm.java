package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algorithm {

    public static List<Map<String, Object>> calculateMoneyTransfer(Map<Integer, Double> needToPay, Map<Integer, Double> alreadyPaid, Map<Integer, String> users) {
        List<Map<String, Object>> transfers = new ArrayList<>();
        int maxDifferenceUser = -1;
        double maxDifference = Double.MIN_VALUE;
        for (int userId : needToPay.keySet()) {
            double difference = alreadyPaid.get(userId) - needToPay.get(userId);
            if (difference > maxDifference) {
                maxDifference = difference;
                maxDifferenceUser = userId;
            }
        }


        for (int userId : needToPay.keySet()) {
            if (userId != maxDifferenceUser) {
                double difference = alreadyPaid.get(userId) - needToPay.get(userId);
                if (difference < 0) {
                    double transferAmount = Math.abs(difference);
                    Map<String, Object> transfer = new HashMap<>();
                    transfer.put("from", users.get(userId));
                    transfer.put("to", users.get(maxDifferenceUser));
                    transfer.put("transfer", transferAmount);
                    transfers.add(transfer);
                }
            }
        }
        for (int userId : needToPay.keySet()) {
            if (userId != maxDifferenceUser) {
                double difference = alreadyPaid.get(userId) - needToPay.get(userId);
                if (difference > 0) {
                    double transferAmount = Math.min(maxDifference, difference);
                    Map<String, Object> transfer = new HashMap<>();
                    transfer.put("from", users.get(maxDifferenceUser));
                    transfer.put("to", users.get(userId));
                    transfer.put("transfer", transferAmount);
                    transfers.add(transfer);
                    maxDifference -= transferAmount;
                }
            }
        }
        return transfers;
    }
}
