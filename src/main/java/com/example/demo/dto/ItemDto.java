package com.example.demo.dto; // 👈 본인 패키지 경로 확인

import java.math.BigDecimal;

// API 응답을 위한 DTO (Data Transfer Object)
public class ItemDto {

    public record CreateRequest(
            String name,
            String category,
            String howToRecycle,
            String caution
        ) {}

    /**
     * 상세 조회 응답용 DTO (Java 14+의 record 사용)
     */
    public record DetailResponse(
            Long id,
            String name,         // 예: "페트병"
            String category,     // 예: "플라스틱"
            String howToRecycle, // 예: "라벨을 제거하고 헹군 뒤 찌그러뜨려 버리세요."
            String caution       // 예: "유색 페트병은 비닐류로 버리세요."
    ) {}
    
    /**
     * 3. 품목 수정(PUT) 요청용 DTO
     */
    public record UpdateRequest(
            String name,
            String category,
            String howToRecycle,
            String caution
    ) {}

    /**
     * 4. 품목 검색(GET) 응답용 DTO
     * (이건 검색 목록을 위한 간단한 버전입니다)
     */
    public record SearchResponse(
            Long id,
            String name,
            String category
    ) {}
}