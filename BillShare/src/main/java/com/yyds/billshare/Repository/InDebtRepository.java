package com.yyds.billshare.Repository;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.ResponseModel.ResponseDebtForOneBill;
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

    List<InDebt> findByDebtor(User debtor);
    List<InDebt> findByDebtorAndStatus(User debtor,Integer status);

    @Query("SELECT d FROM InDebt d WHERE d.debtor.email = ?1 AND d.bill.bid = ?2")
    Optional<InDebt> findByDebtorEmailAndBillId(String email, Integer bid);

    @Query("SELECT d FROM InDebt d WHERE d.debtor.id = ?1 AND d.bill.bid = ?2")
    Optional<InDebt> findByDebtorIdAndBillId(Integer did, Integer bid);

    // 好像没用
    @Query("SELECT bill FROM InDebt WHERE debtor =?1")
    List<Bill> findHistoryBillsByDebtor(User debtor);

    @Query("SELECT bill FROM InDebt WHERE debtor =?1 and status=?2")
    List<Bill> findBillByDebtorAndStatus(User debtor, Integer status);


}