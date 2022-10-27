package com.ifuture.AcountService.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Обновление баланса аккаунта", description = "Запрос для обновления баланса аккаунта")
public class AccountUpdate {

    @Schema(description = "Количество средств у пользователя",
            maximum = "9223372036854775807",
            minimum = "-9223372036854775808",
            example = "2")
    private Long amount;
}
