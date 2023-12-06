package com.cts.refillmodule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RefillOrderLineItem {  // queue orders for refill
    @Id
    private long refillOrderLineId;   // Set with orderId from orderModule
    private long quantityConsiderForRefill;  //no.of packages ( 4 units for single package)

    /*
    * we have to maintain list of Rx (drugs) and the quantity of each drug.
    * Long => it contains the Rx ID's
    * String => it contains quantity of the rx(drug)
    * */
//    private Map<Long,String> listOfRxs; // list of drugs assigned to the member (collect rx id's)
}
