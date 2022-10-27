package com.ifuture.AcountService.mapper.impl;

import com.ifuture.AcountService.mapper.AccountMapper;
import com.ifuture.AcountService.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * class CommentMapperImpl is implementation of {@link AccountMapper} interface.
 *
 * @author Kirill Soklakov
 * @since 2022-10-26
 */
@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account merge(Account source, Account target) {
        if (source.getAmount() != null) {
            target.setAmount(source.getAmount());
        }
        return target;
    }
}
