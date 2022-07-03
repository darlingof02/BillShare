package com.yyds.billshare.Repository.ClearRepository;

import com.yyds.billshare.Model.ClearModels.ClearInfo;
import com.yyds.billshare.Model.ClearModels.ClearItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClearItemsRepository extends JpaRepository<ClearItems, ClearInfo> {

}