package com.mtvs.sciencemuseum.domain.ai.service;

import com.mtvs.sciencemuseum.domain.ai.dto.ChatbotRequestDTO;
import com.mtvs.sciencemuseum.domain.ai.dto.ChatbotResponseDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {

    @Value("${ai.url}")
    private String ip;

    @Value("${ai.port}")
    private String port;

    private static WebClient aiServer;
    @PostConstruct
    void init() {
        aiServer = WebClient.create(ip+ ":"+port);
    }

    public ChatbotResponseDTO question(ChatbotRequestDTO dto){

        ChatbotResponseDTO response = aiServer
                .post()
                .uri("/chatbot")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), ChatbotRequestDTO.class)
                .retrieve()
                .bodyToMono(ChatbotResponseDTO.class)
                .block();

        return response;
    }

}
