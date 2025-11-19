package com.example.demo.controller; // 👈 본인 패키지 경로 확인

import com.example.demo.dto.ItemDto;
import com.example.demo.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items") // 이 컨트롤러의 모든 API는 /api/items 로 시작
public class ItemController {

    private final ItemService itemService;

    // 생성자 주입
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 1. 품목 이름으로 검색 API
     * [GET] /api/items/search?name=검색어
     */
    @GetMapping("/search")
    public ResponseEntity<List<ItemDto.SearchResponse>> searchItems(
            @RequestParam("name") String name
    ) {
        List<ItemDto.SearchResponse> results = itemService.searchItemsByName(name);
        return ResponseEntity.ok(results); // HTTP 200 OK
    }

    /**
     * 2. 품목 상세 정보 조회 API
     * [GET] /api/items/{id} (예: /api/items/1)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemDto.DetailResponse> getItemDetails(
            @PathVariable("id") Long id
    ) {
        ItemDto.DetailResponse item = itemService.getItemDetails(id);
        return ResponseEntity.ok(item);
    }
}