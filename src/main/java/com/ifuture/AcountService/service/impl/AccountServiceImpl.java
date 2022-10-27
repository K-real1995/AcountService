package com.ifuture.AcountService.service.impl;

import com.ifuture.AcountService.exception.EntityNotFoundException;
import com.ifuture.AcountService.mapper.AccountMapper;
import com.ifuture.AcountService.model.Account;
import com.ifuture.AcountService.repository.AccountRepository;
import com.ifuture.AcountService.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * class AccountServiceImpl for create connections between AccountRepository and AccountController.
 * Implementation of {@link AccountService} interface.
 * Wrapper for {@link AccountRepository} and plus business logic.
 *
 * @author Kirill Soklakov
 * @since 2022-10-26
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public synchronized Long getAmount(Integer id){
        Account result = accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Аккаунт с Id: {} не найден", id);
                    throw new EntityNotFoundException("Account", "Id", id);
                });
        return result.getAmount();
    }

    @Override
    @Async
    public void addAmount(Integer id, Long value){
        log.info("Обновление баланса аккаунта с Id: {}", id);
        Account target = accountRepository.findById(id)
                .orElse(accountRepository.save(new Account().setAmount(0L)));

        Account updatedAccount = new Account();
        updatedAccount.setAmount(target.getAmount() + value);
        Account update = accountMapper.merge(updatedAccount, target);
        log.info("Балланс с Id: {} обновлен", id);
        accountRepository.save(update);
    }
}
