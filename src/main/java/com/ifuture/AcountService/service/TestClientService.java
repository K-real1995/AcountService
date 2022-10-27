package com.ifuture.AcountService.service;

/**
 * interface TestClientService for test client
 *
 * @author Kirill Soklakov
 * @since 2022-10-26
 */
public interface TestClientService {
    void makeThreads(Integer rCount, Integer wCount, String idList);
}
