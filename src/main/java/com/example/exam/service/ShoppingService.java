package com.example.exam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ShoppingEntity> retrieve(final String userId){
        return repository.findByUserId(userId);

    }

    public List<ShoppingEntity> search(final ShoppingEntity entity){
        return repository.findByTitle(entity.getTitle());

    }

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

        public ShoppingEntity update(final ShoppingEntity entity){
        validate(entity);
        final Optional<ShoppingEntity> orignal = repository.findById(entity.getId());
        if (orignal.isPresent()){
            final ShoppingEntity shopping = orignal.get();
            shopping.setTitle(entity.getTitle());
            shopping.setPrice(entity.getPrice());
            shopping.setTopic(entity.getTopic());
            return repository.save(shopping);
        }
        return null;
        //return repository.findById(entity.getId());
        //return repository.findById(entity.getId());
    }

    public List<ShoppingEntity> delete(final ShoppingEntity entity){
        validate(entity);
        try{
            repository.delete(entity);
        }
        catch(Exception e){
            log.error("error deleting entity", entity.getId(),e);
            throw new RuntimeException("error deleting entity"+entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    public void validate(final ShoppingEntity entity){
        if(entity==null){
            log.warn("Entity can not be null");
            throw new RuntimeException("Entity cannot be null");

        }
        if (entity.getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("unknown user.");
        }
    }
}
