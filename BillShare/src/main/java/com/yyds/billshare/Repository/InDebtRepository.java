package com.yyds.billshare.Repository;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.ResponseModel.ResponseDebtForOneBill;
import com.yyds.billshare.Model.ResponseModel.ResponseDebtsByDebtor;
import com.yyds.billshare.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InDebtRepository extends JpaRepository<InDebt, User> {
    List<InDebt> findByAmount(int amount);
    List<ResponseDebtForOneBill> findByBill(Bill bill);

    @Query("SELECT d FROM InDebt d WHERE d.debtor.email = ?1 AND d.bill.bid = ?2")
    Optional<InDebt> findByDebtorEmailAndBillId(String email, Integer bid);

    @Query("SELECT d FROM InDebt d WHERE d.debtor.uid = ?1 AND d.bill.bid = ?2")
    Optional<InDebt> findByDebtorIdAndBillId(Integer did, Integer bid);
//    @Query("SELECT d.debtor.uid, d.amount, d.bill.bid, d.status, d.bill.finishTime, d.bill.owner.nickname" +
//            " FROM InDebt d WHERE d.debtor=?1 AND d.status<3 ORDER BY d.status")
    @Query(value = "SELECT new com.yyds.billshare.Model.ResponseModel.ResponseDebtsByDebtor(d.debtor.uid, d.amount, d.bill.bid, d.status, d.bill.finishTime, d.bill.owner.nickname)"+
            "FROM InDebt d WHERE d.debtor=?1 AND d.status<3 ORDER BY d.status")
    List<ResponseDebtsByDebtor> findResponseDebtsByDebtor(User debtor);


}