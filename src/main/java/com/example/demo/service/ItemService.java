package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import com.example.demo.entity.Item; // ⭐️ Item Entity에 category, howToRecycle 등이 있어야 함
import com.example.demo.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 1. 품목 이름으로 검색 (⭐️ 수정됨 ⭐️)
     * SearchResponse DTO에 맞게 price 대신 category 반환
     */
    public List<ItemDto.SearchResponse> searchItemsByName(String name) {
        List<Item> items = itemRepository.findByNameContaining(name);

        return items.stream()
                .map(item -> new ItemDto.SearchResponse(
                        item.getId(),
                        item.getName(),
                        item.getCategory() // ⭐️ price -> getCategory()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 2. 품목 ID로 상세 조회 (⭐️ 수정됨 ⭐️)
     * 공통 헬퍼 메소드 사용
     */
    public ItemDto.DetailResponse getItemDetails(Long id) {
        Item item = findItemById(id);
        return convertToDetailResponse(item);
    }

    /**
     * 3. 품목 등록 (Create)
     */
    @Transactional
    public ItemDto.DetailResponse createItem(ItemDto.CreateRequest request) {
        Item item = new Item();
        item.setName(request.name());
        item.setCategory(request.category());
        item.setHowToRecycle(request.howToRecycle());
        item.setCaution(request.caution());

        Item savedItem = itemRepository.save(item);
        return convertToDetailResponse(savedItem);
    }

    /**
     * 4. 품목 수정 (Update)
     */
    @Transactional
    public ItemDto.DetailResponse updateItem(Long id, ItemDto.UpdateRequest request) {
        Item item = findItemById(id); // 기존 품목 찾기
        
        // Entity 필드 값 변경
        item.setName(request.name());
        item.setCategory(request.category());
        item.setHowToRecycle(request.howToRecycle());
        item.setCaution(request.caution());
        
        return convertToDetailResponse(item); // ⭐️ (JPA 변경 감지로 save 안해도 됨)
    }

    /**
     * 5. 품목 삭제 (Delete)
     */
    @Transactional
    public void deleteItem(Long id) {
        Item item = findItemById(id); // 품목 있는지 확인
        itemRepository.delete(item); // 삭제
    }

    // ----------------------------------------------------
    // [내부 공통 헬퍼 메소드]
    // ----------------------------------------------------

    // ID로 Item 찾기 (공통 로직)
    private Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 품목을 찾을 수 없습니다. id=" + id));
    }

    // Entity -> DetailResponse DTO 변환 (공통 로직)
    private ItemDto.DetailResponse convertToDetailResponse(Item item) {
        return new ItemDto.DetailResponse(
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getHowToRecycle(),
                item.getCaution()
        );
    }
}