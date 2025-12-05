package com.example.store_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Найти все заказы пользователя и отсортировать по дате (сначала новые)
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
}
