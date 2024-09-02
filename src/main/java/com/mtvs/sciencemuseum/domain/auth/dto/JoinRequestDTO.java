package com.mtvs.sciencemuseum.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinRequestDTO {

    @Schema(description = "사용자 이름", example = "춘식이")
    @NotNull(message = "이름을 입력하세요")
    @NotBlank(message = "이름은 빈 문자 혹은 공백으로만 이루어질 수 없습니다.")
    private String username;

    @Schema(description = "비밀번호", example = "qwer")
    @NotNull(message = "비밀번호를 입력하세요")
    @NotBlank(message = "비밀번호는 빈 문자 혹은 공백으로만 이루어질 수 없습니다.")
    private String password;
}
