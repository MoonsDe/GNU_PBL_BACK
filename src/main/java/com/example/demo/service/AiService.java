package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import com.example.demo.repository.ItemRepository; // ItemRepository가 직접 필요하지는 않지만, 필요한 경우를 위해 남겨둡니다.
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters; // WebClient에서 파일 전송에 필요
import org.springframework.web.reactive.function.client.WebClient; // WebClient 사용
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class AiService {

    private final ItemService itemService;
    private final WebClient webClient; // ⭐️ WebClient 객체 추가

    // ⭐️ 생성자 수정: ItemService와 WebClient.Builder를 주입받습니다.
    public AiService(ItemService itemService, WebClient.Builder webClientBuilder) {
        this.itemService = itemService;
        // ⭐️ AI 서버의 IP 주소를 Base URL로 설정합니다. (실제 AI PC의 IP로 변경하세요!)
        this.webClient = webClientBuilder.baseUrl("http://192.168.0.11:5000").build();
    }

    /**
     * AI 모델을 호출하고, 그 결과로 Item DB를 검색하는 메인 로직
     */
    public ItemDto.DetailResponse classifyImage(MultipartFile image) throws IOException {
        
        // ------------------------------------
        // 1. image 파일을 외부 AI 서버로 전송하고 결과를 받습니다.
        // ------------------------------------
        String aiResultText = "";
        try {
            aiResultText = webClient.post()
                    .uri("/classify-image") // AI 서버의 이미지 분석 엔드포인트 주소
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(
                            "image", // 👈 AI 서버에서 받을 파일 파라미터 이름 (AI팀과 협의하세요)
                            image.getResource()
                    ))
                    .retrieve() // 응답 받기
                    .bodyToMono(String.class) // 응답 본문을 텍스트(예: "페트병")로 받음
                    .block(); // ⭐️ 동기 처리를 위해 잠시 블로킹
            
        } catch (Exception e) {
            // 통신 오류 발생 시
            throw new RuntimeException("AI 서버 통신에 실패했습니다. AI 서버가 켜져 있는지 확인하세요.", e);
        }

        // 2. AI 결과를 바탕으로 우리 DB 검색 (ItemService 재활용)
        // (예: "페트병"이 포함된 모든 품목 검색)
        if (aiResultText == null || aiResultText.trim().isEmpty()) {
            throw new IllegalArgumentException("AI 분석 결과가 유효하지 않습니다.");
        }
        
        List<ItemDto.SearchResponse> searchResults = itemService.searchItemsByName(aiResultText);

        if (searchResults.isEmpty()) {
            // TODO: 검색 결과가 없을 때 예외 처리 (DB에 해당 품목이 없을 경우)
             throw new EntityNotFoundException("DB에서 AI 분석 결과와 일치하는 품목을 찾을 수 없습니다: " + aiResultText);
        }

        // 3. 검색 결과 중 첫 번째 항목의 상세 정보 반환
        // (AI가 정확히 1개만 알려준다고 가정)
        Long firstItemId = searchResults.get(0).id();
        return itemService.getItemDetails(firstItemId);
    }
}