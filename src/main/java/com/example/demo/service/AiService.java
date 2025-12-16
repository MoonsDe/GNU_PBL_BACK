package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class AiService {

    private final ItemService itemService;
    private final WebClient webClient;

    // AI 서버가 주는 응답 형태 (예: {"prediction": "can"})
    record AiResponse(String prediction) {}

    public AiService(ItemService itemService, WebClient.Builder webClientBuilder) {
        this.itemService = itemService;
        
        // 🚨 [중요 체크] 친구 AI 컴퓨터 IP가 192.168.0.11 이 맞는지, 포트가 8001인지 확인하세요.
        this.webClient = webClientBuilder.baseUrl("http://10.27.174.225:8001").build();
    }

    public ItemDto.DetailResponse classifyImage(MultipartFile image) throws IOException {
        
        // 1. AI 서버로 이미지 전송 (POST /api/ai/classify)
        AiResponse aiResponse = null;
        try {
            aiResponse = webClient.post()
                    .uri("/api/ai/classify") 
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("image", image.getResource()))
                    .retrieve()
                    .bodyToMono(AiResponse.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            // 에러가 나면 이 메시지가 프론트엔드로 전달됩니다.
            throw new RuntimeException("AI 서버 연결 실패! (IP 주소나 8001 포트가 열려있는지 확인하세요)");
        }

        if (aiResponse == null || aiResponse.prediction() == null) {
            throw new RuntimeException("AI 서버로부터 응답을 받지 못했습니다.");
        }

        String aiResultName = aiResponse.prediction(); // 예: "can"
        
        // 2. ⭐️ 영어 결과 -> 한글 검색어로 변환
        String koreanKeyword = convertToKorean(aiResultName);
        
        System.out.println("🤖 AI 분석 결과: " + aiResultName + " -> 🇰🇷 검색어 변환: " + koreanKeyword);

        // 3. 변환된 한글 이름으로 DB 검색
        List<ItemDto.SearchResponse> searchResults = itemService.searchItemsByName(koreanKeyword);

        if (searchResults.isEmpty()) {
            // DB에 데이터가 없으면 구체적인 이유를 알려줌
            throw new EntityNotFoundException(
                "AI는 '" + aiResultName + "'(" + koreanKeyword + ")라고 분석했으나, DB에 해당 품목 정보가 없습니다."
            );
        }

        // 4. 검색된 첫 번째 결과의 상세 정보 반환
        return itemService.getItemDetails(searchResults.get(0).id());
    }

    // 🔄 [번역기] 영어를 우리 DB에 저장된 한글 단어로 바꿔주는 함수
    private String convertToKorean(String englishName) {
        if (englishName == null) return "";
        
        return switch (englishName.toLowerCase()) {
            case "can" -> "캔";             // "캔류..." 검색
            case "plastic" -> "플라스틱";     // "투명 페트병" 검색
            case "glass" -> "유리";         // "유리병" 검색
            case "paperpack" -> "종이팩";       // "우유팩/두유팩" 검색
            case "vinyl" -> "비닐";         // "비닐류" 검색
            case "styrofoam" -> "스티로폼"; // "스티로폼" 검색
            case "general_waste" -> "일반"; // "일반쓰레기" 검색
            case "unknown" -> "알수없음";   // "알수없음" 검색
            default -> englishName;        // 목록에 없으면 영어 그대로 검색 시도
        };
    }
}