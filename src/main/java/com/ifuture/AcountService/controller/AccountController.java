package com.ifuture.AcountService.controller;

import com.ifuture.AcountService.dto.DtoErrorInfo;
import com.ifuture.AcountService.dto.response.AccountResponse;
import com.ifuture.AcountService.dto.update.AccountUpdate;
import com.ifuture.AcountService.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.ifuture.AcountService.utils.UrlConstants.*;
import static org.springframework.http.HttpStatus.OK;

/**
 * Rest Controller for class {@link com.ifuture.AcountService.model.Account}.
 *
 * @author Kirill.Soklakov
 * @since 2022-10-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + ACCOUNT_URL)
@Tag(name = "Аккаунт контроллер", description = "Контроллер для работы с аккаунтом")
@Slf4j
public class AccountController {
    private static AtomicLong rRequest = new AtomicLong();
    private static AtomicLong wRequest = new AtomicLong();
    private static AtomicLong rCountPerSecond = new AtomicLong();
    private static AtomicLong wCountPerSecond = new AtomicLong();
    private final AccountService accountService;

    @Operation(summary = "Получение данных аккаунта по Id")
    @ApiResponse(responseCode = "200", description = "Аккаунт найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Аккаунт не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public synchronized ResponseEntity<Long> findById(@PathVariable Integer id) {
        rRequest.incrementAndGet();
        rCountPerSecond.incrementAndGet();
        return new ResponseEntity<>(accountService.getAmount(id), OK);
    }


    @Operation(summary = "Обновление баланса аккаунта")
    @ApiResponse(responseCode = "200", description = "Баланс обновлен",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AccountUpdate.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Аккаунт не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping(ID)
    public synchronized ResponseEntity<?> addAmount(
            @PathVariable Integer id,
            @RequestParam(value = "amount") Long amount) {
        accountService.addAmount(id, amount);
        wRequest.incrementAndGet();
        wCountPerSecond.incrementAndGet();
        return ResponseEntity.status(HttpStatus.CREATED).body("Баланс обновлен");
    }

    @Operation(summary = "Сброс статистики")
    @ApiResponse(responseCode = "201", description = "Статистика сброшена",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping(RESET_STATISTIC_URL)
    public ResponseEntity<?> resetStatistics(){
        rRequest.set(0);
        wRequest.set(0);
        rCountPerSecond.set(0);
        wCountPerSecond.set(0);
        return ResponseEntity.status(HttpStatus.CREATED).body("Статистика успешно сброшена");
    }

    @Operation(summary = "Количество запросов каждые 5 минут")
    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 5)
    public void countOfAllRequest(){
        log.info("read requests: {}", rRequest.get());
        log.info("write requests: {}", wRequest.get());
    }

    @Operation(summary = "Количество запросов каждую минуту")
    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 10)
    public void requestsPerMinute(){
        log.info("read requests per minute: {}", rCountPerSecond.getAndSet(0));
        log.info("write requests per minute: {}", wCountPerSecond.getAndSet(0));
    }
}
