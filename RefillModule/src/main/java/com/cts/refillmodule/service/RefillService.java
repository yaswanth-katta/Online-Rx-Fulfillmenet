package com.cts.refillmodule.service;

import com.cts.refillmodule.exception.RefillException;
import com.cts.refillmodule.feign.OrderModuleClient;
import com.cts.refillmodule.models.RefillOrderDetails;
import com.cts.refillmodule.repository.RefillOrderRepo;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service @Slf4j @AllArgsConstructor
public class RefillService {
    @Autowired
    private OrderModuleClient orderModuleClient;
    @Autowired
    private RefillOrderRepo refillOrderRepo;
    public RefillOrderDetails requestAdhocRefill(ObjectNode objectNode) throws RefillException {
        Map<String,String> refillDetails=new HashMap<>();
        refillDetails.put("orderId",objectNode.get("orderId").asText());
        refillDetails.put("location",objectNode.get("location").asText());

        RefillOrderDetails refillOrder=refillOrderRepo.findByOrderId(objectNode.get("orderId").asLong());
        if(orderModuleClient.requestingRefill(refillDetails)){  // integrating with order module

            refillOrder.setRefillDate(String.valueOf(LocalDate.now()));
            refillOrder.setNextRefillDate(
                    calculateNextRefillDate(String.valueOf(LocalDate.now()),refillOrder.getNumberOfRefills(),refillOrder.getRefillOccurrence())
            );
            refillOrder.setRefillStatus("In Progress");
            refillOrderRepo.save(refillOrder);

            Timer t = new java.util.Timer();
            t.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            refillOrder.setRefillStatus("Refilled");
                            refillOrderRepo.save(refillOrder);
                            t.cancel();
                        }
                    },
                    5000
            );

            return refillOrder;
        }else throw new RefillException("Refill Request Failed");
    }


    public RefillOrderDetails viewRefillStatus(long orderId) throws RefillException {
        Optional<RefillOrderDetails> refillOrder=refillOrderRepo.findById(orderId);
        if(refillOrder.isPresent()){
            return refillOrder.get();
        }else throw new RefillException("No Refill orders found with id "+orderId);
    }

    public List<RefillOrderDetails> getRefillDuesOfDate() throws RefillException {
        if(!refillOrderRepo.getDueRefillOrders().isEmpty()){
            return refillOrderRepo.getDueRefillOrders();
        }
        throw new RefillException("There is No Due Refill Available");
    }

    public String calculateNextRefillDate(String orderDate, int numberOfRefillsRemaining,String refillOccurrence) {
        if(numberOfRefillsRemaining>0){
            LocalDate date = LocalDate.parse(orderDate);
            if(refillOccurrence.equalsIgnoreCase("weekly")){
                return String.valueOf(date.plusWeeks(1)); // adding one week to the orderDate
            }else{
                return String.valueOf(date.plusMonths(1));  //adding 1 month to orderDate
            }
        }
        return "No Refills";
    }




}
