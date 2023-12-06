package com.cts.rxmodule.service;

import com.cts.rxmodule.exceptions.NoRecordFoundException;
import com.cts.rxmodule.models.ReviewOnDrugs;
import com.cts.rxmodule.models.Rx;
import com.cts.rxmodule.repository.ReviewOnDrugsRepo;
import com.cts.rxmodule.repository.RxRepo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service @Slf4j
public class RxService {
    @Autowired
    private RxRepo rxRepo;

    @Autowired
    private ReviewOnDrugsRepo reviewOnDrugsRepo;

    public Rx addRxDetails(Rx rxDetails) throws NoRecordFoundException {
        try {
//            rxRepo.findById(rxDetails.getRxId()).orElseThrow(new RecordFoundException("User data is not Found"));
            if(rxRepo.findById(rxDetails.getRxId()).isPresent()){
                throw new NoRecordFoundException(" Primary key violation, Record is found with id "+rxDetails.getRxId());
            }
            return rxRepo.save(rxDetails);
        }catch (Exception e){
            throw new NoRecordFoundException(e.getMessage());
        }
    }
    public List<Rx> searchById(String searchKeyword) throws NoRecordFoundException {
            if(rxRepo.searchByIdOrName(searchKeyword).isEmpty())
                throw new NoRecordFoundException("No Records found");
            return rxRepo.searchByIdOrName(searchKeyword);

    }
    public Rx getDispatchableRxStock(String rxId,String locationName) throws NoRecordFoundException {
        Optional<Rx> rxDetails=rxRepo.getDispatchableRxStock(rxId,locationName);
        if(rxDetails.isPresent()){
            return rxDetails.get();
        }
        throw new NoRecordFoundException("Stock is not Available");
        /*
        * Optional<Rx> rxDetails =rxRepo.findById(rxId);
        if(rxDetails.isPresent()){
            for(RxLocation rxLocation:rxDetails.get().getRxLocation()){
                if(rxLocation.getLocationName().equalsIgnoreCase(locationName)){
                    rxDetails.get().getRxLocation().clear();
                    rxDetails.get().getRxLocation().add(rxLocation);

                    return rxDetails.get();
                }
            }
        } throw new NoRecordFoundException("Record is not Found");
        * */

    }


    public Boolean isStockAvailable(Map<String,String> orderDetails) throws NoRecordFoundException {
            Optional<Rx> rxDetails=rxRepo.getDispatchableRxStock(orderDetails.get("rxId"),orderDetails.get("location"));
            if(rxDetails.isPresent()){
                return (rxDetails.get().getRxLocation().get(0).getUnitsInPackage() > 0); //checking is stock available or not
            }
            throw new NoRecordFoundException("Stock is not Available");

    }
    public List<Rx> getAllRxDetails() throws NoRecordFoundException {
        List<Rx> allRxDetails=rxRepo.findAll();
        if(!allRxDetails.isEmpty()){
            return allRxDetails;
        }else throw new NoRecordFoundException("No rxDetails found");
    }

    public ReviewOnDrugs writeReviewOnDrugs(ReviewOnDrugs reviewOnDrugs){
        return reviewOnDrugsRepo.save(reviewOnDrugs);
    }
    public List<ReviewOnDrugs> getAllReviews(){
        return reviewOnDrugsRepo.findAll();
    }
}
