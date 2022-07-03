package com.yyds.billshare.Repository.ClearRepository;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.ClearModels.ClearInfo;
import com.yyds.billshare.Model.ResponseModel.ResponseOneBill;
import com.yyds.billshare.Model.ResponseModel.ResponseOwnedBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClearInfoRepository extends JpaRepository<ClearInfo, Integer> {

}