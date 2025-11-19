package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class AiService {

    // ⭐️ AI 서비스가 기존의 Item 서비스를 사용(주입)합니다.
    private final ItemService itemService; 

    public AiService(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * AI 모델을 호출하고, 그 결과로 Item DB를 검색하는 메인 로직
     */
    public ItemDto.DetailResponse classifyImage(MultipartFile image) throws IOException {
        
        // ------------------------------------
        // 1. (나중에 AI 연동 시 구현)
        // - image 파일을 외부 AI 서버로 전송
        // - AI 서버로부터 분석 결과 (예: "페트병") 텍스트를 받음
        // ------------------------------------

        // 지금은 AI가 "페트병"이라고 알려줬다고 '가정'
        String aiResultText = "페트병"; 

        // 2. AI 결과를 바탕으로 우리 DB 검색 (ItemService 재활용)
        // "페트병"이 포함된 모든 품목 검색
        List<ItemDto.SearchResponse> searchResults = itemService.searchItemsByName(aiResultText);

        if (searchResults.isEmpty()) {
            // TODO: 검색 결과가 없을 때 예외 처리
            // (예: "분석 결과 없음" DTO 반환)
            return null; // 임시
        }

        // 3. 검색 결과 중 첫 번째 항목의 상세 정보 반환
        // (AI가 정확히 1개만 알려준다고 가정)
        Long firstItemId = searchResults.get(0).id();
        return itemService.getItemDetails(firstItemId);
    }
}