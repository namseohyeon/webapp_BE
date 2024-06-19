 package com.example.exam.controller;
 import lombok.extern.slf4j.Slf4j;

import com.example.exam.dto.UserDTO;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exam.dto.ResponseDTO;
import com.example.exam.dto.ResponseDTO1;
import com.example.exam.dto.ShoppingDTO;
import com.example.exam.model.ShoppingEntity;
import com.example.exam.service.ShoppingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Slf4j
 @RestController
@RequestMapping("shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService service;

    // @GetMapping("/test")
    // public ResponseEntity<?> testShopping() {
    //     String str = service.testService();
    //     List<String> list = new ArrayList<>();
    //     list.add(str);
    //     ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
    //     return ResponseEntity.ok().body(response);
    // }    
    
    @PostMapping
    public ResponseEntity<?> createitem(@AuthenticationPrincipal String userId, @RequestBody ShoppingDTO dto){
        try{
        //String temporaryUserId = "seohyeonnam";

        ShoppingEntity entity = ShoppingDTO.toEntity(dto);

        entity.setId(null);

        entity.setUserId(userId);

        List<ShoppingEntity> entities = service.create(entity);
        List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());

        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }catch(Exception e){
        String error = e.getMessage();
        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().error(error).build();
        return ResponseEntity.badRequest().body(response);
    }
    }

    @GetMapping
    public ResponseEntity<?> retrieveitemlist(@AuthenticationPrincipal String userId) {
        //String temporaryUserId = "seohyeonnam";
        List<ShoppingEntity> entities = service.retrieve(userId);
        List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());

        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/like")
    public ResponseEntity<?> retrieveLikeitemlist() {
        //String temporaryUserId = "seohyeonnam";
        List<ShoppingEntity> entities = service.retrieveLike();
        List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());

        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();
        log.info("---",response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/orderedByPrice")
    public ResponseEntity<ResponseDTO<ShoppingDTO>> getAllItemsOrderedByPrice() {
        List<ShoppingEntity> entities = service.getAllItemsOrderedByPrice();
        List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());
        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }


    //    @GetMapping("/search")
//    public ResponseEntity<?> searchitem(@RequestBody ShoppingDTO dto) {
//        // String temporaryUserId = "temporary-user";
//        //String temporaryUserId = "seohyeonnam";
//        ShoppingEntity  entity = ShoppingDTO.toEntity(dto);
//        //entity.setUserId(temporaryUserId);
//        List<ShoppingEntity> entities = service.search(entity);
//        List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());
//
//        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();
//
//        return ResponseEntity.ok().body(response);
//    }
    @GetMapping("/search")
    public ResponseEntity<?> searchitem(@RequestParam String title) {
        List<ShoppingEntity> entities = service.search(title);
        List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());

        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    // @PutMapping
    // public ResponseEntity<?> updateitem(@RequestBody ShoppingDTO dto){
    //     String temporaryUserId = "seohyeonnam";
    //     ShoppingEntity  entity = ShoppingDTO.toEntity(dto);
    //     entity.setUserId(temporaryUserId);

    //     ShoppingEntity entities = service.update(entity);
    //     //ShoppingDTO dtos =entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());

    //     ShoppingDTO dtos = new ShoppingDTO(entities);
        
    //     List<ShoppingDTO> L_dtos = new ArrayList<>();
    //     L_dtos.add(dtos);

    //     ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(L_dtos).build();

    //     return ResponseEntity.ok().body(response);
    // }

    @PutMapping
    public ResponseEntity<?> updateitem(@AuthenticationPrincipal String userId, @RequestBody ShoppingDTO dto){
        //String temporaryUserId = "seohyeonnam";
        ShoppingEntity entity = ShoppingDTO.toEntity(dto);
        entity.setUserId(userId);
        List<ShoppingEntity> entities = service.update(entity);
        List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());
        ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
    
    @DeleteMapping
    public ResponseEntity<?> deleteitem(@AuthenticationPrincipal String userId,@RequestBody ShoppingDTO dto){
        try{
            //String temporaryUserId = "seohyeonnam";
            ShoppingEntity entity = ShoppingDTO.toEntity(dto); 
            entity.setUserId(userId);
            System.out.println(entity);
            List<ShoppingEntity> entities = service.delete(entity);
            List<ShoppingDTO> dtos = entities.stream().map(ShoppingDTO::new).collect(Collectors.toList());
            ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
            }
        catch(Exception e){
            String error = e.getMessage();
            ResponseDTO<ShoppingDTO> response = ResponseDTO.<ShoppingDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    
}