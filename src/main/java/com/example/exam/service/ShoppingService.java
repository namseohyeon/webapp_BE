package com.example.exam.service;

import java.util.List;
import java.util.Optional;

import com.example.exam.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.exam.model.ShoppingEntity;
import com.example.exam.persistence.ShoppingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShoppingService {
    @Autowired
    private ShoppingRepository repository;

    // public String testService(){
    //     ShoppingEntity entity = ShoppingEntity.builder().title("책먹는 여우").build();
    //     repository.save(entity);
    //     ShoppingEntity savedEntity = repository.findById(entity.getId()).get();
    //     return savedEntity.getTitle();

    // }

    public List<ShoppingEntity> create(final ShoppingEntity entity){
        validate(entity);
        repository.save(entity);

        log.info("Enity Id : {} is saved", entity.getId());
        return repository.findByUserId(entity.getUserId());


    }

    public List<ShoppingEntity> retrieve(String userId){
        return repository.findByUserId(userId);

    }

    public List<ShoppingEntity> retrieveLike(){
        return repository.findByLiked(true);

    }

    public List<ShoppingEntity> getAllItemsOrderedByPrice() {
        return repository.findAllByOrderByPriceAsc();
    }



    public List<ShoppingEntity> search(final String title){
        return repository.findByTitle(title);

    }

    //방법1 : 단일 엔터티로 보내고, 컨트롤러에서 리스트로 변환
    // public ShoppingEntity update(final ShoppingEntity entity){
    //     validate(entity);
    //     final Optional<ShoppingEntity> orignal = repository.findById(entity.getId());
    //     if (orignal.isPresent()){
    //         final ShoppingEntity shopping = orignal.get();
    //         shopping.setTitle(entity.getTitle());
    //         shopping.setPrice(entity.getPrice());
    //         shopping.setTopic(entity.getTopic());

    //         return repository.save(shopping);
    //     }
    //     return null;
    //     //return repository.findById(entity.getId());
    //     //return repository.findById(entity.getId());
    // }

    //방법2: 단일엔터티로 보내고, 단일 엔터티 반환 dto를 다시 제작   
    //     public ShoppingEntity update(final ShoppingEntity entity){
    //     validate(entity);
    //     final Optional<ShoppingEntity> orignal = repository.findById(entity.getId());
    //     if (orignal.isPresent()){
    //         final ShoppingEntity shopping = orignal.get();
    //         shopping.setTitle(entity.getTitle());
    //         shopping.setPrice(entity.getPrice());
    //         shopping.setTopic(entity.getTopic());
    //         return repository.save(shopping);
    //     }
    //     return null;
    // }

//    public ShoppingEntity update(final ShoppingEntity entity){
//        validate(entity);
//        final Optional<ShoppingEntity> orignal = repository.findById(entity.getId());
//        if (orignal.isPresent()){
//            final ShoppingEntity shopping = orignal.get();
//            shopping.setTitle(entity.getTitle());
//            shopping.setPrice(entity.getPrice());
//            shopping.setTopic(entity.getTopic());
//            shopping.setUserid(entity.getUserid());
//            return repository.save(shopping);
//        }
//        return null;
//    }

//    public List<ShoppingEntity> update(final ShoppingEntity entity) {
//        //validate(entity);
//        List<ShoppingEntity> entities = repository.findByTitle(entity.getTitle());
//
//        for (ShoppingEntity foundEntity : entities) {
//            // 엔터티를 삭제하기 전에 유효성 검사 등을 수행할 수 있음
//            validate(foundEntity);
//            try {
//                System.out.print(foundEntity);
//                log.info("Updating entity: {}", foundEntity);
//                foundEntity.setTitle(entity.getTitle());
//                foundEntity.setPrice(entity.getPrice());
//                foundEntity.setTopic(entity.getTopic());
//                foundEntity.setUserid(entity.getUserid());
//                repository.save(foundEntity);
//                System.out.print(foundEntity);
//            } catch (Exception e) {
//                log.error("Error deleting entity with ID: {}", foundEntity.getId(), e);
//                throw new RuntimeException("Error deleting entity with ID: " + foundEntity.getId());
//            }
//        }
//        return repository.findAll();
//    }

//    public List<ShoppingEntity> delete(final ShoppingEntity entity){
//        final Optional<ShoppingEntity> orignal = repository.findById(entity.getId());
//        final ShoppingEntity entities =orignal.get();
//        entity.setTitle(entities.getTitle());
//        validate(entity);
//        try{
//            repository.delete(entity);
//        }
//        catch(Exception e){
//            log.error("error deleting entity", entity.getId(),e);
//            throw new RuntimeException("error deleting entity"+entity.getId());
//        }
//        return retrieve();
//    }

    public List<ShoppingEntity> update(final ShoppingEntity entity) {
        validate(entity);
        final Optional<ShoppingEntity> orignal = repository.findById(entity.getId());
        if (orignal.isPresent()) {
            final ShoppingEntity shopping = orignal.get();
            shopping.setTitle(entity.getTitle());
            shopping.setPrice(entity.getPrice());
            shopping.setTopic(entity.getTopic());
            shopping.setUserId(entity.getUserId());
            shopping.setLiked(entity.isLiked());
            shopping.setUserName(entity.getUserName());
            repository.save(shopping);
        }
        return repository.findAll();
    }
     public List<ShoppingEntity> delete(final ShoppingEntity entity) {
        final List<ShoppingEntity> entities = repository.findByTitle(entity.getTitle()); // 해당하는 제목을 가진 엔터티들을 찾음
        for (ShoppingEntity foundEntity : entities) {
            // 엔터티를 삭제하기 전에 유효성 검사 등을 수행할 수 있음
            validate(foundEntity);
            try {
                repository.delete(foundEntity); // 찾은 엔터티를 삭제
            } catch (Exception e) {
                log.error("Error deleting entity with ID: {}", foundEntity.getId(), e);
                throw new RuntimeException("Error deleting entity with ID: " + foundEntity.getId());
            }
        }
        return retrieve(entity.getUserId()); // 엔터티 목록을 반환
    }

    public void validate(final ShoppingEntity entity){
        if(entity==null){
            log.warn("Entity can not be null");
            throw new RuntimeException("Entity cannot be null");

        }
//        if (entity.getUserid() == null){
//            log.warn("Unknown user.");
//            throw new RuntimeException("Unknown user.");
//        }
    }
}
