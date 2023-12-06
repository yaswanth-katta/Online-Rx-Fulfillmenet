package com.cts.rxmodule.controller;

import com.cts.rxmodule.exceptions.NoRecordFoundException;
import com.cts.rxmodule.models.ReviewOnDrugs;
import com.cts.rxmodule.models.Rx;
import com.cts.rxmodule.service.RxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="http://localhost:4200",allowedHeaders ="*")
public class RxController {
    @Autowired
    private RxService rxService;

    @PostMapping(value = "/addRxDetails")
    public Rx addRxDetails(@RequestBody Rx rxDetails) throws Exception {
        return rxService.addRxDetails(rxDetails);
    }

    @GetMapping(value = "/searchRxById/{searchKeyword}")  //search rx details by its name or id
    public List<Rx> searchById(@PathVariable String searchKeyword) throws NoRecordFoundException {
        return rxService.searchById(searchKeyword);
    }

    @GetMapping(value = "/getDispatchableRxStock/{rxId}/{locationName}")
    public Rx getDispatchableRxStock(@PathVariable String rxId,@PathVariable String locationName) throws NoRecordFoundException {
        return rxService.getDispatchableRxStock(rxId,locationName);
//        if(rxDetails!=null){
//            return rxDetails;
//        }else throw new NoRecordFoundException("There is no Records Found");

    }

    @PostMapping(value = "/isDrugsAvailable")
    public Boolean isStockAvailable(@RequestBody Map<String,String> orderDetails) throws NoRecordFoundException {
        return rxService.isStockAvailable(orderDetails);
    }

    @GetMapping(value = "/getAllRxDetails")
    public List<Rx> getAllOrders() throws NoRecordFoundException {
        return rxService.getAllRxDetails();
    }

    @PostMapping(value = "/reviewOnDrugs")
    public ReviewOnDrugs writeReviewOnDrugs(@RequestBody ReviewOnDrugs reviewOnDrugs){
        return rxService.writeReviewOnDrugs(reviewOnDrugs);
    }
    @GetMapping(value = "/getAllReviews")
    public List<ReviewOnDrugs> getAllReviews(){
        return rxService.getAllReviews();
    }
}
