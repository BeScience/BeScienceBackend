package com.mtvs.sciencemuseum.domain.ai.controller;

import com.mtvs.sciencemuseum.common.exception.ErrorResponse;
import com.mtvs.sciencemuseum.domain.ai.dto.ChatbotRequestDTO;
import com.mtvs.sciencemuseum.domain.ai.dto.ChatbotResponseDTO;
import com.mtvs.sciencemuseum.domain.ai.service.AIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @Operation(summary = "챗봇", description = "챗봇과 통신하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "챗봇과 성공적으로 통신했습니다."),
            @ApiResponse(responseCode = "400", description = "비밀번호가 틀렸습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "401", description = "사용자가 로그인 되있지 않습니다.(지금은 안씀)", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 401, \"errorType\": \"Login required\", \"message\": \"로그인이 필요합니다.\"}"
                    )
            ))
    })
    @PostMapping("/chat")
    public ResponseEntity<ChatbotResponseDTO> chatbot(@RequestBody ChatbotRequestDTO question){

        ChatbotResponseDTO answer = aiService.question(question);

        return ResponseEntity.ok(answer);
    }
}
