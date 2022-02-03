package com.yyds.billshare.Repository;

import com.yyds.billshare.Model.Debtor;
import com.yyds.billshare.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtorRepository extends JpaRepository<Debtor, User> {
}