package com.yyds.billshare.Repository;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.ResponseModel.ResponseOwnedBill;
import com.yyds.billshare.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    List<Bill> findByAmount(int amount);


    @Query("SELECT b FROM Bill as b WHERE b.owner.uid =?1")
    List<ResponseOwnedBill> findByOwnerId(Integer oid);

}