package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "items") // DB 테이블 이름
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 품목 이름 (예: "페트병")

    // ⭐️ [이 부분 추가] ⭐️
    @Column(nullable = false)
    private String category; // 분류 (예: "플라스틱")

    @Lob // 긴 텍스트
    @Column(nullable = false)
    private String howToRecycle; // 분리수거 방법

    @Lob
    private String caution; // 주의사항

    // ------------------------------------
    // ❌ [이 부분 삭제] ❌
    // private String description; // (howToRecycle로 대체)
    // private BigDecimal price;
    // private int stockQuantity;
    // ------------------------------------
    
}