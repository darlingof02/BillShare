package com.yyds.billshare.Repository;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.ResponseModel.ResponseOneBill;
import com.yyds.billshare.Model.ResponseModel.ResponseOwnedBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query("SELECT b FROM Bill as b WHERE b.owner.uid =?1")
    List<ResponseOwnedBill> findByOwnerId(Integer oid);

    @Query("SELECT b FROM Bill as b WHERE b.owner.email =?1 AND b.bid = ?2")
    Optional<Bill> findByOwnerEmailAndBid(String ownerEmail,Integer bid);

    @Query("SELECT b FROM Bill as b WHERE b.bid =?1")
    List<ResponseOneBill> findByBid(Integer bid);
}