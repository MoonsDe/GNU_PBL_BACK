package com.example.demo.controller;

import com.example.demo.dto.ItemDto; // Item의 DTO를 재사용
import com.example.demo.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    /**
     * AI 이미지 분석 요청 API
     * [POST] /api/ai/classify-image
     */
    @PostMapping("/classify-image")
    public ResponseEntity<ItemDto.DetailResponse> classifyImage(
            @RequestParam("image") MultipartFile image // "image"라는 이름으로 파일 받기
    ) throws IOException {
        
        // 로직은 AiService에 위임
        ItemDto.DetailResponse result = aiService.classifyImage(image);
        return ResponseEntity.ok(result);
    }
}