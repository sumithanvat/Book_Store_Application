package com.bridgelabz.BookStore.repository;

import com.bridgelabz.BookStore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Override
    Optional<Order> findById(Integer integer);
}
