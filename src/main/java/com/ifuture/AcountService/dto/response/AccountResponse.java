package com.ifuture.AcountService.dto.response;

import lombok.*;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Аккаунт", description = "Информация об аккаунте")
public class AccountResponse {

    @Schema(description = "Количество средств у пользователя",
            maximum = "9223372036854775807",
            minimum = "-9223372036854775808",
            example = "2")
    private Long amount;
}
