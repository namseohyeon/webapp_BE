package com.example.exam.dto;

import com.example.exam.model.ShoppingEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingDTO {
    private String id;       // 쇼핑 아이템의 ID
    private String title;    // 쇼핑 아이템의 제목
    private int price;       // 쇼핑 아이템의 가격
    private String topic;    // 쇼핑 아이템의 주제
    private String userId;   // 쇼핑 아이템을 등록한 사용자의 ID
    private boolean liked;    // 사용자가 쇼핑 아이템을 좋아하는지 여부
    private String userName;

    // 엔티티 객체에서 DTO 객체로 변환하는 생성자
    public ShoppingDTO(ShoppingEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.topic = entity.getTopic();
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.liked = entity.isLiked();
    }

    // DTO 객체에서 엔티티 객체로 변환하는 정적 메서드
    public static ShoppingEntity toEntity(ShoppingDTO dto) {
        return ShoppingEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .topic(dto.getTopic())
                .userId(dto.getUserId())
                .liked(dto.isLiked())
                .userName(dto.getUserName())
                .build();
    }
}
