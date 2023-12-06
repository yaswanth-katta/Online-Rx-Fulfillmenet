package com.cts.ordermodule.service;

import com.cts.ordermodule.exception.OrderException;
import com.cts.ordermodule.exception.StockUnAvailableException;
import com.cts.ordermodule.feignclient.RefillClient;
import com.cts.ordermodule.feignclient.RxModuleClient;
import com.cts.ordermodule.models.PrescriptionOrder;
import com.cts.ordermodule.models.RefillOrderDetails;
import com.cts.ordermodule.repository.OrderRepo;
import com.cts.ordermodule.repository.RefillRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service @Slf4j
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private RxModuleClient rxModuleClient;
    @Autowired
    private RefillClient refillClient;

    @Autowired
    private RefillRepo refillRepo;

    @Transactional
    public PrescriptionOrder orderDrugs(PrescriptionOrder prescriptionOrder) throws StockUnAvailableException {
        Map<String,String> orderDetails=new HashMap<>();
        orderDetails.put("rxId",prescriptionOrder.getPrescriptionDetails().getRxId());
        orderDetails.put("location",prescriptionOrder.getLocation());

        if(rxModuleClient.isStockAvailable(orderDetails)
        ){
            prescriptionOrder.setOrderStatus("In Progress");
            prescriptionOrder.setOrderDate(String.valueOf(LocalDate.now()));
            Timer t = new java.util.Timer();
            t.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            prescriptionOrder.setOrderStatus("Order Placed");
                            orderRepo.save(prescriptionOrder);
                            t.cancel();
                        }
                    },
                    5000
            );
            prescriptionOrder.setNextRefillDate(  // set next refill date
                    calculateNextRefillDate(prescriptionOrder.getOrderDate(),prescriptionOrder.getPrescriptionDetails().getNumberOfRefills(),
                            prescriptionOrder.getRefillOccurrence())
            );
            //set next refill date int refill microservice
            PrescriptionOrder order=orderRepo.save(prescriptionOrder);
            log.info("Refill Occurrence ==== "+order.getRefillOccurrence());
            RefillOrderDetails refillOrder=new RefillOrderDetails(order.getOrderId(),"none","none",order.getNextRefillDate(),order.getRefillOccurrence(),order.getPrescriptionDetails().getNumberOfRefills());
            refillRepo.save(refillOrder);

            return order;
            // need to add the refill details by interacting with refill microservice

        }
        else throw new StockUnAvailableException("Drugs are not available");
    }

    @Transactional
    public String cancelOrder(String memberId,long orderId) throws OrderException {

        if(orderRepo.findById(orderId).isPresent()){
             orderRepo.deleteById(orderId);
             refillRepo.deleteByOrderId(orderId);  // removing order from refill data base
            return "Order is cancelled successfully";
        }else throw new OrderException("There is no Order Present with OrderID : "+orderId);


    }

    @Transactional
    public Boolean requestingRefill(Map<String,String> refillDetails) throws OrderException {
       Optional<PrescriptionOrder> order= orderRepo.findById(Long.valueOf(refillDetails.get("orderId")));
        if(order.isPresent()){
            Map<String,String> orderDetails=new HashMap<>();
            orderDetails.put("rxId",order.get().getPrescriptionDetails().getRxId());
            orderDetails.put("location",refillDetails.get("location"));

            if(rxModuleClient.isStockAvailable(orderDetails)){   // check stock is available or not
                   if( order.get().getPrescriptionDetails().getNumberOfRefills()>0 )  // checking refill limit)
                   {
                       order.get().getPrescriptionDetails().setNumberOfRefills(
                               order.get().getPrescriptionDetails().getNumberOfRefills()-1
                       ); //decreasing refill count

                       //set next refill
                        order.get().setNextRefillDate(
                                calculateNextRefillDate(String.valueOf(LocalDate.now()),order.get().getPrescriptionDetails().getNumberOfRefills(),
                                    order.get().getRefillOccurrence())
                        );

                        //RefillOrderDetails refillOrder=new RefillOrderDetails(order.get().getOrderId(),"none","none",order.get().getNextRefillDate());
                       orderRepo.save(order.get()); // updating with new refill count
//                       RefillOrderDetails refillOrder=refillRepo.findByOrderId(order.get().getOrderId());
//                       refillOrder.setNextRefillDate(calculateNextRefillDate(String.valueOf(LocalDate.now()),order.get().getPrescriptionDetails().getNumberOfRefills(),
//                               order.get().getRefillOccurrence()));  //updating next refill Date
//                       log.info("before saving === "+refillOrder.getNextRefillDate());
//                       refillRepo.save(refillOrder);
//                       log.info("After saving ==== "+refillRepo.findByOrderId(order.get().getOrderId()).getNextRefillDate());

                       return true;
                   }else throw new OrderException("There is no Refills for this Order");

            }else throw new OrderException("Request has failed Due to Stock is not available");
        }else throw new OrderException("The request is failed to Refill with order ID: "+refillDetails.get("orderId"));

    }

    public List<PrescriptionOrder> getAllOrders() throws OrderException {
        List<PrescriptionOrder> allOrders=orderRepo.findAll();
        if(!allOrders.isEmpty())
        {
            return allOrders;
        }else throw new OrderException("No Orders found ");

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

    public List<PrescriptionOrder> searchOrderByIdOrLocation(String searchKey){
        return orderRepo.searchOrderByIdOrName(searchKey);
    }
}
