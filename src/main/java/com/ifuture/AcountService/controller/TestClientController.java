package com.ifuture.AcountService.controller;

import com.ifuture.AcountService.dto.DtoErrorInfo;
import com.ifuture.AcountService.service.TestClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ifuture.AcountService.utils.UrlConstants.*;

/**
 * Rest Controller for test client.
 *
 * @author Kirill.Soklakov
 * @since 2022-10-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + TEST_CLIENT_URL)
@Tag(name = "Контроллер тестового клиента", description = "Контроллер для запуска тестового клиента")
public class TestClientController {
    @Value("${rCount}")
    private Integer rCount;
    @Value("${wCount}")
    private Integer wCount;

    @Value("${idList}")
    private String idList;
    private final TestClientService testClientService;

    @Operation(summary = "Контроллер тестового клиента")
    @ApiResponse(responseCode = "200", description = "Тестовый клиент выполнен")
    @ApiResponse(responseCode = "404", description = "Аккаунты не найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<?> runTestClient() {
        testClientService.makeThreads(rCount, wCount, idList);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}