package com.example.exam.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exam.model.ShoppingEntity;
import java.util.List;


@Repository
public interface ShoppingRepository extends JpaRepository<ShoppingEntity, String> {
    //List<ShoppingEntity> findByUserId(String userid);
    List<ShoppingEntity> findByTitle(String title);

    List<ShoppingEntity> findByUserId(String userId);
    //Optinal<ShoppingEntity> findByTitle(String title);
    List<ShoppingEntity> findByLiked(boolean like);

    List<ShoppingEntity> findAllByOrderByPriceAsc();
}