package com.cts.rxmodule.repository;

import com.cts.rxmodule.models.Rx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RxRepo extends JpaRepository<Rx,String> {
    //List<Rx> findByRxIdOrRxName(String keyword,String keyword1);

//    @Query("from Rx m where m.rxId=:keyword OR m.rxName=:keyword")
//    SELECT * FROM tableName WHERE CompanyName LIKE 'Em%' or CompanyName LIKE '% Em%'
    @Query("FROM Rx m WHERE m.rxId LIKE :keyword% or m.rxName LIKE %:keyword%")
    List<Rx> searchByIdOrName(String keyword);

    @Query("SELECT r FROM Rx r JOIN FETCH r.rxLocation l WHERE r.rxId = :rxId AND l.locationName = :locationName")
    Optional<Rx> getDispatchableRxStock(String rxId, String locationName);

//    @Query("FROM Rx r JOIN r.rxLocation l WHERE r.rxId=:rxId AND l.locationName=:location")
//    Rx getDispatchableStock(String rxId,String location);
//
//    Rx findByRxIdAndRxLocationLocationName(String rxId, String locationName);
}
