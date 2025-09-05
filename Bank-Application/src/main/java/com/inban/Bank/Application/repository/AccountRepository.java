package com.inban.Bank.Application.repository;

import com.inban.Bank.Application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

}
