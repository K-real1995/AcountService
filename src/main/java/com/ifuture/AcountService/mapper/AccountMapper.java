package com.ifuture.AcountService.mapper;

import com.ifuture.AcountService.model.Account;

/**
 * interface AccountMapper for Dto layer and for class {@link Account}.
 *
 * @author Kirill Soklakov
 * @since 2022-10-26
 */
public interface AccountMapper {

    Account merge(Account source, Account target);
}