package com.ifuture.AcountService.repository;

import com.ifuture.AcountService.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Jpa repository for model {@link Account}
 *  @author Kirill Soklakov
 *  @version 1.1
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}