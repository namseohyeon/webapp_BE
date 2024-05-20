package com.example.exam.dto;
import com.example.exam.model.ShoppingEntity;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingDTO{
    private String id;
    private String title;
    private int price;
    private String topic;
    private String userid;

    public ShoppingDTO(final ShoppingEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.topic = entity.getTopic();
        this.userid = entity.getUserid();
    }

    public static ShoppingEntity toEntity(final ShoppingDTO dto){
        return ShoppingEntity.builder()
            .id(dto.getId())
            .title(dto.getTitle())
            .price(dto.getPrice())
            .topic(dto.getTopic())
            .userid(dto.getUserid())
            .build();
    }
}