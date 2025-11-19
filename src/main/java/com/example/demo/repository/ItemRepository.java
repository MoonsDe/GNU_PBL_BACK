package com.example.demo.repository; // 👈 본인 패키지 경로 확인

import com.example.demo.entity.Item; // 👈 Item 클래스 경로 확인
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * 품목 이름에 'name'이 포함된(LIKE '%name%') Item 리스트를 검색합니다.
     */
    List<Item> findByNameContaining(String name);
}