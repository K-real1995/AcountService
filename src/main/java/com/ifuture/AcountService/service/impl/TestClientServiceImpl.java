package com.ifuture.AcountService.service.impl;

import com.ifuture.AcountService.exception.IndexOutOfBoundException;
import com.ifuture.AcountService.model.Account;
import com.ifuture.AcountService.repository.AccountRepository;
import com.ifuture.AcountService.service.TestClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class TestClientServiceImpl for making requests to app.
 * Implementation of {@link TestClientService} interface.
 *
 * @author Kirill Soklakov
 * @since 2022-10-26
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class TestClientServiceImpl extends Thread implements TestClientService {

    private final AccountRepository accountRepository;

    @Override
    public void makeThreads(Integer rCount, Integer wCount, String idList) {
        List<Integer> integerList = new ArrayList<>();
        Pattern p = Pattern.compile("(\\d+\\.?\\d*)");
        Matcher m = p.matcher(idList);
        while (m.find()) integerList.add(Integer.parseInt(m.group()));
        List<Account> accounts = accountRepository.findAll();
        if (accounts.size() >= integerList.get(1)){
            List<Integer> listId = new ArrayList<>();
            for (int i = integerList.get(0); i <= integerList.get(1) ; i++) {
                listId.add(i);
            }
            ExecutorService pool = Executors.newFixedThreadPool(rCount+wCount);
            for (int i = 0; i < rCount ; i++) {
                Random random = new Random();
                pool.submit(runGetThread(random.nextInt(1, listId.size()+1)));
            }
            for (int i = 0; i < wCount ; i++) {
                Random random = new Random();
                pool.submit(runPostThread(random.nextInt(1, listId.size()+1)));
            }
            pool.shutdown();
        }
        else {
            throw new IndexOutOfBoundException("List id", String.valueOf(integerList.get(1)), accounts.size());
        }

    }

    public Runnable runGetThread(Integer id){

        return new Runnable() {
            @Override
            public void run() {
                String uri = "http://localhost:8080/api/v1/accounts/" + id;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getForObject(uri, String.class);
            }
        };
    }

    public Runnable runPostThread(Integer id){

        return new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                String uri = "http://localhost:8080/api/v1/accounts/" + id + "?amount=" + random.nextLong(-1000,1001);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForObject(uri, String.class, String.class);
            }
        };
    }
}
