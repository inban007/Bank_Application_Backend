package com.inban.Bank.Application.service.impl;

import com.inban.Bank.Application.dto.AccountDto;
import com.inban.Bank.Application.entity.Account;
import com.inban.Bank.Application.mapper.AccountMapper;
import com.inban.Bank.Application.repository.AccountRepository;
import com.inban.Bank.Application.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountRepository
        .findById(id).orElseThrow(()->new RuntimeException("Account does not exist! Create new"));
        return  AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=accountRepository
                .findById(id).orElseThrow(()->new RuntimeException("Account does not exist! Create new"));
       double total=account.getBalance()+amount;
       account.setBalance(total);
       Account savedAccount=accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }
    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist! Create new"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance!");
        }

        double updatedBalance = account.getBalance() - amount;
        account.setBalance(updatedBalance);

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map((account) ->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account =accountRepository.findById(id).orElseThrow(()->new RuntimeException("No id ,cannot be deleted"));
        accountRepository.deleteById(id);
    }


}

